package org.laruche.maven.plugins.beans;

public class VersionToken {
	private int value;
	private String separator;

	public VersionToken(final String separator, final int value) {
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
		boolean isEqual = (value == token.value);
		isEqual = isEqual && (separator.equals(token.separator));
		return isEqual;
	}

	@Override
	public int hashCode() {
		final int prime = 17;
		int hascode = value;
		hascode = (prime * hascode) + (separator.hashCode());
		return hascode;
	}

	public String getSeparator() {
		return separator;
	}

	public int getValue() {
		return value;
	}
}
