package com.weissdennis.tsas.tsurs.persistence;

import com.weissdennis.tsas.common.ts3users.IpRelation;
import com.weissdennis.tsas.common.ts3users.UserRelation;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class UserRelationEntity implements UserRelation {

    @EmbeddedId
    private UserRelationIdentity userRelationIdentity;

    private double geoRelation;

    private double channelRelation;

    private IpRelation ipRelation;

    public UserRelationEntity() {
    }

    public UserRelationEntity(UserRelationIdentity userRelationIdentity, double geoRelation, double channelRelation,
                              IpRelation ipRelation) {
        this.userRelationIdentity = userRelationIdentity;
        this.geoRelation = geoRelation;
        this.channelRelation = channelRelation;
        this.ipRelation = ipRelation;
    }

    @Override
    public String getClient1() {
        return userRelationIdentity.getClient1();
    }

    @Override
    public String getClient2() {
        return userRelationIdentity.getClient2();
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
