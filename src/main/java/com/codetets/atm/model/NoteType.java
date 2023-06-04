package com.codetets.atm.model;

public enum NoteType {
    TWENTY(20),
    FIFTY(50);

    private final double value;

    NoteType(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
