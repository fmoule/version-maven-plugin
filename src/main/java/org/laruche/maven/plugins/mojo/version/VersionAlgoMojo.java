package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.factory.AlgoConvert;
import org.laruche.maven.plugins.beans.algo.factory.DefaultAlgoConverter;
import org.laruche.maven.plugins.mojo.AbstractProjectMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Mojo permettant modifiant les versions du projet et des sous-modules en fonction
 * d'algorithme. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "compute-version")
public class VersionAlgoMojo extends AbstractProjectMojo {
    private AlgoConvert factory = new DefaultAlgoConverter();

    @Parameter(property = "algo", readonly = false, required = true)
    private String command;

    @Parameter(property = "limit", readonly = false, defaultValue = "10")
    private String limit;

    @Override
    protected void modifyPom(final Model projectModel, final Object... parameters) throws Exception {
        if (isEmpty(limit)) {
            throw new Exception("La limite des numéros est nul !!");
        }
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
        final Version newVersion = factory.createAlgorithme(command).compute(new Version(oldVersion));
        this.getLog().info("Changement de version " + oldVersion + " => " + newVersion);
        if (!isEmpty(projectModel.getVersion())) {
            projectModel.setVersion(newVersion.toString());
        }
        if (projectModel.getParent() != null) {
            projectModel.getParent().setVersion(newVersion.toString());
        }
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public void setLimit(final String limit) {
        this.limit = limit;
    }
}
