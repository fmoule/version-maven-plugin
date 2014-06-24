package org.laruche.maven.plugins.mojo;

import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.writeLines;
import static org.apache.commons.lang.StringUtils.isEmpty;

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
}
