package com.gradle.parser.version.strategy;

import java.util.Objects;

public class DefaultVersionParserStrategy implements IVersionParserStrategy {

    @Override
    public String parseVersion(String rawVersion) {
        Objects.requireNonNull(rawVersion);
        if (rawVersion.contains("->")) {
            return rawVersion.split("->")[1].trim();
        }
        return rawVersion.trim();
    }
}
