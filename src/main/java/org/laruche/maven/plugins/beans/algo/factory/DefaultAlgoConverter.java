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
        AlgoNode currentNode = new AlgoNode();
        String token;
        nodes.add(currentNode);
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken().trim();
            if (isEmpty(token)) {
                continue;
            }
            if ("(".equals(token)) {
                CompositeVersionAlgorithm newAlgo = new AndAlgo();
                currentNode.addChild(newAlgo);
                currentNode = new AlgoNode(newAlgo);
                nodes.add(currentNode);
            } else if (")".equals(token)) {
                nodes.remove(currentNode);
                currentNode = nodes.get(nodes.size() - 1);
            } else if ("&".equals(token) || "&&".equals(token)) {
                currentNode.setType(LogicalType.AND);
            } else if ("||".equals(token)) {
                currentNode.setType(LogicalType.OR);
            } else if (dicoAlgorithms.containsKey(token)) {
                currentNode.addChild(dicoAlgorithms.get(token));
            } else {
                throw new IllegalArgumentException("Pas d'algorithme correspondant au label : " + token);
            }
        }
        return nodes.get(0).getVersionAlgorithm();
    }

    private static String explodeLine(final String line) {
        String newLine = line.replace("(", "(" + BLANK_SPACE);
        newLine = newLine.replace(")", BLANK_SPACE + ")");
        return newLine;
    }

    ////// Classes Internes //////

    private static class AlgoNode {
        private CompositeVersionAlgorithm compositeAlgo = new AndAlgo();
        private LogicalType type = LogicalType.AND;

        public AlgoNode() {
            // EMPTY
        }

        public AlgoNode(final CompositeVersionAlgorithm newAlgo) {
            compositeAlgo = newAlgo;
            type = (compositeAlgo instanceof OrAlgo ? LogicalType.OR : LogicalType.AND);
        }

        public void addChild(final VersionAlgorithm algo) {
            compositeAlgo.addAlgo(algo);
        }

        public void setType(final LogicalType type) {
            if (this.type == type) {
                return;
            }
            CompositeVersionAlgorithm newAlgo;
            if (LogicalType.OR == type) {
                newAlgo = new OrAlgo();
            } else {
                newAlgo = new AndAlgo();
            }
            newAlgo.addAlgos(compositeAlgo.getAlgorithms());
            this.compositeAlgo = newAlgo;
        }

        public VersionAlgorithm getVersionAlgorithm() {
            return compositeAlgo;
        }
    }

    private static enum LogicalType {
        OR,
        AND
    }


}
