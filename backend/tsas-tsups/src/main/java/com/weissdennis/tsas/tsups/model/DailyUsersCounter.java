package com.weissdennis.tsas.tsups.model;

public class DailyUsersCounter {
    private int totalUsers = 0;
    private long minutes = 0;

    public void addUsers(long users) {
        totalUsers += users;
        minutes++;
    }

    public float getAverageUsersPerMinute() {
        return (float) totalUsers / minutes;
    }

}
