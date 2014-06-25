package org.laruche.maven.plugins.beans.algo.iterate;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.AbstractAlgoTest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IterateMajorNumAlgoTest extends AbstractAlgoTest {
    private final IterateMajorNumAlgo versionAlgo = new IterateMajorNumAlgo();

    @Test
    public void test_execute() {
        assertThat(versionAlgo.compute(new Version("1.2.0")), equalTo(new Version("2.2.0")));
        assertThat(versionAlgo.compute(new Version("1.2.0")), equalTo(new Version("2.2.0")));
    }

}