package org.laruche.maven.plugins.beans;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
        assertThat(token.iterate(), equalTo(new VersionToken(".", "6")));
        token = new VersionToken(".", "value");
        assertThat(token.iterate(), equalTo(new VersionToken(".", "value")));
    }

}