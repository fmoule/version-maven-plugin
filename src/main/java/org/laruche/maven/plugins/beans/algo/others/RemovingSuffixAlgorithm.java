package org.laruche.maven.plugins.beans.algo.others;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;
import org.laruche.maven.plugins.beans.algo.AbstractVersionAlgorithm;

public class RemovingSuffixAlgorithm extends AbstractVersionAlgorithm {

    public RemovingSuffixAlgorithm() {
        super("remove-snapshot");
    }

    @Override
    public Version compute(final Version oldVersion) {
        if (oldVersion == null) {
            throw new IllegalArgumentException("La version à convertir est nulle");
        }
        return oldVersion.removeToken(new VersionToken("-", "SNAPSHOT"));
    }
}
