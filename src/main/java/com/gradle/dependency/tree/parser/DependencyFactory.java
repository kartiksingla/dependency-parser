package com.gradle.dependency.tree.parser;

import com.gradle.common.model.Dependency;
import com.gradle.common.model.ExternalMavenDependency;
import com.gradle.common.model.ProjectDependency;
import com.gradle.parser.strategy.version.DefaultVersionParserStrategy;
import com.gradle.parser.strategy.version.IVersionParserStrategy;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyFactory {

    private static final IVersionParserStrategy versionParserStrategy = new DefaultVersionParserStrategy();
    private static final Pattern MAVEN_DEPENDENCY_PATTERN = Pattern.compile("([^:]+):([^:]+):(\\S+[^$]*)$");
    private static final Pattern MAVEN_DEPENDENCY_PATTERN_V2 = Pattern.compile("([^:]+):([^:]+) -> (.+)$");
    private static final Pattern PROJECT_DEPENDENCY_PATTERN = Pattern.compile("project '([^']+)'");

    public static Dependency createDependency(final String dependencyData) {
        Objects.requireNonNull(dependencyData);
        Matcher projectMatcher = PROJECT_DEPENDENCY_PATTERN.matcher(dependencyData);
        if (projectMatcher.find()) {
            return new ProjectDependency(projectMatcher.group(1));
        }
        Optional<ExternalMavenDependency> externalMavenDependency = getExternalMavenDependency(dependencyData, MAVEN_DEPENDENCY_PATTERN, MAVEN_DEPENDENCY_PATTERN_V2);
        if(externalMavenDependency.isPresent()) {
            return externalMavenDependency.get();
        }
        throw new IllegalArgumentException("Unrecognized dependency format: " + dependencyData);
    }

    private static Optional<ExternalMavenDependency> getExternalMavenDependency(String dependencyData, Pattern... mavenPatterns) {
        return Arrays.stream(mavenPatterns)
            .map(mavenPattern -> mavenPattern.matcher(dependencyData))
            .filter(Matcher::find)
            .map(mavenDependencyMatcher -> {
                final String groupId = mavenDependencyMatcher.group(1).trim();
                final String artifactId = mavenDependencyMatcher.group(2).trim();
                final String version = versionParserStrategy.parseVersion(mavenDependencyMatcher.group(3).trim());
                return new ExternalMavenDependency(groupId, artifactId, version);
            }).findFirst();
    }

}