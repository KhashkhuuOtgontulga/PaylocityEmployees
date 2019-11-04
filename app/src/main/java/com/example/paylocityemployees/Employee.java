package com.example.paylocityemployees;

import java.io.Serializable;

// holds the data for one employee
public class Employee implements Serializable {
    private String name;
    private boolean spouse;
    private int children;

    public Employee(String name, boolean spouse, int children) {
        this.name = name;
        this.spouse = spouse;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public boolean isSpouse() {
        return spouse;
    }

    public int getChildren() {
        return children;
    }
}
