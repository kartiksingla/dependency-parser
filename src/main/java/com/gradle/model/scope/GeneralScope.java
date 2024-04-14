package com.gradle.model.scope;

public enum GeneralScope {

    COMPILE("compile"),
    RUNTIME("runtime");

    private final String name;

    GeneralScope(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
