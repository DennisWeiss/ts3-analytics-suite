package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.tsuds.persistence.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class TS3UserInChannelRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final TS3UserRepository ts3UserRepository;
    private final TS3UserPairTogetherRepository ts3UserPairTogetherRepository;

    public TS3UserInChannelRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                         TS3UserInChannelRepository ts3UserInChannelRepository,
                                         TS3UserRepository ts3UserRepository,
                                         TS3UserPairTogetherRepository ts3UserPairTogetherRepository) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3UserPairTogetherRepository = ts3UserPairTogetherRepository;
    }

    private TS3UserInChannelEntity mapFromClientAndTimestampToUserInChannelEntity (Client client, Instant timestamp) {
        TS3UserInChannelEntity ts3UserInChannel = new TS3UserInChannelEntity();
        ts3UserInChannel.setTs3UserInChannelIdentity(new TS3UserInChannelIdentity(client.getUniqueIdentifier(), timestamp));
        ts3UserInChannel.setChannelId(client.getChannelId());
        ts3UserInChannel.setDataInterval(ts3PropertiesConfig.getUserInChannelInterval());
        return ts3UserInChannel;
    }

    @Override
    public void run() {
        List<Client> clients = ts3Api.getClients();
        Instant dateTime = Instant.now();

        saveClientsInChannel(clients, dateTime);

        saveClientsTogetherInChannel(clients);
    }

    private void saveClientsTogetherInChannel(List<Client> clients) {
        Map<Integer, Set<String>> channelToClients = new HashMap<>();

        for (Client client : clients) {
            channelToClients.computeIfAbsent(client.getChannelId(), k -> new HashSet<>());
            channelToClients.get(client.getChannelId()).add(client.getUniqueIdentifier());
        }

        for (Map.Entry<Integer, Set<String>> entry : channelToClients.entrySet()) {
            for (String user1 : entry.getValue()) {
                for (String user2 : entry.getValue()) {
                    if (!user1.equals(user2)) {
                        TS3UserPair ts3UserPair = new TS3UserPair(user1, user2);

                        Optional<TS3UserPairTogetherEntity> userPairOptional =
                                ts3UserPairTogetherRepository.findById(ts3UserPair);

                        TS3UserPairTogetherEntity ts3UserPairTogetherEntity = new TS3UserPairTogetherEntity();

                        if (userPairOptional.isPresent()) {
                            ts3UserPairTogetherEntity = userPairOptional.get();
                            ts3UserPairTogetherEntity.addTime(ts3PropertiesConfig.getUserInChannelInterval());

                        } else {
                            ts3UserPairTogetherEntity.setUserPair(ts3UserPair);
                            ts3UserPairTogetherEntity.setTimeTogether(ts3PropertiesConfig.getUserInChannelInterval());
                        }

                        ts3UserPairTogetherRepository.save(ts3UserPairTogetherEntity);
                    }
                }
            }
        }
    }

    private void saveClientsInChannel(List<Client> clients, Instant dateTime) {
        clients.stream()
                .filter(this::isValidClient)
                .map(client -> mapFromClientAndTimestampToUserInChannelEntity(client, dateTime))
                .forEach(ts3UserInChannelEntity -> {
                    ts3UserInChannelRepository.save(ts3UserInChannelEntity);
                    ts3UserRepository
                            .findById(ts3UserInChannelEntity.getUniqueId())
                            .ifPresent(ts3UserEntity -> {
                                ts3UserEntity.setLastOnline(ts3UserInChannelEntity.getDateTime());
                                ts3UserRepository.save(ts3UserEntity);
                            });
                });
    }

    private boolean isValidClient(Client client) {
        return !client.isAway() && !client.isServerQueryClient() && !ts3PropertiesConfig.getParsedAfkChannels().contains(client.getChannelId());
    }
}
