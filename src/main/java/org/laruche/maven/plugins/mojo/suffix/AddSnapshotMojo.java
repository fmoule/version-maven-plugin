package org.laruche.maven.plugins.mojo.suffix;

import org.apache.maven.plugins.annotations.Mojo;
import org.laruche.maven.plugins.beans.algo.others.AddingSuffixAlgorithm;

/**
 * Tache Maven permettant d'ajouter le suffix "snapshot" à la version. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "add-snapshot")
public class AddSnapshotMojo extends AbstractSuffixMojo {

    protected AddSnapshotMojo() {
        super(new AddingSuffixAlgorithm());
    }

}
