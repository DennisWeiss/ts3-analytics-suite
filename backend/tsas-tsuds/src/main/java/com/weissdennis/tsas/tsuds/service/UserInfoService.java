package com.weissdennis.tsas.tsuds.service;


import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.tsuds.persistence.TS3UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class UserInfoService implements InitializingBean {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserRepository ts3UserRepository;

    @Autowired
    public UserInfoService(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                           TS3UserRepository ts3UserRepository) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserRepository = ts3UserRepository;
    }

    @Override
    public void afterPropertiesSet() {
        retrieveUserInfo();
    }

    private void retrieveUserInfo() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserRetrievalTask(ts3Api, ts3PropertiesConfig, ts3UserRepository),
                15, 300, TimeUnit.SECONDS);
    }
}
