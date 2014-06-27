package org.laruche.maven.plugins.mojo;

import org.apache.maven.project.MavenProject;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;
import org.laruche.maven.plugins.beans.algo.AbstractVersionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.io.FileUtils.writeLines;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNumeric;

public class AbstractMojoTest {

    protected static MavenProject mockProject(final String artifactId,
                                              final String version,
                                              final File pomFile,
                                              final boolean isModule) throws IOException {
        final MavenProject project = new MavenProject();
        project.setVersion(version);
        project.setFile(pomFile);
        final List<String> contentLines = new ArrayList<String>();
        contentLines.add("<project>");
        if (isModule) {
            contentLines.add("<parent>");
        }
        contentLines.add("<groupId>org.laruche.test</groupId>");
        if (isModule) {
            contentLines.add("<artifactId>projet-test</artifactId>");
        } else {
            contentLines.add("<artifactId>" + artifactId + "</artifactId>");
        }
        contentLines.add("<version>" + version + "</version>");
        if (isModule) {
            contentLines.add("</parent>");
        }
        contentLines.add("</project>");
        writeLines(pomFile, contentLines);
        return project;
    }

    protected static boolean containsLine(final Collection<String> lines, final String content) {
        if (lines == null || lines.isEmpty() || isEmpty(content)) {
            return false;
        }
        final String theContent = content.trim();
        for (String line : lines) {
            if (theContent.equals(line.trim())) {
                return true;
            }
        }
        return false;
    }

    ////// Classes Internes //////

    protected static class TestAlgo extends AbstractVersionAlgorithm {
        private final int index;

        protected TestAlgo(final int index) {
            super("test");
            this.index = index;
        }

        @Override
        public Version compute(final Version oldVersion) {
            if (oldVersion == null) {
                throw new IllegalArgumentException("La version passée en paramètre est nulle");
            }
            int cursor = 0;
            String value;
            final Version newVersion = new Version();
            for (VersionToken token : oldVersion) {
                value = token.getValue();
                if (cursor == index && isNumeric(value)) {
                    newVersion.addVersionToken(new VersionToken(token.getSeparator(),
                            Integer.toString(Integer.valueOf(value) + 1)));
                } else {
                    newVersion.addVersionToken(token);
                }
                cursor++;
            }
            return newVersion;
        }
    }
}
