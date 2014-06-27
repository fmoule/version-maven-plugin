package org.laruche.maven.plugins.beans.algo.factory;

import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

public interface AlgoConvert {

    public VersionAlgorithm createAlgorithme(final String line);
}
