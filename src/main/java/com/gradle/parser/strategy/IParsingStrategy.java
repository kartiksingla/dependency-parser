package com.gradle.parser.strategy;

import com.gradle.common.model.Dependency;

import java.util.List;
import java.util.Map;
import java.util.Set;

public sealed interface IParsingStrategy permits DependencyTreeParsingStrategy, BuildGradleParsingStrategy {


    Map<String, Set<Dependency>> parse(String fileContent);

    Map<String, Set<Dependency>> parse(List<String> fileContent);
}
