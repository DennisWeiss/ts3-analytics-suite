package com.weissdennis.tsas.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ipv4AddressTest {

    @Test
    void fromString1() {
        String ipString = "192.168.2.103";

        int ipAsInt = Ipv4Address.fromString(ipString);

        assertEquals(-1062731161, ipAsInt);
    }

    @Test
    void fromString2() {
        String ipString = "255.168.2.103";

        int ipAsInt = Ipv4Address.fromString(ipString);

        assertEquals(-5766553, ipAsInt);
    }

    @Test
    void toString1() {
        int ipAsInt = -1062731161;

        String ipString = Ipv4Address.toString(ipAsInt);

        assertEquals("192.168.2.103", ipString);
    }

    @Test
    void toString2() {
        int ipAsInt = -5766553;

        String ipString = Ipv4Address.toString(ipAsInt);

        assertEquals("255.168.2.103", ipString);
    }
}