package com.weissdennis.tsas.common.ts3users;

public class UserRelationImpl implements UserRelation {

    private String client1;
    private String client2;
    private double geoRelation;
    private double channelRelation;
    private IpRelation ipRelation;
    private double totalRelation;

    @Override
    public String getClient1() {
        return client1;
    }

    public void setClient1(String client1) {
        this.client1 = client1;
    }

    @Override
    public String getClient2() {
        return client2;
    }

    public void setClient2(String client2) {
        this.client2 = client2;
    }

    @Override
    public double getGeoRelation() {
        return geoRelation;
    }

    public void setGeoRelation(double geoRelation) {
        this.geoRelation = geoRelation;
    }

    @Override
    public double getChannelRelation() {
        return channelRelation;
    }

    public void setChannelRelation(double channelRelation) {
        this.channelRelation = channelRelation;
    }

    @Override
    public IpRelation getIpRelation() {
        return ipRelation;
    }

    public void setIpRelation(IpRelation ipRelation) {
        this.ipRelation = ipRelation;
    }

    @Override
    public double getTotalRelation() {
        return totalRelation;
    }

    public void setTotalRelation(double totalRelation) {
        this.totalRelation = totalRelation;
    }
}
