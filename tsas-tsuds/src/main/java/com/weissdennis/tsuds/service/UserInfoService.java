package com.weissdennis.tsuds.service;


import com.github.theholywaffle.teamspeak3.TS3Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoService {

    private final TS3Api ts3Api;

    @Autowired
    public UserInfoService(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    @PostConstruct
    public void retrieveUserInfo() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new TS3UserRetrievalTask(ts3Api), 30, 300, TimeUnit.SECONDS);
    }
}
