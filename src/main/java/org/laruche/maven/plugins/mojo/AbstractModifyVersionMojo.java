package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.IOUtils.closeQuietly;

public abstract class AbstractModifyVersionMojo extends AbstractMojo {
    protected static final String BAK_SUFFIX = ".bak";

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final MavenProject project = this.getProject();
        final File pomFile = project.getFile();
        final File copiedPomFile = getCopiedPomFile(pomFile);
        try {
            copyFile(pomFile, copiedPomFile);
            modifyPom(project);
            writeModelToPom(project);
        } catch (final Exception exception) {
            rollBack(pomFile);
            this.getLog().error("=== ERREUR : " + exception.getMessage());
            throw new MojoFailureException(exception.getMessage(), exception);
        } finally {
            deleteQuietly(copiedPomFile);
        }
    }

    /**
     * Méthode permettant de modifier le modèle de projet en fonction des différents paramètres.
     *
     * @param project
     * @throws Exception : En cas de problème
     */
    protected abstract void modifyPom(final MavenProject project) throws Exception;


    protected static void writeModelToPom(final MavenProject project) throws IOException {
        FileOutputStream pomOutputStream = null;
        try {
            pomOutputStream = new FileOutputStream(project.getFile(), false);
            final MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(pomOutputStream, project.getModel());
        } finally {
            closeQuietly(pomOutputStream);
        }
    }

    protected void rollBack(final File pomFile) {
        final File copiedPomFile = getCopiedPomFile(pomFile);
        try {
            copyFile(copiedPomFile, pomFile);
        } catch (final IOException ioException) {
            throw new RuntimeException(ioException.getMessage(), ioException);
        }
    }

    private File getCopiedPomFile(final File pomFile) {
        return new File(pomFile.getParent(), pomFile.getName() + BAK_SUFFIX);
    }

    protected MavenProject getProject() {
        return project;
    }

    public void setProject(final MavenProject project) {
        this.project = project;
    }
}
