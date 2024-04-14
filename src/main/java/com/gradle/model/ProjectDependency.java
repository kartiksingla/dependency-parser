package com.gradle.model;

public class ProjectDependency extends Dependency {

    protected ProjectDependency(final String artifactId) {
        super(artifactId);
    }

    @Override
    public String toString() {
        return super.getArtifactId();
    }
}
