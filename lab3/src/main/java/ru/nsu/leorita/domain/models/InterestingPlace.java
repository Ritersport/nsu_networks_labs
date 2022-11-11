package ru.nsu.leorita.domain.models;

public class InterestingPlace {
    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        if (description != null) {
            return this.name + ": " + description;
        } else return name + ": no description";
    }
}
