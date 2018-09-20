package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.weissdennis.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsuds.model.TS3User;
import com.weissdennis.tsuds.model.TS3UserImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TS3UserRetrievalTask implements Runnable {

    private static Map<String, Date> userUnqiueIdToLastUpdated = new HashMap<>();

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final KafkaTemplate<String, TS3User> ts3UserKafkaTemplate;

    public TS3UserRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                KafkaTemplate<String, TS3User> ts3UserKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserKafkaTemplate = ts3UserKafkaTemplate;
    }

    private TS3User mapDatabaseClientToUser(DatabaseClient databaseClient) {
        TS3UserImpl ts3User = new TS3UserImpl();
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
        ts3Api.getDatabaseClients()
                .parallelStream()
                .filter(this::getDatabaseClientPredicate)
                .map(this::mapDatabaseClientToUser)
                .forEach(user -> {
                    System.out.println("ts3_user " + user);
                    ts3UserKafkaTemplate.send("ts3_user", user);
                });
    }


}
