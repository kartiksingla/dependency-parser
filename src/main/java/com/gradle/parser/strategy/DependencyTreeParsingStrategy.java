package com.gradle.parser.strategy;

import com.gradle.common.model.Dependency;
import com.gradle.dependency.tree.model.DependencyTreeScope;
import com.gradle.dependency.tree.parser.DependencyIterator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class DependencyTreeParsingStrategy implements IParsingStrategy {

    @Override
    public Map<String, Set<Dependency>> parse(final String fileContent) {
        Objects.requireNonNull(fileContent);
        return parse(List.of(fileContent.split(System.lineSeparator())));
    }

    @Override
    public Map<String, Set<Dependency>> parse(final List<String> fileContent) {
        Map<DependencyTreeScope, Set<Dependency>> dependenciesByScope = Stream.of(DependencyTreeScope.values())
            .collect(Collectors.toMap(
                Function.identity(),
                scope -> this.getDependencies(scope.getName(), fileContent)
            ));

        return eliminateDuplicates(dependenciesByScope);
    }

    private Map<String, Set<Dependency>> eliminateDuplicates(Map<DependencyTreeScope, Set<Dependency>> dependenciesByScope) {
        return Arrays.stream(DependencyTreeScope.values())
            .filter(dependenciesByScope::containsKey) // if dependencyTree had that scope present
            .collect(
                Collectors.toMap(
                    DependencyTreeScope::name,
                    scope -> {
                        Set<Dependency> dependencies = dependenciesByScope.get(scope);
                        dependencies.removeAll(scope.getInheritedScopeSet().stream()
                            .filter(dependenciesByScope::containsKey)
                            .flatMap(pScope -> dependenciesByScope.get(pScope).stream())
                            .collect(Collectors.toSet()));
                        return dependencies;
                    }
                )
            );
    }

    /**
     * Finds and extracts dependency information for a specified configuration.
     *
     * @param configurationName The name of the configuration to find (e.g., "compileClasspath").
     * @param fileContent       The list of lines from the Gradle dependencies output.
     * @return A {@link Set} of {@link Dependency} containing the dependencies under the specified configuration.
     */
    private Set<Dependency> getDependencies(String configurationName, List<String> fileContent) {
        List<String> rawDependenciesStrings = findConfigurationSpecificDependencies(configurationName, fileContent);
        DependencyIterator dependencyIterator = new DependencyIterator(rawDependenciesStrings);
        Set<Dependency> dependencies = new TreeSet<>();
        dependencyIterator.forEachRemaining(dependencies::add);

        return dependencies;
    }

    private List<String> findConfigurationSpecificDependencies(String configurationName,
                                                               List<String> fileContent) {
        int startIndex = IntStream.range(0, fileContent.size() - 1)
            .filter(idx -> fileContent.get(idx).trim().startsWith(configurationName))
            .findFirst().orElse(-1);
        if (startIndex == -1) {
            return List.of();
        }
        int endIndex = IntStream.range(startIndex + 1, fileContent.size())
            .filter(idx -> fileContent.get(idx).trim().isEmpty())
            .findFirst()
            .orElse(fileContent.size());

        return fileContent.subList(startIndex + 1, endIndex);
    }
}
