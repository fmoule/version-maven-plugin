package org.laruche.maven.plugins.beans.algo;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IdentityAlgoTest {
    private final IdentityAlgo identityAlgo = new IdentityAlgo();

    @Test
    public void test_identical() {
        assertThat(identityAlgo.compute(new Version("1.2.0")), equalTo(new Version("1.2.0")));
        assertThat(identityAlgo.compute(null), nullValue());
    }

}