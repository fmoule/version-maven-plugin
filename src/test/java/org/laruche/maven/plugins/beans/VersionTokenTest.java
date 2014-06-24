package org.laruche.maven.plugins.beans;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class VersionTokenTest {


	@Test
	public void test_equals() throws Exception {
		assertThat(new VersionToken("-", 12), equalTo(new VersionToken("-", 12)));
		assertThat(new VersionToken("-", 12).hashCode(), equalTo(new VersionToken("-", 12).hashCode()));
	}

}