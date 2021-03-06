package com.weissdennis.tsas.common.ts3users;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;

public class TS3UserImpl implements TS3User {

    private String uniqueId;
    private Integer clientId;
    private String nickName;
    private String ip;
    private String hostName;
    private String city;
    private String region;
    private String country;
    private Double longitude;
    private Double latitude;
    private String postalCode;
    private String org;
    private Instant lastOnline;
    private boolean banned = false;

    public TS3UserImpl() {
    }

    private TS3UserImpl withExtendedUserInfo(ClientIpInfo clientIpInfo) {
        TS3UserImpl ts3User = new TS3UserImpl();
        ts3User.setUniqueId(uniqueId);
        ts3User.setClientId(clientId);
        ts3User.setNickName(nickName);
        ts3User.setIp(ip);
        ts3User.setHostName(clientIpInfo.getHostname());
        ts3User.setCity(clientIpInfo.getCity());
        ts3User.setRegion(clientIpInfo.getRegion());
        ts3User.setCountry(clientIpInfo.getCountry());
        ts3User.setLatitude(clientIpInfo.getLatitude());
        ts3User.setLongitude(clientIpInfo.getLongitude());
        ts3User.setPostalCode(clientIpInfo.getPostal());
        ts3User.setOrg(clientIpInfo.getOrg());
        return ts3User;
    }

    public TS3UserImpl withExtendedUserInfo() {
        if (ip != null && !ip.equals("")) {
            try {
                return withExtendedUserInfo(new GsonBuilder().create().fromJson(new BufferedReader(new InputStreamReader(
                                new URL("http://ipinfo.io/" + ip + "/json").openConnection().getInputStream())),
                        ClientIpInfo.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public Instant getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Instant lastOnline) {
        this.lastOnline = lastOnline;
    }

    @Override
    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}

