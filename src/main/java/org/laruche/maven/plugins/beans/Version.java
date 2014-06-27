package org.laruche.maven.plugins.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Classe représentant la version d'un projet <br />
 *
 * @author Frédéric Moulé
 */
public class Version implements Iterable<VersionToken> {
    private static final String SEPARATORS = ".-";
    private List<VersionToken> tokens = new ArrayList<VersionToken>();

    public Version() {
        // EMPTY
    }

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

    // Contructeur de copie
    public Version(final Version oldVersion) {
        if (oldVersion == null) {
            return;
        }
        tokens.addAll(oldVersion.getVersionTokens());
    }

    public void addVersionToken(final VersionToken versionToken) {
        tokens.add(versionToken);
    }

    public Version removeToken(final VersionToken snapshot) {
        final Version newVersion = new Version();
        for (VersionToken token : tokens) {
            if (!snapshot.equals(token)) {
                newVersion.addVersionToken(token);
            }
        }
        return newVersion;
    }

    @Override
    public Iterator<VersionToken> iterator() {
        return tokens.iterator();
    }

    public boolean containsString(final String suffix) {
        return (!isEmpty(this.toString()) && this.toString().contains(suffix));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final Version that = (Version) obj;
        return tokens.equals(that.tokens);
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
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

    public List<VersionToken> getVersionTokens() {
        return new ArrayList<VersionToken>(tokens);
    }

    public VersionToken getVersionToken(final int index) {
        return tokens.get(index);
    }

    public void setVersionToken(final int index, final VersionToken token) {
        this.tokens.set(index, token);
    }
}
