package com.weissdennis.tsuds.persistence;

public interface TS3User {

    Integer getClientId();

    String getUniqueId();

    String getNickName();

    String getIp();

    String getHostName();

    String getCity();

    String getRegion();

    String getCountry();

    Double getLongitude();

    Double getLatitude();

    String getPostalCode();

    String getOrg();
}
