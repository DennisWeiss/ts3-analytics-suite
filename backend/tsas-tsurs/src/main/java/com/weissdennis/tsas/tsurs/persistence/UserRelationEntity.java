package com.weissdennis.tsas.tsurs.persistence;

import com.weissdennis.tsas.common.ts3users.IpRelation;
import com.weissdennis.tsas.common.ts3users.UserRelation;

import javax.persistence.EmbeddedId;

public class UserRelationEntity implements UserRelation {

    @EmbeddedId
    private UserRelationIdentity userRelationIdentity;

    private double geoRelation;

    private double channelRelation;

    private IpRelation ipRelation;

    @Override
    public String getClient1() {
        return userRelationIdentity.getClient1();
    }

    public void setClient1(String client) {
        this.userRelationIdentity.setClient1(client);
    }

    @Override
    public String getClient2() {
        return userRelationIdentity.getClient2();
    }

    public void setClient2(String client) {
        this.userRelationIdentity.setClient2(client);
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
        return channelRelation + 0.1 * geoRelation + ipRelation.getValue();
    }
}
