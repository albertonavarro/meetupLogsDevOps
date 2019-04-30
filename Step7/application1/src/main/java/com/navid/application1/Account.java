package com.navid.application1;

public class Account {
    private final String name;
    private final int id;

    public Account(String name, int id) {
        this.name = name;

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
