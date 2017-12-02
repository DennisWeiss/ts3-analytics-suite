package com.weissdennis.ai;

public enum IpMatch {
    NOT_MATCH(0), FIRST_THREE_MATCH(0.1), MATCH(0.5);

    private double value;

    IpMatch(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
