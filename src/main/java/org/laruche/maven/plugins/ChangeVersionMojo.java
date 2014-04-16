package org.laruche.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * Mojo permettant de gérer les numéros de version des différents modules. <br />
 *
 * @goal change-version
 * @author Frédéric Moulé
 */
@SuppressWarnings("JavaDoc")
public class ChangeVersionMojo extends AbstractMojo {

    /**
     * @parameter default-value="${project}"
     * @required
     */
    private MavenProject project;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Ceci est un test");
    }

    MavenProject getProject() {
        return project;
    }

    void setProject(final MavenProject project) {
        this.project = project;
    }
}
