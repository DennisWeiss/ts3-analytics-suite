package com.weissdennis.tsuds.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
