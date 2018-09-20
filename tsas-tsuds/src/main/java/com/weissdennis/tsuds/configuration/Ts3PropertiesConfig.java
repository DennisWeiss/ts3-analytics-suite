package com.weissdennis.tsuds.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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

    public List<Integer> getParsedAfkChannels() {
        return Arrays.stream(afkChannels.split(".*,.*"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void setAfkChannelsFromList(List<Integer> afkChannels) {
        this.afkChannels = afkChannels
                .stream()
                .reduce("", (String accStr, Integer channel) -> accStr.concat(String.valueOf(channel)), (a, b) -> a + b);


    }

    public Integer getLocationInfoUpdateInterval() {
        return locationInfoUpdateInterval;
    }

    public void setLocationInfoUpdateInterval(Integer locationInfoUpdateInterval) {
        this.locationInfoUpdateInterval = locationInfoUpdateInterval;
    }
}
