package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.tsuds.persistence.TS3UserInChannelEntity;
import com.weissdennis.tsas.tsuds.persistence.TS3UserInChannelIdentity;
import com.weissdennis.tsas.tsuds.persistence.TS3UserInChannelRepository;
import com.weissdennis.tsas.tsuds.persistence.TS3UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TS3UserInChannelRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final TS3UserRepository ts3UserRepository;

    public TS3UserInChannelRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                         TS3UserInChannelRepository ts3UserInChannelRepository, TS3UserRepository ts3UserRepository) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.ts3UserRepository = ts3UserRepository;
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
