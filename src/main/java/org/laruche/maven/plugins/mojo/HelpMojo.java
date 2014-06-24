package org.laruche.maven.plugins.mojo;

import org.apache.maven.model.Model;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "help")
public class HelpMojo extends AbstractProjectMojo {

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
	protected void modifyPom(final Model projectModel, final Object... parameters) {
		// DO NOTHING !!
	}
}
