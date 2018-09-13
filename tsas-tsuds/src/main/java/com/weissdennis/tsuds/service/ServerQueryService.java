package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsuds.configuration.Ts3PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class ServerQueryService {

    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private TS3Api ts3Api;

    @Autowired
    public ServerQueryService(Ts3PropertiesConfig ts3PropertiesConfig, TS3Api ts3Api) {
        System.out.println(ts3PropertiesConfig.getQueryName());
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3Api = ts3Api;
    }

    @PostConstruct
    private void login() {
        String loginMessage = ts3PropertiesConfig.getLoginMessage();
        if (loginMessage != null && loginMessage.length() > 0) {
            ts3Api.sendChannelMessage(loginMessage);
        }

        retrieveUserData();
    }

    private void retrieveUserData() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserInChannelRetrievalTask(ts3Api), 1, 10, TimeUnit.SECONDS);
    }
}
