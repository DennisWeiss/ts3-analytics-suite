package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.weissdennis.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsuds.persistence.TS3User;
import com.weissdennis.tsuds.persistence.TS3UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TS3UserRetrievalTask implements Runnable {

    private static Map<String, Date> userUnqiueIdToLastUpdated = new HashMap<>();

    private final TS3Api ts3Api;
    private TS3UserRepository ts3UserRepository;
    private Ts3PropertiesConfig ts3PropertiesConfig;

    public TS3UserRetrievalTask(TS3Api ts3Api, TS3UserRepository ts3UserRepository, Ts3PropertiesConfig ts3PropertiesConfig) {
        this.ts3Api = ts3Api;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
    }

    private TS3User mapDatabaseClientToUser(DatabaseClient databaseClient) {
        TS3User ts3User = new TS3User();
        ts3User.setUniqueId(databaseClient.getUniqueIdentifier());
        ts3User.setClientId(databaseClient.getDatabaseId());
        ts3User.setNickName(databaseClient.getNickname());
        ts3User.setIp(databaseClient.getLastIp());
        return ts3User.withExtendedUserInfo();
    }

    private boolean getDatabaseClientPredicate(DatabaseClient client) {
        return !userUnqiueIdToLastUpdated.containsKey(client.getUniqueIdentifier()) ||
                new Date().getTime() - userUnqiueIdToLastUpdated.get(client.getUniqueIdentifier()).getTime() <
                        TimeUnit.MILLISECONDS.convert(ts3PropertiesConfig.getLocationInfoUpdateInterval(), TimeUnit.DAYS);
    }

    @Override
    public void run() {
        List<TS3User> ts3Users = ts3Api.getDatabaseClients()
                .parallelStream()
                .filter(this::getDatabaseClientPredicate)
                .map(this::mapDatabaseClientToUser)
                .collect(Collectors.toList());

        ts3UserRepository.saveAll(ts3Users);

    }


}
