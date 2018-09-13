package com.weissdennis.tsuds.configuration;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "ts3api")
public class Ts3PropertiesConfig {

    private String queryName;
    private String loginMessage;
    private String serverIp;
    private String serverQueryAdminName;
    private String serverQueryAdminPassword;
    private Integer serverId;
    private String afkChannels;
    private Integer locationInfoUpdateInterval;

    public Ts3PropertiesConfig() {
        System.out.println("Test");
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerQueryAdminName() {
        return serverQueryAdminName;
    }

    public void setServerQueryAdminName(String serverQueryAdminName) {
        this.serverQueryAdminName = serverQueryAdminName;
    }

    public String getServerQueryAdminPassword() {
        return serverQueryAdminPassword;
    }

    public void setServerQueryAdminPassword(String serverQueryAdminPassword) {
        this.serverQueryAdminPassword = serverQueryAdminPassword;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getAfkChannels() {
        return afkChannels;
    }

    public void setAfkChannels(String afkChannels) {
        this.afkChannels = afkChannels;
    }

    public List<Long> getParsedAfkChannels() {
        return Arrays.asList(afkChannels.split(".*,.*"))
                .parallelStream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setAfkChannelsFromList(List<Long> afkChannels) {
        this.afkChannels = afkChannels
                .parallelStream()
                .reduce("", (String accStr, Long channel) -> accStr.concat(String.valueOf(channel)), (a, b) -> a + b);


    }

    public Integer getLocationInfoUpdateInterval() {
        return locationInfoUpdateInterval;
    }

    public void setLocationInfoUpdateInterval(Integer locationInfoUpdateInterval) {
        this.locationInfoUpdateInterval = locationInfoUpdateInterval;
    }
}
