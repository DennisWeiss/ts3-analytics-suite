package com.weissdennis.tsuds.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class TS3ServerUsers {

    @Id
    private Instant dateTime;

    private Long users;

    public TS3ServerUsers() {
    }

    public TS3ServerUsers(Instant dateTime, Long users) {
        this.dateTime = dateTime;
        this.users = users;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }
}
