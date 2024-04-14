package com.gradle.parser.strategy;

import com.gradle.model.Dependency;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class DependencyTreeParsingStrategy implements IParsingStrategy {
    @Override
    public Map<String, Set<Dependency>> parse(final String fileContent) {
        Objects.requireNonNull(fileContent);
        return parse(List.of(fileContent.split(System.lineSeparator())));
    }

    @Override
    public Map<String, Set<Dependency>> parse(final List<String> fileContent) {
        
        return null;
    }
}
