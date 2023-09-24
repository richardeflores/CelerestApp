package com.google.code.microlog4android.repository;

public abstract class AbstractRepositoryNode {
    protected String name;

    protected AbstractRepositoryNode() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
