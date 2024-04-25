package com.gradle.parser.strategy.version;

import java.util.Objects;
import java.util.regex.Pattern;

public class DefaultVersionParserStrategy implements IVersionParserStrategy {

    private static final Pattern GARBAGE_SUFFIX = Pattern.compile(" \\(\\*\\)| \\(.\\)");
    @Override
    public String parseVersion(String rawVersion) {
        Objects.requireNonNull(rawVersion);
        rawVersion = GARBAGE_SUFFIX.matcher(rawVersion).replaceAll("");
        if (rawVersion.contains("->")) {
            return rawVersion.split("->")[1].trim();
        }
        return rawVersion.trim();
    }
}
