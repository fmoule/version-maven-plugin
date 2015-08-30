package org.laruche.maven.plugins.mojo.iterate;

import org.apache.maven.project.MavenProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FileUtils.writeLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DefaultIterateMojoTest {
    private DefaultIterateMojo mojo = new DefaultIterateMojo();
    private MavenProject project;
    private final File testDir = Paths.get("test").toFile();


    protected static void initializeVersion(final MavenProject project, final File pomFile, final String version) throws IOException {
        writeLines(pomFile, asList("<project>",
                "<groupId>com.test.projet</groupId>",
                "<artifactId>test</artifactId>",
                "<version>" + version + "</version>",
                "</project>"));
        project.setVersion(version);
    }

    @Before
    public void setUp() throws Exception {
        project = new MavenProject();
        forceMkdir(testDir);
        final File pomFile = new File(testDir, "pom.xml");
        initializeVersion(project, pomFile, "1.2.3");
        project.setFile(pomFile);
    }

    @After
    public void tearDown() throws Exception {
        forceDelete(testDir);
    }

    @Test
    public void test_existPom() {
        assertThat(project.getFile().exists(), equalTo(true));
    }


    @Test
    public void test_defaultLimit() throws Exception {
        assertThat(mojo.getDigitLimit(), equalTo(10));
    }


    @Test
    public void test_withLimit() throws Exception {
        mojo.setDigitLimit(100);
        initializeVersion(project, project.getFile(), "1.3.99-SNAPSHOT");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("1.3.99"));
        initializeVersion(project, project.getFile(), "1.3.99");
        mojo.execute();
        assertThat(project.getVersion(), equalTo("1.4.0-SNAPSHOT"));
    }

    @Test
    public void test_case1() throws Exception {
        initializeVersion(project, project.getFile(), "1.2.3");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("1.2.4-SNAPSHOT"));
    }

    @Test
    public void test_case2() throws Exception {
        initializeVersion(project, project.getFile(), "1.2.9");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("1.3.0-SNAPSHOT"));
    }

    @Test
    public void test_case3() throws Exception {
        initializeVersion(project, project.getFile(), "2.3.5-SNAPSHOT");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("2.3.5"));
    }

    @Test
    public void test_case4() throws Exception {
        initializeVersion(project, project.getFile(), "2.9.9");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("3.0.0-SNAPSHOT"));
    }


    @Test
    public void test_case5() throws Exception {
        initializeVersion(project, project.getFile(), "2.9.9-SNAPSHOT");
        mojo.setProject(project);
        mojo.execute();
        assertThat(project.getVersion(), equalTo("2.9.9"));
    }
}