package org.laruche.maven.plugins.beans;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class VersionTest {


    @Test
    public void test_parseAll() {
        final Version version = new Version("1.0.2");
        final List<VersionToken> tokens = version.getVersionTokens();
        assertThat(tokens.size(), equalTo(3));
        assertThat(tokens.get(0), equalTo(new VersionToken(null, "1")));
        assertThat(tokens.get(1), equalTo(new VersionToken(".", "0")));
        assertThat(tokens.get(2), equalTo(new VersionToken(".", "2")));
    }

}