package org.laruche.maven.plugins.mojo.suffix;

import org.junit.After;
import org.junit.Test;
import org.laruche.maven.plugins.mojo.AbstractMojoTest;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AddSnapshotMojoTest extends AbstractMojoTest {
    private static final String BASE_PATH = AddSnapshotMojo.class.getResource(".").getPath();
    private final AddSnapshotMojo mojo = new AddSnapshotMojo();
    private final File pomFile = new File(BASE_PATH + "pom.xml");

    @After
    public void tearDown() throws Exception {
        if (pomFile.exists()) {
            deleteQuietly(pomFile);
        }
    }

    @Test
    public void test_execute() throws Exception {
        mojo.setProject(mockProject("projet-test", "1.0", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.0-SNAPSHOT</version>"), equalTo(true));
    }

    @Test
    public void test_execute_withSuffix() throws Exception {
        mojo.setProject(mockProject("projet-test", "2.0-SNAPSHOT", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>2.0-SNAPSHOT</version>"), equalTo(true));
    }

}