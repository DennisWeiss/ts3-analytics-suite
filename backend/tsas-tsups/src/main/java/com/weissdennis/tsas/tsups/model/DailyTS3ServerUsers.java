package com.weissdennis.tsas.tsups.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weissdennis.tsas.tsups.serializer.DailyTS3ServerUsersSerializer;

import java.util.Date;

@JsonSerialize(using = DailyTS3ServerUsersSerializer.class)
public class DailyTS3ServerUsers {

    private Date date;
    private long users;

    public DailyTS3ServerUsers(Date date, long users) {
        this.date = date;
        this.users = users;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }
}
