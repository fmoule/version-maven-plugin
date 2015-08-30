package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.factory.AlgoConvert;
import org.laruche.maven.plugins.beans.algo.factory.DefaultAlgoConverter;
import org.laruche.maven.plugins.mojo.AbstractModifyVersionMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Mojo permettant modifiant les versions du projet et des sous-modules en fonction
 * d'algorithme. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "compute-version")
public class VersionAlgoMojo extends AbstractModifyVersionMojo {
    private AlgoConvert factory = new DefaultAlgoConverter();

    @Parameter(property = "algo", readonly = false, required = true)
    private String command;

    @Parameter(property = "limit", readonly = false, defaultValue = "10")
    private String limit;

    @Override
    protected void modifyPom(final MavenProject project) throws Exception {
        if (isEmpty(limit)) {
            throw new Exception("La limite des numéros est nul !!");
        }
        final String oldVersion;
        if (!isEmpty(project.getVersion())) {
            oldVersion = project.getVersion();
        } else if (project.getParent() != null && !isEmpty(project.getParent().getVersion())) {
            oldVersion = project.getParent().getVersion();
        } else {
            oldVersion = "";
        }
        final Version newVersion = factory.createAlgorithme(command).compute(new Version(oldVersion));
        this.getLog().info("Changement de version " + oldVersion + " => " + newVersion);
        if (!isEmpty(project.getVersion())) {
            project.setVersion(newVersion.toString());
        }
        if (project.getParent() != null) {
            project.getParent().setVersion(newVersion.toString());
        }
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public void setLimit(final String limit) {
        this.limit = limit;
    }
}
