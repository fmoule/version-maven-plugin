package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.Parent;
import org.apache.maven.project.MavenProject;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import static org.apache.commons.lang.StringUtils.isEmpty;

public abstract class AbstractVersionAlgoMojo extends AbstractModifyVersionMojo {
    protected VersionAlgorithm computeAlgo;

    protected AbstractVersionAlgoMojo(final VersionAlgorithm computeAlgo) {
        this.computeAlgo = computeAlgo;
    }

    @Override
    protected void modifyPom(final MavenProject project) throws Exception {
        if (isEmpty(project.getVersion())) {
            throw new Exception("La version du project est vide");
        }
        final Version newVersion = computeAlgo.compute(new Version(project.getVersion()));
        project.setVersion(newVersion.toString());
        final Parent parent = project.getModel().getParent();
        if (parent != null) {
            parent.setVersion(newVersion.toString());
        }
    }
}
