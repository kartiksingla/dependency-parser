package com.gradle.dependency.tree.parser;

import com.gradle.common.model.Dependency;
import com.gradle.common.model.ExternalMavenDependency;
import com.gradle.common.model.ProjectDependency;
import com.gradle.parser.version.strategy.DefaultVersionParserStrategy;
import com.gradle.parser.version.strategy.IVersionParserStrategy;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyFactory {

    private static final IVersionParserStrategy versionParserStrategy = new DefaultVersionParserStrategy();
    private static final Pattern MAVEN_DEPENDENCY_PATTERN = Pattern.compile("([^:]+):([^:]+):(\\S+)");
    private static final Pattern PROJECT_DEPENDENCY_PATTERN = Pattern.compile("project\\('([^']+)'\\)");

    public static Dependency createDependency(final String dependencyData) {
        Objects.requireNonNull(dependencyData);
        Matcher projectMatcher = PROJECT_DEPENDENCY_PATTERN.matcher(dependencyData);
        if (projectMatcher.find()) {
            return new ProjectDependency(projectMatcher.group(1));
        }
        Matcher mavenDependencyMatcher = MAVEN_DEPENDENCY_PATTERN.matcher(dependencyData);
        if (mavenDependencyMatcher.find()) {
            final String groupId = mavenDependencyMatcher.group(1).trim();
            final String artifactId = mavenDependencyMatcher.group(2).trim();
            final String version = versionParserStrategy.parseVersion(mavenDependencyMatcher.group(3).trim());
            return new ExternalMavenDependency(groupId, artifactId, version);
        }
        throw new IllegalArgumentException("Unrecognized dependency format: " + dependencyData);
    }

}