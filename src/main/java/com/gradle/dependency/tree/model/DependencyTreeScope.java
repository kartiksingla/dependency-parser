package com.gradle.dependency.tree.model;

import com.gradle.common.model.GeneralScope;

import java.util.Set;

public enum DependencyTreeScope {

    COMPILE_CLASSPATH("compileClasspath", GeneralScope.COMPILE, Set.of()),
    TEST_COMPILE_CLASSPATH("testCompileClasspath", GeneralScope.COMPILE, Set.of(COMPILE_CLASSPATH)),

    RUNTIME_ONLY("runtimeOnly", GeneralScope.RUNTIME, Set.of(COMPILE_CLASSPATH)),
    TEST_RUNTIME_ONLY("testRuntimeOnly", GeneralScope.RUNTIME, Set.of(COMPILE_CLASSPATH, TEST_COMPILE_CLASSPATH, RUNTIME_ONLY));

    private final String name;

    private final GeneralScope parent;

    private final Set<DependencyTreeScope> inheritedScopeSet;

    DependencyTreeScope(String name, GeneralScope parent, Set<DependencyTreeScope> inheritedScopeSet) {
        this.name = name;
        this.parent = parent;
        this.inheritedScopeSet = inheritedScopeSet;
    }

    public String getName() {
        return name;
    }

    public GeneralScope getParent() {
        return parent;
    }

    public Set<DependencyTreeScope> getInheritedScopeSet() {
        return inheritedScopeSet;
    }
}
