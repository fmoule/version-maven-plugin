package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.IOUtils.closeQuietly;

public abstract class AbstractProjectMojo extends AbstractMojo {
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
            final Model projectModel = readModelFromPom(pomFile);
            modifyPom(projectModel, getProjectParameters(project));
            writeModelToPom(projectModel);
        } catch (final Exception exception) {
            rollBack(pomFile);
            throw new MojoFailureException(exception.getMessage(), exception);
        } finally {
            deleteQuietly(copiedPomFile);
        }
    }

    protected String[] getProjectParameters(final MavenProject project) {
        return new String[]{
                project.getVersion()
        };
    }

    /**
     * Méthode permettant de modifier le modèle de projet en fonction des différents paramètres.
     *
     * @param projectModel : modèle de projet
     * @param parameters   : paramètres du projet
     * @throws Exception : En cas de problème
     */
    protected abstract void modifyPom(final Model projectModel, final Object... parameters) throws Exception;


    protected Model readModelFromPom(final File pomFile) throws Exception {
        final MavenXpp3Reader reader = new MavenXpp3Reader();
        FileInputStream pomInputStream = null;
        try {
            getLog().debug("POM du projet : " + pomFile.getAbsolutePath());
            pomInputStream = new FileInputStream(pomFile);
            return reader.read(pomInputStream);
        } finally {
            closeQuietly(pomInputStream);
        }
    }

    protected void writeModelToPom(final Model projectModel) throws IOException {
        FileOutputStream pomOutputStream = null;
        try {
            pomOutputStream = new FileOutputStream(this.getProject().getFile(), false);
            final MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(pomOutputStream, projectModel);
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

    protected void setProject(final MavenProject project) {
        this.project = project;
    }
}
