package com.weissdennis.tsas.tsuds.service;


import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.common.ts3users.TS3User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class UserInfoService implements InitializingBean {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final KafkaTemplate<String, TS3User> ts3UserKafkaTemplate;

    @Autowired
    public UserInfoService(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                           KafkaTemplate<String, TS3User> ts3UserKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserKafkaTemplate = ts3UserKafkaTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        retrieveUserInfo();
    }

    private void retrieveUserInfo() {


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserRetrievalTask(ts3Api, ts3PropertiesConfig, ts3UserKafkaTemplate),
                30, 300, TimeUnit.SECONDS);
    }
}
