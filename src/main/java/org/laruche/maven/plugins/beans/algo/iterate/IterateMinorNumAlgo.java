package org.laruche.maven.plugins.beans.algo.iterate;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * Alogorithme permettant d'incrémenter le numéro de version mineur. <br />
 *
 * @author Frédéric Moulé
 */
public class IterateMinorNumAlgo extends AbstractIterateAlgo {
    private int limit = 10;

    public IterateMinorNumAlgo() {
        super("minor", 1);
    }

    @Override
    protected void iterate(final Version newVersion, final VersionToken token) {
        if (newVersion == null || token == null) {
            throw new IllegalArgumentException("Soit la version / soit le token est nul !!");
        }
        final String value = token.getValue();
        if (!isNumeric(value)) {
            newVersion.addVersionToken(token);
        } else if (valueOf(value) + 1 == limit) {
            newVersion.setVersionToken(0, newVersion.getVersionToken(0).iterate());
            newVersion.addVersionToken(new VersionToken(token.getSeparator(), "0"));
        } else {
            newVersion.addVersionToken(new VersionToken(token.getSeparator(), Integer.toString(valueOf(value) + 1)));
        }
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }
}
