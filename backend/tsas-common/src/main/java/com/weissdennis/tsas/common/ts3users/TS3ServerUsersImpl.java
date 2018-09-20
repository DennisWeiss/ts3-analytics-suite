package com.weissdennis.tsas.common.ts3users;

import java.time.Instant;

public class TS3ServerUsersImpl implements TS3ServerUsers {

    private Instant dateTime;
    private Long users;

    public TS3ServerUsersImpl() {
    }

    public TS3ServerUsersImpl(Instant dateTime, Long users) {
        this.dateTime = dateTime;
        this.users = users;
    }

    @Override
    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }
}