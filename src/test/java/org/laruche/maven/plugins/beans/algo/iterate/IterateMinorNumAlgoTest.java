package org.laruche.maven.plugins.beans.algo.iterate;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IterateMinorNumAlgoTest {
    private final IterateMinorNumAlgo algo = new IterateMinorNumAlgo();

    @Test
    public void test_compute() {
        assertThat(algo.compute(new Version("1.2.0-SNAPSHOT")), equalTo(new Version("1.3.0-SNAPSHOT")));
        assertThat(algo.compute(new Version("1.9")), equalTo(new Version("2.0")));
    }

    @Test
    public void test_compute_withLimit() {
        algo.setLimit(100);
        assertThat(algo.compute(new Version("1.2.0-SNAPSHOT")), equalTo(new Version("1.3.0-SNAPSHOT")));
        assertThat(algo.compute(new Version("2.9.0-SNAPSHOT")), equalTo(new Version("2.10.0-SNAPSHOT")));
        assertThat(algo.compute(new Version("2.99.0-SNAPSHOT")), equalTo(new Version("3.0.0-SNAPSHOT")));
    }


    @Test
    public void test_compute_inLimits() {
        algo.setLimit(10);
        assertThat(algo.compute(new Version("1.9.2")), equalTo(new Version("2.0.2")));
    }
}