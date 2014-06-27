package org.laruche.maven.plugins.beans.algo.composite;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import static java.util.Arrays.asList;


public class AndAlgo extends AbstractCompositeVersionAlgorithm {

    public AndAlgo() {
        super("&");
    }

    @Override
    public Version compute(final Version oldVersion) {
        Version newVersion = oldVersion;
        for (VersionAlgorithm algorithm : algorithms) {
            newVersion = algorithm.compute(newVersion);
        }
        return newVersion;
    }

    public void addAlgo(final VersionAlgorithm algorithm) {
        algorithms.add(algorithm);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return (super.equals(obj) && this.getClass() == obj.getClass());
    }

    public static AndAlgo and(final VersionAlgorithm... algorithms) {
        final AndAlgo andAlgo = new AndAlgo();
        if (algorithms != null) {
            andAlgo.addAlgos(asList(algorithms));
        }
        return andAlgo;
    }
}
