package org.laruche.maven.plugins.mojo.suffix;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;
import org.laruche.maven.plugins.mojo.AbstractProjectMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

abstract class AbstractSuffixMojo extends AbstractProjectMojo {
    protected VersionAlgorithm computeAlgo;

    protected AbstractSuffixMojo(final VersionAlgorithm computeAlgo) {
        this.computeAlgo = computeAlgo;
    }

    @Override
    protected void modifyPom(final Model projectModel, final Object... parameters) throws Exception {
        final String oldVersion = parameters[0].toString();
        final Version newVersion = new Version(oldVersion);
        if (!isEmpty(projectModel.getVersion())) {
            projectModel.setVersion(computeAlgo.compute(newVersion).toString());
        }
        final Parent parent = projectModel.getParent();
        if (parent != null) {
            parent.setVersion(computeAlgo.compute(newVersion).toString());
        }
    }
}
