package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.model.Model;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;
import org.laruche.maven.plugins.mojo.AbstractProjectMojo;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Mojo permettant modifiant les versions du projet et des sous-modules en fonction
 * d'algorithme. <br />
 *
 * @author Frédéric Moulé
 */
public class VersionAlgoMojo extends AbstractProjectMojo {
    private final List<VersionAlgorithm> algorithms = new ArrayList<VersionAlgorithm>();

    @Override
    protected void modifyPom(final Model projectModel, final Object... parameters) throws Exception {
        if (parameters == null || parameters.length == 0) {
            return;
        }
        final String oldVersion;
        if (!isEmpty(projectModel.getVersion())) {
            oldVersion = projectModel.getVersion();
        } else if (projectModel.getParent() != null && !isEmpty(projectModel.getParent().getVersion())) {
            oldVersion = projectModel.getParent().getVersion();
        } else {
            oldVersion = "";
        }
        Version newVersion = new Version(oldVersion);
        for (VersionAlgorithm algorithm : algorithms) {
            newVersion = algorithm.compute(newVersion);
        }

    }

    public void setAlgos(final List<? extends VersionAlgorithm> newAlgorithms) {
        algorithms.clear();
        algorithms.addAll(newAlgorithms);
    }
}
