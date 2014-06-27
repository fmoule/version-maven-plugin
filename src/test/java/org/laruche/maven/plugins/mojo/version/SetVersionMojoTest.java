package org.laruche.maven.plugins.mojo.version;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Test;
import org.laruche.maven.plugins.mojo.AbstractMojoTest;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SetVersionMojoTest extends AbstractMojoTest {
    private static final String BASE_PATH = SetVersionMojoTest.class.getResource(".").getPath();
    private final File pomFile = new File(BASE_PATH + "pom.xml");
    private final SetVersionMojo mojo = new SetVersionMojo();

    @After
    public void tearDown() throws Exception {
        if (pomFile.exists()) {
            deleteQuietly(pomFile);
        }
    }

    @Test
    public void test_execute() throws Exception {
        mojo.setProject(mockProject("projet-test", "3.1", pomFile, false));
        mojo.setVersion("3.2-SNAPSHOT");
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>3.2-SNAPSHOT</version>"), equalTo(true));
    }


    @Test
    public void test_execute_withNoVersion() throws Exception {
        mojo.setProject(mockProject("projet-test", "3.1", pomFile, false));
        mojo.setVersion(null);
        try {
            mojo.execute();
            fail("L'appel à la méthode execute doit échouer");
        } catch (final MojoFailureException mojoException) {
            assertThat(mojoException.getMessage(), equalTo("La nouvelle version est nulle!"));
        }
    }

}