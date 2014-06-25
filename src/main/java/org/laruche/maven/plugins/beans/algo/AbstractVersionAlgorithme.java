package org.laruche.maven.plugins.beans.algo;

public abstract class AbstractVersionAlgorithme implements VersionAlgorithm {
    private String label;

    protected AbstractVersionAlgorithme(final String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return (label == null ? 0 : label.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof AbstractVersionAlgorithme)) {
            return false;
        }
        final AbstractVersionAlgorithme that = (AbstractVersionAlgorithme) obj;
        return (label == null ? that.label == null : label.equals(that.label));
    }
}
