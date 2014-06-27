package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.laruche.maven.plugins.mojo.AbstractProjectMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Commande permettant de spécifier directement la version du projet <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "set-version")
public class SetVersionMojo extends AbstractProjectMojo {

    @Parameter(property = "version", readonly = false, required = true)
    private String version;


    @Override
    protected void modifyPom(final Model projectModel, final Object... parameters) throws Exception {
        if (isEmpty(version)) {
            throw new Exception("La nouvelle version est nulle!");
        }
        if (!isEmpty(projectModel.getVersion())) {
            this.getLog().info("Changement de version : " + projectModel.getVersion() + " => " + version);
            projectModel.setVersion(version);
        }
        if (projectModel.getParent() != null) {
            this.getLog().info("Changement de version : " + projectModel.getParent().getVersion() + " => " + version);
            projectModel.getParent().setVersion(version);
        }
    }


    public void setVersion(final String version) {
        this.version = version;
    }
}
