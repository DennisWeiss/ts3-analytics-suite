package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Ban;
import com.github.theholywaffle.teamspeak3.api.wrapper.DatabaseClient;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.TS3UserImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TS3UserRetrievalTask implements Runnable {

    private static Map<String, Date> userUniqueIdToLastUpdated = new HashMap<>();

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final KafkaTemplate<String, TS3User> ts3UserKafkaTemplate;

    public TS3UserRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                KafkaTemplate<String, TS3User> ts3UserKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserKafkaTemplate = ts3UserKafkaTemplate;
    }

    private TS3User mapDatabaseClientToUser(DatabaseClient databaseClient, List<Ban> bans) {
        TS3UserImpl ts3User = new TS3UserImpl();
        ts3User.setUniqueId(databaseClient.getUniqueIdentifier());
        ts3User.setClientId(databaseClient.getDatabaseId());
        ts3User.setNickName(databaseClient.getNickname());
        ts3User.setIp(databaseClient.getLastIp());
        ts3User.setBanned(bans.stream().anyMatch(ban -> ban.getBannedUId().equals(databaseClient.getUniqueIdentifier())));
        return ts3User.withExtendedUserInfo();
    }

    private boolean getDatabaseClientPredicate(DatabaseClient client) {
        return !userUniqueIdToLastUpdated.containsKey(client.getUniqueIdentifier()) ||
                new Date().getTime() - userUniqueIdToLastUpdated.get(client.getUniqueIdentifier()).getTime() <
                        TimeUnit.MILLISECONDS.convert(ts3PropertiesConfig.getLocationInfoUpdateInterval(), TimeUnit.DAYS);
    }

    @Override
    public void run() {
        List<Ban> bans = ts3Api.getBans();
        ts3Api.getDatabaseClients()
                .parallelStream()
                .filter(this::getDatabaseClientPredicate)
                .map(databaseClient -> mapDatabaseClientToUser(databaseClient, bans))
                .forEach(user -> {
                    ts3UserKafkaTemplate.send("ts3_user", user);
                    userUniqueIdToLastUpdated.put(user.getUniqueId(), new Date());
                });
    }


}
