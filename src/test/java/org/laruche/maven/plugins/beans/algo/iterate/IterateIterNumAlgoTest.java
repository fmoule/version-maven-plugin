package org.laruche.maven.plugins.beans.algo.iterate;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IterateIterNumAlgoTest {
    private final IterateIterNumAlgo algo = new IterateIterNumAlgo();

    @Test
    public void test_execute() {
        assertThat(algo.compute(new Version("1.2")), equalTo(new Version("1.2")));
        assertThat(algo.compute(new Version("1.2.5")), equalTo(new Version("1.2.6")));
        assertThat(algo.compute(new Version("1.2.9")), equalTo(new Version("1.3.0")));
        assertThat(algo.compute(new Version("1.2.9-SNAPSHOT")), equalTo(new Version("1.3.0-SNAPSHOT")));
    }


    @Test
    public void test_execute_withLimit() {
        algo.setLimit(30);
        assertThat(algo.compute(new Version("2.0")), equalTo(new Version("2.0")));
        assertThat(algo.compute(new Version("2.0.29")), equalTo(new Version("2.1.0")));
        assertThat(algo.compute(new Version("2.5.9-SNAPSHOT")), equalTo(new Version("2.5.10-SNAPSHOT")));
    }

}