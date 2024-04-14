package com.gradle.model;

import java.util.Objects;

public class ExternalMavenDependency extends Dependency {

    String groupId;

    String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
        return String.join(":", groupId, artifactId, version);
    }
}
