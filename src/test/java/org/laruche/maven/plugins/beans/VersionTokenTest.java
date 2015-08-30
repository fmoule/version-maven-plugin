package org.laruche.maven.plugins.beans;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.laruche.maven.plugins.beans.VersionToken.SNAPSHOT_TOKEN;

public class VersionTokenTest {


    @Test
    public void test_equals() throws Exception {
        assertThat(new VersionToken(null, "12"), equalTo(new VersionToken(null, "12")));
        assertThat(new VersionToken(null, "12").hashCode(), equalTo(new VersionToken(null, "12").hashCode()));
        assertThat(new VersionToken("-", "12"), equalTo(new VersionToken("-", "12")));
        assertThat(new VersionToken("-", "12").hashCode(), equalTo(new VersionToken("-", "12").hashCode()));
    }


    @Test
    public void test_iterate() {
        VersionToken token = new VersionToken(".", "5");
        assertThat(token.iterateValue(), equalTo(new VersionToken(".", "6")));
        token = new VersionToken(".", "value");
        assertThat(token.iterateValue(), equalTo(new VersionToken(".", "value")));
    }


    @Test
    public void test_snapshotToken() {
        assertThat(new VersionToken("-", "SNAPSHOT"), equalTo(SNAPSHOT_TOKEN));
        assertThat(new VersionToken("-", "SNAPSHOT").hashCode(), equalTo(SNAPSHOT_TOKEN.hashCode()));
    }

}