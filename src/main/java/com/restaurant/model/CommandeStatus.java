package com.restaurant.model;

public enum CommandeStatus {
    EN_ATTENTE("En Attente"),
    EN_COURS("En Cours"),
    DONE("Done"),
    LIVRE("Livré");

    private final String label;

    CommandeStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
