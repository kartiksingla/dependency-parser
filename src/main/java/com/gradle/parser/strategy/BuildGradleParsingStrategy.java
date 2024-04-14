package com.gradle.parser.strategy;

import com.gradle.model.Dependency;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class BuildGradleParsingStrategy implements IParsingStrategy {
    @Override
    public Map<String, Set<Dependency>> parse(String fileContent) {
        Objects.requireNonNull(fileContent);
        return parse(List.of(fileContent.split("\n")));
    }

    @Override
    public Map<String, Set<Dependency>> parse(List<String> fileContent) {
        return null;
    }
}
