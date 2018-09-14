package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsuds.persistence.TS3ServerUsers;
import com.weissdennis.tsuds.persistence.TS3ServerUsersRepository;

import java.time.Instant;

public class TS3ServerUsersRetrievalTask implements Runnable {

    private TS3Api ts3Api;
    private TS3ServerUsersRepository ts3ServerUsersRepository;

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

        ts3ServerUsersRepository.save(new TS3ServerUsers(Instant.now(), users));
    }

    private static boolean isValidClient(Client client) {
        return !client.isServerQueryClient();
    }
}
