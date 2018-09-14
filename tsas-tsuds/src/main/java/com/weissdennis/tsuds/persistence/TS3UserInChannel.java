package com.weissdennis.tsuds.persistence;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class TS3UserInChannel {

    @EmbeddedId
    private TS3UserInChannelIdentity TS3UserInChannelIdentity;

    private Integer channelId;


    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public TS3UserInChannelIdentity getTS3UserInChannelIdentity() {
        return TS3UserInChannelIdentity;
    }

    public void setTS3UserInChannelIdentity(TS3UserInChannelIdentity TS3UserInChannelIdentity) {
        this.TS3UserInChannelIdentity = TS3UserInChannelIdentity;
    }

}
