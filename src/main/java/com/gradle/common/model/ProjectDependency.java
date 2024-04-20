package com.gradle.common.model;

public class ProjectDependency extends Dependency {

    public ProjectDependency(final String artifactId) {
        super(artifactId);
    }

    @Override
    public String toString() {
        return super.getArtifactId();
    }

    @Override
    public int compareTo(Dependency other) {
        if(!ProjectDependency.class.isAssignableFrom(other.getClass())) return -1;
        return super.getArtifactId().compareTo(other.getArtifactId());
    }
}
