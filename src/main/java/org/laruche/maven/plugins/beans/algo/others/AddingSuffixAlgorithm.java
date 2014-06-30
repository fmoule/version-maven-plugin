package org.laruche.maven.plugins.beans.algo.others;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;
import org.laruche.maven.plugins.beans.algo.AbstractVersionAlgorithm;

/**
* Created by f.moule on 27/06/2014.
*/
public class AddingSuffixAlgorithm extends AbstractVersionAlgorithm {
    private static final String SUFFIX = "-SNAPSHOT";

    public AddingSuffixAlgorithm() {
        super("add-snapshot");
    }

    @Override
    public Version compute(final Version oldVersion) {
        if (oldVersion.containsString(SUFFIX)) {
            return oldVersion;
        }
        final Version newVersion = new Version(oldVersion);
        newVersion.addVersionToken(new VersionToken("-", "SNAPSHOT"));
        return newVersion;
    }

}
