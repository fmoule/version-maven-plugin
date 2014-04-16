package org.laruche.maven.plugins;

import org.apache.maven.project.MavenProject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChangeVersionMojoTest {

    private static MavenProject mockProject(final String version) {
        final MavenProject project = mock(MavenProject.class);
        when(project.getVersion()).thenReturn(version);
        return project;
    }

    @Test
    public void test_snapshotVersion() {
        final MavenProject mavenProject = mockProject("1.1-SNAPSHOT");
        assertThat(mavenProject.getVersion(), equalTo("1.1-SNAPSHOT"));
    }
}
