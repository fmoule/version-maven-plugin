package org.laruche.maven.plugins.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Classe représentant la version d'un projet <br />
 *
 * @author Frédéric Moulé
 */
public class Version implements Iterable<VersionToken> {
    private static final String SEPARATORS = ".-";
    private List<VersionToken> tokens = new ArrayList<VersionToken>();

    public Version(final String oldVersion) {
        final StringTokenizer tokenizer = new StringTokenizer(oldVersion, SEPARATORS, true);
        int cursor = 0;
        String theToken;
        VersionToken token = null;
        while (tokenizer.hasMoreTokens()) {
            theToken = tokenizer.nextToken();
            if (cursor == 0) {
                token = new VersionToken(null, theToken);
                tokens.add(token);
            } else if (SEPARATORS.contains(theToken)) {
                token = new VersionToken(theToken, null);
            } else {
                token.setValue(theToken);
                tokens.add(token);
            }
            cursor++;
        }
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        VersionToken token;
        for (int i = 0; i < tokens.size(); i++) {
            token = tokens.get(i);
            if (i > 0) {
                buffer.append(token.getSeparator());
            }
            buffer.append(token.getValue());
        }
        return buffer.toString();
    }


    @Override
    public Iterator<VersionToken> iterator() {
        return tokens.iterator();
    }

    public List<VersionToken> getVersionTokens() {
        return new ArrayList<VersionToken>(tokens);
    }
}
