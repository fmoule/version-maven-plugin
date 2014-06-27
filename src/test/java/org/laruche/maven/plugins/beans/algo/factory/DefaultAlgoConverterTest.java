package org.laruche.maven.plugins.beans.algo.factory;

import org.junit.Test;
import org.laruche.maven.plugins.beans.algo.VersionAlgorithm;
import org.laruche.maven.plugins.beans.algo.iterate.IterateIterNumAlgo;
import org.laruche.maven.plugins.beans.algo.iterate.IterateMajorNumAlgo;
import org.laruche.maven.plugins.beans.algo.iterate.IterateMinorNumAlgo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.laruche.maven.plugins.beans.algo.composite.AndAlgo.and;
import static org.laruche.maven.plugins.beans.algo.composite.OrAlgo.or;

public class DefaultAlgoConverterTest {
    private final DefaultAlgoConverter converter = new DefaultAlgoConverter();

    @Test
    public void test_createAlgo() {
        assertThat(converter.createAlgorithme("minor & iter"), equalTo((VersionAlgorithm) and(new IterateMinorNumAlgo(), new IterateIterNumAlgo())));
        assertThat(converter.createAlgorithme("minor || iter"), equalTo((VersionAlgorithm) or(new IterateMinorNumAlgo(), new IterateIterNumAlgo())));
        assertThat(converter.createAlgorithme("(major & minor) || iter"),
                equalTo((VersionAlgorithm) or(and(new IterateMajorNumAlgo(), new IterateMinorNumAlgo()), new IterateIterNumAlgo())));
    }

}