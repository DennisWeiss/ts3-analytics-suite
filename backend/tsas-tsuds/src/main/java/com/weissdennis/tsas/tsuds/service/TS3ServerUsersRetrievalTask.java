package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsuds.persistence.TS3ServerUsersEntity;
import com.weissdennis.tsas.tsuds.persistence.TS3ServerUsersRepository;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;

public class TS3ServerUsersRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final TS3ServerUsersRepository ts3ServerUsersRepository;

    public TS3ServerUsersRetrievalTask(TS3Api ts3Api, TS3ServerUsersRepository ts3ServerUsersRepository) {
        this.ts3Api = ts3Api;
        this.ts3ServerUsersRepository = ts3ServerUsersRepository;
    }

    @Override
    public void run() {
        long users = ts3Api.getClients()
                .stream()
                .filter(TS3ServerUsersRetrievalTask::isValidClient)
                .count();

        TS3ServerUsersEntity ts3ServerUsersEntity = new TS3ServerUsersEntity();
        ts3ServerUsersEntity.setDateTime(Instant.now());
        ts3ServerUsersEntity.setUsers(users);
        ts3ServerUsersRepository.save(ts3ServerUsersEntity);
    }

    private static boolean isValidClient(Client client) {
        return !client.isServerQueryClient();
    }
}
