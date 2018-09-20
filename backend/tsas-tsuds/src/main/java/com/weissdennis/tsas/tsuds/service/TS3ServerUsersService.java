package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TS3ServerUsersService implements InitializingBean {

    private final TS3Api ts3Api;
    private final KafkaTemplate<String, TS3ServerUsers> ts3ServerUsersKafkaTemplate;

    @Autowired
    public TS3ServerUsersService(TS3Api ts3Api, KafkaTemplate<String, TS3ServerUsers> ts3ServerUsersKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3ServerUsersKafkaTemplate = ts3ServerUsersKafkaTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        getUserCount();
    }

    private void getUserCount() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3ServerUsersRetrievalTask(ts3Api, ts3ServerUsersKafkaTemplate), 1, 60, TimeUnit.SECONDS);
    }
}
