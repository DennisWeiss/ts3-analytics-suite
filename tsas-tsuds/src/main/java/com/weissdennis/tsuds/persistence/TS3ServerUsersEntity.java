package com.weissdennis.tsuds.persistence;

import com.weissdennis.tsuds.model.TS3ServerUsers;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class TS3ServerUsersEntity implements TS3ServerUsers {

    @Id
    private Instant dateTime;

    private Long users;

    public TS3ServerUsersEntity() {
    }

    public TS3ServerUsersEntity(Instant dateTime, Long users) {
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
