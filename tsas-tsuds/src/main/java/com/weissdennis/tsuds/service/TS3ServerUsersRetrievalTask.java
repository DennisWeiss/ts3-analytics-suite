package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsuds.model.TS3ServerUsers;
import com.weissdennis.tsuds.model.TS3ServerUsersImpl;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;

public class TS3ServerUsersRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final KafkaTemplate<String, TS3ServerUsers> ts3ServerUsersKafkaTemplate;

    public TS3ServerUsersRetrievalTask(TS3Api ts3Api, KafkaTemplate<String, TS3ServerUsers> ts3ServerUsersKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3ServerUsersKafkaTemplate = ts3ServerUsersKafkaTemplate;
    }

    @Override
    public void run() {
        long users = ts3Api.getClients()
                .stream()
                .filter(TS3ServerUsersRetrievalTask::isValidClient)
                .count();

        System.out.println("ts3_server_users " + new TS3ServerUsersImpl(Instant.now(), users));
        ts3ServerUsersKafkaTemplate.send("ts3_server_users", new TS3ServerUsersImpl(Instant.now(), users));
    }

    private static boolean isValidClient(Client client) {
        return !client.isServerQueryClient();
    }
}
