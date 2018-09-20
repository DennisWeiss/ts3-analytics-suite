package com.weissdennis.tsas.tsuds.configuration;


import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Ts3ApiConfig {

    private final Ts3PropertiesConfig ts3PropertiesConfig;

    private TS3Api ts3Api;

    @Autowired
    public Ts3ApiConfig(Ts3PropertiesConfig ts3PropertiesConfig) {
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(ts3PropertiesConfig.getServerIp());
        TS3Query ts3Query = new TS3Query(ts3Config);
        ts3Query.connect();
        ts3Api = ts3Query.getApi();
        ts3Api.login(ts3PropertiesConfig.getServerQueryAdminName(), ts3PropertiesConfig.getServerQueryAdminPassword());
        ts3Api.selectVirtualServerById(ts3PropertiesConfig.getServerId());
        ts3Api.setNickname(ts3PropertiesConfig.getQueryName());
    }

    @Bean
    public TS3Api getTs3Api() {
        return ts3Api;
    }
}
