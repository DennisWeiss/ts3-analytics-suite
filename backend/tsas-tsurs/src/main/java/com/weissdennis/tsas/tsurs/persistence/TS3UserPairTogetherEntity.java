package com.weissdennis.tsas.tsurs.persistence;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ts3_user_pair_together")
public class TS3UserPairTogetherEntity {

    @EmbeddedId
    private TS3UserPair userPair;

    private double timeTogether;

    public TS3UserPair getUserPair() {
        return userPair;
    }

    public void setUserPair(TS3UserPair userPair) {
        this.userPair = userPair;
    }

    public double getTimeTogether() {
        return timeTogether;
    }

    public void setTimeTogether(double channelRelation) {
        this.timeTogether = channelRelation;
    }

    public void addTime(int units) {
        this.timeTogether += units;
    }
}
