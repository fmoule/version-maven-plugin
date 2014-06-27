package org.laruche.maven.plugins.beans.algo.composite;

import org.laruche.maven.plugins.beans.algo.AbstractVersionAlgorithm;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractCompositeVersionAlgorithm extends AbstractVersionAlgorithm implements CompositeVersionAlgorithm {
    protected final List<VersionAlgorithm> algorithms = new ArrayList<VersionAlgorithm>();

    public AbstractCompositeVersionAlgorithm(final String label) {
        super(label);
    }

    @Override
    public void addAlgos(final List<? extends VersionAlgorithm> algos) {
        algorithms.addAll(algos);
    }

    @Override
    public void addAlgo(final VersionAlgorithm algorithm) {
        algorithms.add(algorithm);
    }

    @Override
    public int hashCode() {
        return algorithms.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj) || !(obj instanceof AbstractCompositeVersionAlgorithm)) {
            return false;
        }
        final AbstractCompositeVersionAlgorithm that = (AbstractCompositeVersionAlgorithm) obj;
        return (algorithms.equals(that.algorithms));
    }

    @Override
    public List<VersionAlgorithm> getAlgorithms() {
        return new ArrayList<VersionAlgorithm>(algorithms);
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("(");
        int cursor = 0;
        for (VersionAlgorithm algorithm : algorithms) {
            if (cursor > 0) {
                buffer.append(" ");
                buffer.append(this.getLabel());
            }
            buffer.append(algorithm.toString());
            cursor++;
        }
        buffer.append(")");
        return buffer.toString();
    }
}
