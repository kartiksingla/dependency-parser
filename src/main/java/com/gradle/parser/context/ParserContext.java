package com.gradle.parser.context;

import com.gradle.common.model.Dependency;
import com.gradle.parser.strategy.IParsingStrategy;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ParserContext {

    private IParsingStrategy parsingStrategy;

    public ParserContext(IParsingStrategy parsingStrategy) {
        this.parsingStrategy = parsingStrategy;
    }

    public void setParsingStrategy(IParsingStrategy parsingStrategy) {
        Objects.requireNonNull(parsingStrategy);
        this.parsingStrategy = parsingStrategy;
    }

    public Map<String, Set<Dependency>> parse(final String content) {
        return parsingStrategy.parse(content);
    }

}
