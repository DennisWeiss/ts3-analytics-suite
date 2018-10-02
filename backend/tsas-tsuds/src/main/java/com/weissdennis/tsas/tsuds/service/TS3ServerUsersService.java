package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.weissdennis.tsas.tsuds.persistence.TS3ServerUsersRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TS3ServerUsersService implements InitializingBean {

    private final TS3Api ts3Api;
    private final TS3ServerUsersRepository ts3ServerUsersRepository;

    @Autowired
    public TS3ServerUsersService(TS3Api ts3Api, TS3ServerUsersRepository ts3ServerUsersRepository) {
        this.ts3Api = ts3Api;
        this.ts3ServerUsersRepository = ts3ServerUsersRepository;
    }

    @Override
    public void afterPropertiesSet() {
        getUserCount();
    }

    private void getUserCount() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3ServerUsersRetrievalTask(ts3Api, ts3ServerUsersRepository), 1, 60, TimeUnit.SECONDS);
    }
}
