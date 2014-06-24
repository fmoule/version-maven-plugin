package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.Mojo;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Mojo permettant de gérer les numéros de version des différents modules. <br />
 *
 * @author Frédéric Moulé
 */
@Mojo(name = "remove-snapshot")
public class RemoveSnapshotMojo extends AbstractProjectMojo {
    protected static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

    @Override
    protected void modifyPom(final Model projectModel, final Object... parameters) {
        final String oldVersion = parameters[0].toString();
        final String newVersion = convertVersion(oldVersion);
        if (!isEmpty(projectModel.getVersion())) {
            projectModel.setVersion(newVersion);
        }
        if (projectModel.getParent() != null) {
            projectModel.getParent().setVersion(newVersion);
        }
    }

    protected String convertVersion(final String oldVersion) {
        if (isEmpty(oldVersion)) {
            return oldVersion;
        }
        return oldVersion.replace(SNAPSHOT_SUFFIX, "");
    }

}
