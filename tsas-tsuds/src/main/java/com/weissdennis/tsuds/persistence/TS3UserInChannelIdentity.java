package com.weissdennis.tsuds.persistence;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TS3UserInChannelIdentity implements Serializable {

    private String uniqueId;
    private Integer channelId;

    public TS3UserInChannelIdentity() {
    }

    public TS3UserInChannelIdentity(String uniqueId, Integer channelId) {
        this.uniqueId = uniqueId;
        this.channelId = channelId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}
