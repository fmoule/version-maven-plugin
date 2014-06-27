package org.laruche.maven.plugins.beans.algo.iterate;

import org.apache.commons.lang.StringUtils;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;
import org.laruche.maven.plugins.beans.algo.AbstractVersionAlgorithm;


abstract class AbstractIterateAlgo extends AbstractVersionAlgorithm {
    protected int index;

    public AbstractIterateAlgo(final String label, int index) {
        super(label);
        this.index = index;
    }

    @Override
    public Version compute(final Version oldVersion) {
        if (oldVersion == null) {
            throw new IllegalArgumentException("La version à modifier est nulle");
        }
        final Version newVersion = new Version();
        int count = 0;
        for (VersionToken token : oldVersion) {
            if (count == index && StringUtils.isNumeric(token.getValue())) {
                iterate(newVersion, token);
            } else {
                newVersion.addVersionToken(token);
            }
            count++;
        }
        return newVersion;
    }

    protected abstract void iterate(Version newVersion, VersionToken token);

    @Override
    public int hashCode() {
        final int prime = 17;
        int hasCode = super.hashCode();
        hasCode = (prime * hasCode) + index;
        return hasCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj) || !(obj instanceof AbstractIterateAlgo)) {
            return false;
        }
        final AbstractIterateAlgo that = (AbstractIterateAlgo) obj;
        return (index == that.index);
    }

    @Override
    public String toString() {
        return this.getLabel();
    }
}
