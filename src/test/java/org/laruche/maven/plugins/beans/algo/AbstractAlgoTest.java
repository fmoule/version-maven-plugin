package org.laruche.maven.plugins.beans.algo;

import org.laruche.maven.plugins.beans.Version;
import org.laruche.maven.plugins.beans.VersionToken;

import static org.apache.commons.lang.StringUtils.isNumeric;


public class AbstractAlgoTest {

    ////// Classes Internes ///////

    protected static class TestAlgo extends AbstractVersionAlgorithm {
        private final int index;

        public TestAlgo(final int index) {
            super("test");
            this.index = index;
        }

        @Override
        public Version compute(final Version oldVersion) {
            if (oldVersion == null) {
                throw new IllegalArgumentException("La version passée en paramètre est nulle");
            }
            int cursor = 0;
            String value;
            final Version newVersion = new Version();
            for (VersionToken token : oldVersion) {
                value = token.getValue();
                if (cursor == index && isNumeric(value)) {
                    newVersion.addVersionToken(new VersionToken(token.getSeparator(),
                            Integer.toString(Integer.valueOf(value) + 1)));
                } else {
                    newVersion.addVersionToken(token);
                }
                cursor++;
            }
            return newVersion;
        }
    }
}
