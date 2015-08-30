package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.laruche.maven.plugins.mojo.AbstractModifyVersionMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Commande permettant de spécifier directement la version du projet <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "set-version")
public class SetVersionMojo extends AbstractModifyVersionMojo {

    @Parameter(property = "version", readonly = false, required = true)
    private String version;


    @Override
    protected void modifyPom(final MavenProject project) throws Exception {
        if (isEmpty(version)) {
            throw new Exception("La nouvelle version est nulle!");
        }
        if (!isEmpty(project.getVersion())) {
            this.getLog().info("Changement de version : " + project.getVersion() + " => " + version);
            project.setVersion(version);
        }
        if (project.getParent() != null) {
            this.getLog().info("Changement de version : " + project.getParent().getVersion() + " => " + version);
            project.getParent().setVersion(version);
        }
    }


    public void setVersion(final String version) {
        this.version = version;
    }
}
