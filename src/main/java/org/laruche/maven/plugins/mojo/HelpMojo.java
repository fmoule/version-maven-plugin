package org.laruche.maven.plugins.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

@Mojo(name = "help")
public class HelpMojo extends AbstractModifyVersionMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (!this.getProject().isExecutionRoot()) {
			return;
		}
		getLog().info("========================");
		getLog().info("Options du plugin : version-plugin-maven");
		getLog().info("========================");
	}

	@Override
	protected void modifyPom(MavenProject project) {
		// DO NOTHING !!
	}
}
