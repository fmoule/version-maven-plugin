package org.laruche.maven.plugins.beans.algo;

import org.laruche.maven.plugins.beans.Version;

public class IdentityAlgo implements VersionAlgorithm {

    @Override
    public String getLabel() {
        return "identical";
    }

    @Override
    public Version compute(final Version oldVersion) {
        return oldVersion;
    }
}
