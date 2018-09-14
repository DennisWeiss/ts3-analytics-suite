package com.weissdennis.tsuds.service;


import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsuds.persistence.TS3UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoService implements InitializingBean {

    private final TS3Api ts3Api;
    private final TS3UserRepository ts3UserRepository;
    private final Ts3PropertiesConfig ts3PropertiesConfig;

    @Autowired
    public UserInfoService(TS3Api ts3Api, TS3UserRepository ts3UserRepository, Ts3PropertiesConfig ts3PropertiesConfig) {
        this.ts3Api = ts3Api;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
    }

    @Override
    public void afterPropertiesSet() {
        retrieveUserInfo();
    }

    private void retrieveUserInfo() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserRetrievalTask(ts3Api, ts3UserRepository, ts3PropertiesConfig), 30, 300, TimeUnit.SECONDS);
    }
}
