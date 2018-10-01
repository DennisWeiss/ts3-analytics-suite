package com.weissdennis.tsas.tsuds.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;

@Embeddable
public class TS3UserInChannelIdentity implements Serializable {

    @Column(nullable = false)
    private String uniqueId;

    @Column(nullable = false)
    private Instant dateTime;

    public TS3UserInChannelIdentity() {
    }

    public TS3UserInChannelIdentity(String uniqueId, Instant dateTime) {
        this.uniqueId = uniqueId;
        this.dateTime = dateTime;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }
}
