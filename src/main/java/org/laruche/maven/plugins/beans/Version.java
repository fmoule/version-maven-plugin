package org.laruche.maven.plugins.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la version d'un projet <br />
 *
 * @author Frédéric Moulé
 */
public class Version {
	private List<VersionToken> tokens = new ArrayList<VersionToken>();

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
}
