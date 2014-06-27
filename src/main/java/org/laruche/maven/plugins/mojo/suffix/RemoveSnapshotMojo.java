package org.laruche.maven.plugins.mojo.suffix;

import org.apache.maven.plugins.annotations.Mojo;
import org.laruche.maven.plugins.beans.algo.others.RemovingSuffixAlgorithm;

/**
 * Mojo permettant de gérer les numéros de version des différents modules. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "remove-snapshot")
public class RemoveSnapshotMojo extends AbstractSuffixMojo {

    protected RemoveSnapshotMojo() {
        super(new RemovingSuffixAlgorithm());
    }

}
