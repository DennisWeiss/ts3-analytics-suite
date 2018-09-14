package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsuds.persistence.TS3UserInChannel;
import com.weissdennis.tsuds.persistence.TS3UserInChannelIdentity;
import com.weissdennis.tsuds.persistence.TS3UserInChannelRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TS3UserInChannelRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final TS3UserInChannelRepository ts3UserInChannelRepository;

    public TS3UserInChannelRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig, TS3UserInChannelRepository ts3UserInChannelRepository) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
    }

    private static TS3UserInChannel mapFromClientAndTimestampToUserInChannel(Client client, Instant timestamp) {
        TS3UserInChannel TS3UserInChannel = new TS3UserInChannel();
        TS3UserInChannel.setTS3UserInChannelIdentity(new TS3UserInChannelIdentity(client.getUniqueIdentifier(), timestamp));
        TS3UserInChannel.setChannelId(client.getChannelId());
        return TS3UserInChannel;
    }

    @Override
    public void run() {
        List<Client> clients = ts3Api.getClients();
        Instant dateTime = Instant.now();

        List<TS3UserInChannel> TS3UserInChannels = clients
                .stream()
                .filter(this::isValidClient)
                .map(client -> mapFromClientAndTimestampToUserInChannel(client, dateTime))
                .collect(Collectors.toList());

        ts3UserInChannelRepository.saveAll(TS3UserInChannels);
    }

    private boolean isValidClient(Client client) {
        return !client.isAway() && !client.isServerQueryClient() && !ts3PropertiesConfig.getParsedAfkChannels().contains(client.getChannelId());
    }
}
