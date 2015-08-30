package org.laruche.maven.plugins.beans;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.laruche.maven.plugins.beans.VersionToken.SNAPSHOT_TOKEN;

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

    @Test
    public void test_equalsAndHashCode() {
        final Version version = new Version("1.0.2");
        assertThat(version, equalTo(new Version("1.0.2")));
        assertThat(version.hashCode(), equalTo(new Version("1.0.2").hashCode()));
        assertThat(version, not(equalTo(new Version("1.5.2"))));
        assertThat(version, not(equalTo(new Version("1.0.2-SNAPSHOT"))));
    }


    @Test
    public void test_isSnapshot() {
        assertThat(new Version("1.2.5-SNAPSHOT").isSnapshot(), equalTo(true));
        assertThat(new Version("1.2.8").isSnapshot(), equalTo(false));
    }


    @Test
    public void test_removeToken() {
        assertThat(new Version("1.2.3-SNAPSHOT").removeToken(SNAPSHOT_TOKEN), equalTo(new Version("1.2.3")));
        assertThat(new Version("1.2.3").removeToken(new VersionToken(".", "2")), equalTo(new Version("1.3")));
    }
}