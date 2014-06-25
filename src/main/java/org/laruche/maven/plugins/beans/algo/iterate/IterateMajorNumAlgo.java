package org.laruche.maven.plugins.beans.algo.iterate;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;

/**
 * Algorithme permettant d'itérer le numéro de version majeur. <br />
 *
 * @author Frédéric Moulé
 */
public class IterateMajorNumAlgo extends AbstractIterateAlgo {

    public IterateMajorNumAlgo() {
        super("major", 0);
    }

    @Override
    protected void iterate(final Version newVersion, final VersionToken token) {
        newVersion.addVersionToken(new VersionToken(token.getSeparator(),
                Integer.toString(Integer.valueOf(token.getValue()) + 1)));
    }
}
