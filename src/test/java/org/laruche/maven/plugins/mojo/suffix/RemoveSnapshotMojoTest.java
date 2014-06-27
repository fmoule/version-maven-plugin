package org.laruche.maven.plugins.mojo.suffix;

import org.apache.maven.project.MavenProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.laruche.maven.plugins.mojo.AbstractMojoTest;
import org.laruche.maven.plugins.mojo.suffix.RemoveSnapshotMojo;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RemoveSnapshotMojoTest extends AbstractMojoTest {
    private static final String BASE_PATH = RemoveSnapshotMojoTest.class.getResource(".").getPath();
    private final RemoveSnapshotMojo mojo = new RemoveSnapshotMojo();
    private File pomFile;

    @Before
    public void setUp() throws Exception {
        pomFile = new File(BASE_PATH + "pom.xml");
    }

    @After
    public void tearDown() throws Exception {
        if (pomFile != null && pomFile.exists()) {
            deleteQuietly(pomFile);
        }
    }

    @Test
    public void test_execute() throws Exception {
        final MavenProject mavenProject = mockProject("projet-test", "1.1-SNAPSHOT", pomFile, false);
        mojo.setProject(mavenProject);
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.1</version>"), equalTo(true));
    }


    @Test
    public void test_execute_noSuffix() throws Exception {
        final MavenProject mavenProject = mockProject("projet-test", "1.1", pomFile, false);
        mojo.setProject(mavenProject);
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.1</version>"), equalTo(true));
    }
}
