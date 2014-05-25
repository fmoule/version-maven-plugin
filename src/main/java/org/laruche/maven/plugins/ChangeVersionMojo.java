package org.laruche.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Mojo permettant de gérer les numéros de version des différents modules. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "change-version")
public class ChangeVersionMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
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
