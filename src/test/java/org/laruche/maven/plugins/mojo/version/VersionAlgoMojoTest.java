package org.laruche.maven.plugins.mojo.version;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;
import org.laruche.maven.plugins.mojo.AbstractMojoTest;

import java.io.File;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.deleteQuietly;

public class VersionAlgoMojoTest extends AbstractMojoTest {
    public static final String BASE_PATH = VersionAlgoMojoTest.class.getResource(".").getPath();
    private final VersionAlgoMojo mojo = new VersionAlgoMojo();
    private File pomFile;

    @Before
    public void setUp() throws Exception {
        pomFile = new File(BASE_PATH + "pom.xml");
    }

    @After
    public void tearDown() throws Exception {
        if (pomFile.exists()) {
            deleteQuietly(pomFile);
        }
    }

    @Test
    public void test_executeAlgo() throws Exception {
        mojo.setAlgos(asList(new TestAlgo()));
        mojo.setProject(mockProject("projet-test", "1.0.2-SNAPSHOT", pomFile, false));
        mojo.execute();
    }

    ////// Classes statiques ///////

    public static class TestAlgo implements VersionAlgorithm {

        @Override
        public Version compute(final Version oldVersion) {
            return null;
        }
    }
}