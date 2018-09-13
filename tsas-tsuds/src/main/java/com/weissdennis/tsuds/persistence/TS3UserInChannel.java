package com.weissdennis.tsuds.persistence;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class TS3UserInChannel {

    @EmbeddedId
    private TS3UserInChannelIdentity TS3UserInChannelIdentity;

    private Instant dateTime;

    public TS3UserInChannelIdentity getTS3UserInChannelIdentity() {
        return TS3UserInChannelIdentity;
    }

    public void setTS3UserInChannelIdentity(TS3UserInChannelIdentity TS3UserInChannelIdentity) {
        this.TS3UserInChannelIdentity = TS3UserInChannelIdentity;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }
}
