package org.laruche.maven.plugins.beans.algo;

import org.laruche.maven.plugins.beans.Version;

/**
 * Interface permettant de représentant tous les algorithmes manipulant les numéros de versions.
 *
 * @author Frédéric Moulé
 */
public interface VersionAlgorithm {

    /**
     * Retourne le label utlisé pour désigner l'algorithme. <br />
     *
     * @return label utilisé
     */
    public String getLabel();

    /**
     * Méthode transformant et manipulant la version. <br />
     *
     * @param oldVersion : ancienne version
     * @return nouvelle version
     */
    public Version compute(final Version oldVersion);

}