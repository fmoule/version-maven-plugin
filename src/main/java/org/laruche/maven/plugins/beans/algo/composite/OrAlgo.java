package org.laruche.maven.plugins.beans.algo.composite;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import static java.util.Arrays.asList;

public class OrAlgo extends AbstractCompositeVersionAlgorithm {

    public OrAlgo() {
        super("||");
    }

    @Override
    public Version compute(final Version theVersion) {
        Version newVersion = theVersion;
        Version oldVersion = theVersion;
        for (VersionAlgorithm algorithm : algorithms) {
            newVersion = algorithm.compute(oldVersion);
            if (!oldVersion.equals(newVersion)) {
                return newVersion;
            }
            oldVersion = newVersion;
        }
        return newVersion;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return (super.equals(obj) && this.getClass() == obj.getClass());
    }

    public static OrAlgo or(final VersionAlgorithm... algorithms) {
        final OrAlgo orAlgo = new OrAlgo();
        orAlgo.addAlgos(asList(algorithms));
        return orAlgo;
    }
}
