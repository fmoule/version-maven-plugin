package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public final class AbstractProjectMojoTest extends AbstractMojoTest {
    public static final String BASE_PATH = AbstractProjectMojo.class.getResource(".").getPath();
    private TestMojo mojo = new TestMojo();
    private File pomFile;

    @Before
    public void setUp() throws Exception {
        pomFile = new File(BASE_PATH + "pom.xml");
    }


    @After
    public void tearDown() throws Exception {
        if (pomFile != null && pomFile.exists()) {
            forceDelete(pomFile);
        }
    }

    @Test
    public void test_executeMojo() throws Exception {
        mojo.setNewVersion("2.0");
        mojo.setProject(mockProject("projet-test", "1.0", pomFile, false));
        mojo.execute();
        final List<String> lines = readLines(pomFile);
        assertThat(containsLine(lines, "<version>2.0</version>"), equalTo(true));
        assertThat(containsLine(lines, "<version>1.0</version>"), equalTo(false));
    }


    @Test
    public void test_executeMojo_throwException() throws Exception {
        mojo.setNewVersion("2.0");
        mojo.setProject(mockProject("projet-test", "1.0", pomFile, false));
        mojo.throwsException(true);
        try {
            mojo.execute();
        } catch (final MojoFailureException mojoFailureException) {
            assertThat(mojoFailureException.getMessage(), equalTo("Exception : test"));
        }
        final List<String> lines = readLines(pomFile);
        assertThat(containsLine(lines, "<version>2.0</version>"), equalTo(false));
        assertThat(containsLine(lines, "<version>1.0</version>"), equalTo(true));
    }


    @Test
    public void test_executeMojo_withSubModules() throws Exception {
        mojo.setNewVersion("2.0");
        mojo.setProject(mockProject("module", "1.0", pomFile, true));
        mojo.execute();
        assertThat(containsLine(readLines(pomFile), "<version>2.0</version>"), equalTo(true));
    }


    ///// Classes internes //////

    private static class TestMojo extends AbstractProjectMojo {
        private String newVersion;
        private boolean throwsException = false;

        private TestMojo() {
            // EMPTY
        }


        @Override
        protected void modifyPom(final Model projectModel, final Object... parameters) throws Exception {
            if (throwsException) {
                throw new Exception("Exception : test");
            }
            if (parameters == null || parameters.length == 0) {
                return;
            }
            if (!isEmpty(projectModel.getVersion())) {
                projectModel.setVersion(newVersion);
            }
            if (projectModel.getParent() != null) {
                projectModel.getParent().setVersion(newVersion);
            }
        }

        public void setNewVersion(final String newVersion) {
            this.newVersion = newVersion;
        }

        public void throwsException(final boolean throwsException) {
            this.throwsException = throwsException;
        }
    }
}