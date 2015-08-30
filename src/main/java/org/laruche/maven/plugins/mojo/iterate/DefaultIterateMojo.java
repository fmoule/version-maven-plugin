package org.laruche.maven.plugins.mojo.iterate;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;
import org.laruche.maven.plugins.mojo.AbstractModifyVersionMojo;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.laruche.maven.plugins.beans.VersionToken.SNAPSHOT_TOKEN;

@Mojo(name = "release-version")
public class DefaultIterateMojo extends AbstractModifyVersionMojo {
    public static final VersionToken ZERO_TOKEN = new VersionToken(".", "0");
    private int digitLimit = 10;

    @Override
    protected void modifyPom(final MavenProject project) throws Exception {
        if (project == null || isEmpty(project.getVersion())) {
            throw new Exception("La version du projet est vide !!");
        }
        final Version version = new Version(project.getVersion());
        final boolean isSnapshot = version.isSnapshot();
        if (isSnapshot) {
            version.removeToken(SNAPSHOT_TOKEN);
        } else {
            Integer tokenValue;
            for (int i = version.getNbTokens() - 1; i >= 0; i--) {
                tokenValue = Integer.valueOf(version.getVersionToken(i).getValue()) + 1;
                if (tokenValue >= digitLimit) {
                    version.setVersionToken(i, ZERO_TOKEN);
                } else {
                    version.setVersionToken(i, new VersionToken(".", tokenValue.toString()));
                    break;
                }
            }
            version.addVersionToken(SNAPSHOT_TOKEN);
        }
        project.setVersion(version.toString());
    }

    public int getDigitLimit() {
        return digitLimit;
    }

    public void setDigitLimit(final int digitLimit) {
        this.digitLimit = digitLimit;
    }
}
