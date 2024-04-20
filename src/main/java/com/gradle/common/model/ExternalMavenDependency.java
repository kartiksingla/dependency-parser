package com.gradle.common.model;

import java.util.Objects;

public class ExternalMavenDependency extends Dependency {

    private final String groupId;

    private final String version;

    public ExternalMavenDependency(final String groupId,final String artifactId,final String version) {
        super(artifactId);
        this.groupId = groupId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object other) {
        if(!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        ExternalMavenDependency that = (ExternalMavenDependency) other;
        return super.equals(other) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groupId);
    }

    @Override
    public String toString() {
        return String.join(":", groupId, super.getArtifactId(), version);
    }

    @Override
    public int compareTo(Dependency o) {
        if(this.equals(o)) {
            return 0;
        }
        if(!ExternalMavenDependency.class.isAssignableFrom(o.getClass())) {
            return 1;
        }
        ExternalMavenDependency other = ExternalMavenDependency.class.cast(o);
        if(!this.groupId.equals(other.groupId)) {
            return this.groupId.compareTo(other.groupId);
        }
        if(!super.getArtifactId().equals(other.getArtifactId())) {
            return super.getArtifactId().compareTo(other.getArtifactId());
        }
        return this.version.compareTo(other.version);
    }
}
