package com.gradle.common.model;

public class ProjectDependency extends Dependency {

    public ProjectDependency(final String artifactId) {
        super(artifactId);
    }

    @Override
    public String toString() {
        return super.getArtifactId();
    }
}
