package com.weissdennis.tsas.common.ts3users;

import com.fasterxml.jackson.annotation.JsonValue;
import com.weissdennis.tsas.common.Ipv4Address;

public enum IpRelation {

    NOT_MATCH(0),
    FIRST_THREE_MATCH(0.1),
    MATCH(0.5);

    private double value;

    IpRelation(double value) {
        this.value = value;
    }

    @JsonValue
    public double getValue() {
        return value;
    }

    public static IpRelation getRelation(String a, String b) {
        int[] ipA = Ipv4Address.getIpParts(a);
        int[] ipB = Ipv4Address.getIpParts(b);

        if (ipA.length != 4 || ipB.length != 4) {
            throw new RuntimeException("Malformed IP String");
        }

        for (int i = 0; i < 3; i++) {
            if (ipA[i] != ipB[i]) {
                return NOT_MATCH;
            }
        }

        if (ipA[3] != ipB[3]) {
            return FIRST_THREE_MATCH;
        }

        return MATCH;
    }
}
