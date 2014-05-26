package org.laruche.maven.plugins.mojo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AbstractProjectMojoTest {

	@Test
	public void test_test() throws Exception {
		assertThat(true, equalTo(true));
	}

}