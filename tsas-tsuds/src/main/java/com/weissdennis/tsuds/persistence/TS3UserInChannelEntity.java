package com.weissdennis.tsuds.persistence;

import com.weissdennis.tsuds.model.TS3UserInChannel;
import com.weissdennis.tsuds.model.TS3UserInChannelIdentity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class TS3UserInChannelEntity implements TS3UserInChannel {

    @EmbeddedId
    private TS3UserInChannelIdentity ts3UserInChannelIdentity;

    private Integer channelId;

    @Override
    public Integer getChannelId() {
        return channelId;
    }

    @Override
    public String getUniqueId() {
        return ts3UserInChannelIdentity.getUniqueId();
    }

    @Override
    public Instant getDateTime() {
        return ts3UserInChannelIdentity.getDateTime();
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public TS3UserInChannelIdentity getTs3UserInChannelIdentity() {
        return ts3UserInChannelIdentity;
    }

    public void setTs3UserInChannelIdentity(TS3UserInChannelIdentity ts3UserInChannelIdentity) {
        this.ts3UserInChannelIdentity = ts3UserInChannelIdentity;
    }

}
