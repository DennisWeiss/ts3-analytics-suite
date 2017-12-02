package com.weissdennis.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    @Test
    void distanceTo1() {
        Location location1 = new Location(0, 40);
        Location location2 = new Location(10, 60);
        assertEquals(2331.215622, location1.distanceTo(location2), 1);
    }

    @Test
    void distanceTo2() {
        Location location1 = new Location(0, 0);
        Location location2 = new Location(10, 10);
        assertEquals(1567.78977, location1.distanceTo(location2), 1);
    }

    @Test
    void distanceTo3() {
        Location location1 = new Location(0, -20);
        Location location2 = new Location(-20, 20);
        assertEquals(4970.573552, location1.distanceTo(location2), 1);
    }
}