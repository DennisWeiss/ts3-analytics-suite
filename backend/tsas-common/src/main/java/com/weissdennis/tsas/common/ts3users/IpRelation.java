package com.weissdennis.tsas.common.ts3users;

public enum IpRelation {

    NOT_MATCH(0),
    FIRST_THREE_MATCH(0.1),
    MATCH(0.5);

    private double value;

    IpRelation(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
