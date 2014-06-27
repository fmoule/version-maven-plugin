package org.laruche.maven.plugins.beans;

import static org.apache.commons.lang.StringUtils.isNumeric;

public class VersionToken {
    private String value;
    private String separator;

    public VersionToken(final String separator, final String value) {
        this.value = value;
        this.separator = separator;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final VersionToken token = (VersionToken) obj;
        boolean isEqual = (value == null ? token.value == null : value.equals(token.value));
        isEqual = isEqual && (separator == null ? token.separator == null : separator.equals(token.separator));
        return isEqual;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int hascode = (value == null ? 0 : value.hashCode());
        hascode = (prime * hascode) + (separator == null ? 0 : separator.hashCode());
        return hascode;
    }

    public VersionToken iterate() {
        final String tokenValue = getValue();
        if (!isNumeric(tokenValue)) {
            return this;
        } else {
            return new VersionToken(getSeparator(), Integer.toString(Integer.valueOf(tokenValue) + 1));
        }
    }

    public String getSeparator() {
        return separator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
