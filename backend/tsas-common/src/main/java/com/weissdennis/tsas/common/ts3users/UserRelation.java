package com.weissdennis.tsas.common.ts3users;

public interface UserRelation {

    String getClient1();

    String getClient2();

    double getGeoRelation();

    double getChannelRelation();

    IpRelation getIpRelation();

    double getTotalRelation();
}
