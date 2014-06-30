package org.laruche.maven.plugins.beans.algo.factory;

import org.laruche.maven.plugins.beans.algo.IdentityAlgo;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;
import org.laruche.maven.plugins.beans.algo.composite.AndAlgo;
import org.laruche.maven.plugins.beans.algo.composite.CompositeVersionAlgorithm;
import org.laruche.maven.plugins.beans.algo.composite.OrAlgo;
import org.laruche.maven.plugins.beans.algo.iterate.IterateIterNumAlgo;
import org.laruche.maven.plugins.beans.algo.iterate.IterateMajorNumAlgo;
import org.laruche.maven.plugins.beans.algo.iterate.IterateMinorNumAlgo;
import org.laruche.maven.plugins.beans.algo.others.AddingSuffixAlgorithm;
import org.laruche.maven.plugins.beans.algo.others.RemovingSuffixAlgorithm;

import java.util.*;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Converter permettant de transformer une chaîne de caractères en alogrithme. <br />
 *
 * @author Frédéric Moulé
 * @see org.laruche.maven.plugins.beans.algo.factory.AlgoConvert
 */
public class DefaultAlgoConverter implements AlgoConvert {
    private static final String BLANK_SPACE = " ";
    private final Map<String, VersionAlgorithm> dicoAlgorithms = new HashMap<String, VersionAlgorithm>();

    public DefaultAlgoConverter() {
        dicoAlgorithms.put("major", new IterateMajorNumAlgo());
        dicoAlgorithms.put("minor", new IterateMinorNumAlgo());
        dicoAlgorithms.put("iter", new IterateIterNumAlgo());
        dicoAlgorithms.put("add-snapshot", new AddingSuffixAlgorithm());
        dicoAlgorithms.put("remove-snapshot", new RemovingSuffixAlgorithm());
    }

    @Override
    public VersionAlgorithm createAlgorithme(final String line) {
        if (isEmpty(line.trim())) {
            return new IdentityAlgo();
        }
        final StringTokenizer tokenizer = new StringTokenizer(explodeLine(line), BLANK_SPACE);
        final List<AlgoNode> nodes = new ArrayList<AlgoNode>();
        AlgoNode currentNode = new AlgoNode(new AndAlgo());
        nodes.add(currentNode);
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken().trim();
            if (isEmpty(token)) {
                continue;
            }
            if ("(".equals(token)) {
                final AlgoNode childNode = new AlgoNode(new AndAlgo());
                currentNode.addChild(childNode);
                currentNode = childNode;
                nodes.add(currentNode);
            } else if (")".equals(token)) {
                nodes.remove(currentNode);
                currentNode = nodes.get(nodes.size() - 1);
            } else if ("&".equals(token) || "&&".equals(token)) {
                currentNode.setType(LogicalType.AND);
            } else if ("||".equals(token)) {
                currentNode.setType(LogicalType.OR);
            } else if (dicoAlgorithms.containsKey(token)) {
                currentNode.addChild(new AlgoNode(dicoAlgorithms.get(token)));
            } else {
                throw new IllegalArgumentException("Pas d'algorithme correspondant au label : " + token);
            }
        }
        return nodes.get(0).computeAlgorithm();
    }

    private static String explodeLine(final String line) {
        String newLine = line.replace("(", "(" + BLANK_SPACE);
        newLine = newLine.replace(")", BLANK_SPACE + ")");
        return newLine;
    }

    ////// Classes Internes //////

    private static class AlgoNode {
        private VersionAlgorithm algorithm = new AndAlgo();
        private List<AlgoNode> children = new ArrayList<AlgoNode>();

        public AlgoNode(final VersionAlgorithm newAlgo) {
            algorithm = newAlgo;
        }

        public void addChild(final AlgoNode childNode) {
            children.add(childNode);
        }

        public void setType(final LogicalType type) {
            CompositeVersionAlgorithm newAlgo;
            if (LogicalType.OR == type) {
                newAlgo = new OrAlgo();
            } else {
                newAlgo = new AndAlgo();
            }
            this.algorithm = newAlgo;
        }

        public VersionAlgorithm computeAlgorithm() {
            if (!(algorithm instanceof CompositeVersionAlgorithm)) {
                return algorithm;
            }
            final CompositeVersionAlgorithm compositeAlgorithm = (CompositeVersionAlgorithm) algorithm;
            for (AlgoNode child : children) {
                compositeAlgorithm.addAlgo(child.computeAlgorithm());
            }
            return compositeAlgorithm;
        }
    }

    private static enum LogicalType {
        OR,
        AND
    }


}
