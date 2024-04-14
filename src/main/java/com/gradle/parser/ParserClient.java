package com.gradle.parser;

import com.gradle.parser.context.ParserContext;
import com.gradle.parser.strategy.DependencyTreeParsingStrategy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserClient {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ParserContext context = new ParserContext(new DependencyTreeParsingStrategy());

        String dependencyTreeContent = Files.readString(
            Paths.get(Thread.currentThread().getContextClassLoader().getResource("dependency-tree.txt").toURI())
        );

        context.parse(dependencyTreeContent);
    }
}
