package org.laruche.maven.plugins.beans.algo.composite;

import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import java.util.List;

/**
 * Interface représentant tous les algorithmes permettant de regrouper d'autres algorithmes
 * avec un liaison logique (OR, AND, NOR,.., etc)
 *
 * @author Frédéric Moulé
 */
public interface CompositeVersionAlgorithm extends VersionAlgorithm {

    /**
     * Ajouts d'un groupe d'algo; <br />
     *
     * @param algos collection d'algos à ajouter.
     */
    public void addAlgos(final List<? extends VersionAlgorithm> algos);

    /**
     * Ajout d'un algorithme. <br />
     *
     * @param algorithm : Algorithme à ajouter
     */
    public void addAlgo(final VersionAlgorithm algorithm);

    /**
     * Retourne la liste ordonnée des algorithmes. <br />
     *
     * @return liste des algorithmes
     */
    public List<VersionAlgorithm> getAlgorithms();
}
