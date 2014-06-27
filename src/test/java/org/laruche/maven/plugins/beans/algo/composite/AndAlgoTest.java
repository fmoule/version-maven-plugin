package org.laruche.maven.plugins.beans.algo.composite;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.AbstractAlgoTest;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.laruche.maven.plugins.beans.algo.composite.AndAlgo.and;


public class AndAlgoTest extends AbstractAlgoTest {


    @Test
    public void test_equalsAndHashcode() {
        assertThat(new AndAlgo(), equalTo(new AndAlgo()));
        assertThat(new AndAlgo().hashCode(), equalTo(new AndAlgo().hashCode()));
        assertThat(new AndAlgo(), not(equalTo((VersionAlgorithm) new OrAlgo())));
    }


    @Test
    public void test_compute() {
        AndAlgo andAlgo = and(new TestAlgo(0), new TestAlgo(1));
        assertThat(andAlgo.compute(new Version("1.2.0")).toString(), equalTo("2.3.0"));
        andAlgo = and();
        assertThat(andAlgo.compute(new Version("1.2.0")).toString(), equalTo("1.2.0"));
    }


    @Test
    public void test_addAlgo() {
        final AndAlgo algo = new AndAlgo();
        algo.addAlgo(new TestAlgo(1));
        algo.addAlgo(new TestAlgo(2));
        assertThat(algo.compute(new Version("3.5.6")), equalTo(new Version("3.6.7")));
    }
}