package org.laruche.maven.plugins.mojo.version;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.laruche.maven.plugins.mojo.AbstractMojoTest;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class VersionAlgoMojoTest extends AbstractMojoTest {
    public static final String BASE_PATH = VersionAlgoMojoTest.class.getResource(".").getPath();
    private final VersionAlgoMojo mojo = new VersionAlgoMojo();
    private File pomFile;

    @Before
    public void setUp() throws Exception {
        pomFile = new File(BASE_PATH + "pom.xml");
        mojo.setLimit("10");
    }

    @After
    public void tearDown() throws Exception {
        if (pomFile.exists()) {
            deleteQuietly(pomFile);
        }
    }

    @Test
    public void test_execute_withNoCommand() throws Exception {
        mojo.setCommand("");
        mojo.setProject(mockProject("projet-test", "1.2.0", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.2.0</version>"), equalTo(true));
    }

    @Test
    public void test_execute_case1() throws Exception {
        mojo.setCommand("major & iter");
        mojo.setProject(mockProject("projet-test", "1.2.0-SNAPSHOT", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>2.2.1-SNAPSHOT</version>"), equalTo(true));
    }

    @Test
    public void test_execute_case2() throws Exception {
        mojo.setCommand("iter");
        mojo.setProject(mockProject("projet-test", "1.2.0-SNAPSHOT", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.2.1-SNAPSHOT</version>"), equalTo(true));
    }

    @Test
    public void test_execute_case3() throws Exception {
        mojo.setCommand("iter");
        mojo.setProject(mockProject("projet-test", "1.2.0-SNAPSHOT", pomFile, false));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>1.2.1-SNAPSHOT</version>"), equalTo(true));
    }
}