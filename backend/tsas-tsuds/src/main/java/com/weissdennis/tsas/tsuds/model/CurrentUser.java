package com.weissdennis.tsas.tsuds.model;

public class CurrentUser {
    private String id;
    private int channel;

    public CurrentUser(String id, int channel) {
        this.id = id;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
