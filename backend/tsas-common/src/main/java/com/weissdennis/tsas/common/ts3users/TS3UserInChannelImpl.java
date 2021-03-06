package com.weissdennis.tsas.common.ts3users;

import java.time.Instant;

public class TS3UserInChannelImpl implements TS3UserInChannel {

    private String uniqueId;
    private Instant dateTime;
    private Integer channelId;
    private Integer dataInterval;

    public TS3UserInChannelImpl() {
    }

    public TS3UserInChannelImpl(String uniqueId, Instant dateTime, Integer channelId, Integer dataInterval) {
        this.uniqueId = uniqueId;
        this.dateTime = dateTime;
        this.channelId = channelId;
        this.dataInterval = dataInterval;
    }

    @Override
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Integer getDataInterval() {
        return dataInterval;
    }

    public void setDataInterval(Integer dataInterval) {
        this.dataInterval = dataInterval;
    }
}
