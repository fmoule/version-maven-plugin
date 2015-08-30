package org.laruche.maven.plugins.beans.algo.iterate;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * Algo permettant d'itérer le numéro d'itération de la version. <br />
 *
 * @author Frédéric Moulé
 */
public class IterateIterNumAlgo extends AbstractIterateAlgo {
    private int limit = 10;

    public IterateIterNumAlgo() {
        super("iter", 2);
    }

    public IterateIterNumAlgo(final int digitLimit) {
        super("iter", 2);
        this.limit = digitLimit;
    }

    @Override
    protected void iterate(final Version newVersion, final VersionToken token) {
        if (newVersion == null || token == null) {
            throw new IllegalArgumentException("Soit la version, soit le token est nul !");
        }
        final String value = token.getValue();
        if (!isNumeric(value)) {
            newVersion.addVersionToken(token);
        } else if (valueOf(value) + 1 == limit) {
            newVersion.setVersionToken(index - 1, newVersion.getVersionToken(index - 1).iterateValue());
            newVersion.addVersionToken(new VersionToken(token.getSeparator(), "0"));
        } else {
            newVersion.addVersionToken(token.iterateValue());
        }
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }
}
