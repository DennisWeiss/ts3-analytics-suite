package com.weissdennis.tsas.tsuds.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TS3UserPair implements Serializable {

    @Column(nullable=false)
    private String user1;

    @Column(nullable=false)
    private String user2;

    public TS3UserPair() {
    }

    public TS3UserPair(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
