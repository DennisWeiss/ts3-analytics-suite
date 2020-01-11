package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.tsuds.persistence.TS3UserInChannelRepository;
import com.weissdennis.tsas.tsuds.persistence.TS3UserPairTogetherRepository;
import com.weissdennis.tsas.tsuds.persistence.TS3UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class ServerQueryService implements InitializingBean {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final TS3UserRepository ts3UserRepository;
    private final TS3UserPairTogetherRepository ts3UserPairTogetherRepository;

    @Autowired
    public ServerQueryService(Ts3PropertiesConfig ts3PropertiesConfig, TS3Api ts3Api,
                              TS3UserInChannelRepository ts3UserInChannelRepository,
                              TS3UserRepository ts3UserRepository,
                              TS3UserPairTogetherRepository ts3UserPairTogetherRepository) {
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3Api = ts3Api;
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3UserPairTogetherRepository = ts3UserPairTogetherRepository;
    }

    @Override
    public void afterPropertiesSet() {
        login();
    }

    private void login() {
        String loginMessage = ts3PropertiesConfig.getLoginMessage();
        if (loginMessage != null && loginMessage.length() > 0) {
            ts3Api.sendChannelMessage(loginMessage);
        }

        retrieveUserData();
    }

    private void retrieveUserData() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserInChannelRetrievalTask(ts3Api, ts3PropertiesConfig,
                        ts3UserInChannelRepository, ts3UserRepository, ts3UserPairTogetherRepository),
                1, ts3PropertiesConfig.getUserInChannelInterval(), TimeUnit.SECONDS);
    }


}
