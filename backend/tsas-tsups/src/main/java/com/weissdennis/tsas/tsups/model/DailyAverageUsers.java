package com.weissdennis.tsas.tsups.model;

public class DailyAverageUsers {

    private int minute;
    private float users;

    public DailyAverageUsers(int minute, float users) {
        this.minute = minute;
        this.users = users;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public float getUsers() {
        return users;
    }

    public void setUsers(float users) {
        this.users = users;
    }
}
