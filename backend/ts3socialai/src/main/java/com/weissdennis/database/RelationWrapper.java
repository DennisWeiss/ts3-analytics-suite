package com.weissdennis.database;


public class RelationWrapper {
    private String user;
    private String otherUser;
    private double geoRelation;
    private double channelRelation;
    private double ipRelation;
    private double totalRelation;

    public RelationWrapper(String user, String otherUser, double geoRelation, double channelRelation, double ipRelation, double totalRelation) {
        this.user = user;
        this.otherUser = otherUser;
        this.geoRelation = geoRelation;
        this.channelRelation = channelRelation;
        this.ipRelation = ipRelation;
        this.totalRelation = totalRelation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public double getGeoRelation() {
        return geoRelation;
    }

    public void setGeoRelation(double geoRelation) {
        this.geoRelation = geoRelation;
    }

    public double getChannelRelation() {
        return channelRelation;
    }

    public void setChannelRelation(double channelRelation) {
        this.channelRelation = channelRelation;
    }

    public double getIpRelation() {
        return ipRelation;
    }

    public void setIpRelation(double ipRelation) {
        this.ipRelation = ipRelation;
    }

    public double getTotalRelation() {
        return totalRelation;
    }

    public void setTotalRelation(double totalRelation) {
        this.totalRelation = totalRelation;
    }
}
