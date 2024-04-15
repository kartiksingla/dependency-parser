package com.gradle.dependency.tree.parser;

import com.gradle.common.model.Dependency;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class DependencyIterator implements Iterator<Dependency> {

    private final List<String> lines;

    private int currentPosition = 0;
    private final Predicate<DependencyIterator> hasNextPredicate = DependencyIterator::hasNext;
    
    public DependencyIterator(final List<String> lines) {
        this.lines = lines;
    }

    @Override
    public boolean hasNext() {
        while (currentPosition < lines.size()) {
            String line = lines.get(currentPosition);
            if (line.startsWith("+") || line.startsWith("\\")) {
                return true;
            }
            currentPosition++;
        }
        return false;
    }

    @Override
    public Dependency next() {
        if (Predicate.not(hasNextPredicate).test(this)) {
            throw new NoSuchElementException();
        }
        String line = lines.get(currentPosition++);
        return DependencyFactory.createDependency(
            line.substring(line.indexOf("---") + 3).trim()
        );
    }
}
