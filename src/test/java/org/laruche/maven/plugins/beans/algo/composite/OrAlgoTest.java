package org.laruche.maven.plugins.beans.algo.composite;

import org.junit.Test;
import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.algo.AbstractAlgoTest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class OrAlgoTest extends AbstractAlgoTest {

    @Test
    public void test_toString() {
       assertThat(new OrAlgo().toString(), notNullValue());
    }

    @Test
    public void test_or() {
        final OrAlgo orAlgo = OrAlgo.or(new TestAlgo(0), new TestAlgo(2));
        assertThat(orAlgo, notNullValue());
        assertThat(orAlgo.compute(new Version("1.0.2")).toString(), equalTo("2.0.2"));
    }

}