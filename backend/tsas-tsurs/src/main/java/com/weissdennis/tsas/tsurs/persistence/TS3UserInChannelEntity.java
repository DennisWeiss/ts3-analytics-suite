package com.weissdennis.tsas.tsurs.persistence;

import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "user_in_channel")
public class TS3UserInChannelEntity implements TS3UserInChannel {

    @EmbeddedId
    private TS3UserInChannelIdentity ts3UserInChannelIdentity;

    private Integer channelId;

    private Integer interval;

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

    @Override
    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
