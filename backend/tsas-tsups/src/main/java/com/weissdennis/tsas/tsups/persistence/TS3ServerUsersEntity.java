package com.weissdennis.tsas.tsups.persistence;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "server_users")
public class TS3ServerUsersEntity implements TS3ServerUsers {

    @Id
    private Instant dateTime;

    @Column(nullable = false)
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
