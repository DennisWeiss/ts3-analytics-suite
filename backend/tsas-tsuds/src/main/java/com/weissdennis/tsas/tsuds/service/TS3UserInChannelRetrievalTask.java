package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;
import com.weissdennis.tsas.common.ts3users.TS3UserInChannelImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TS3UserInChannelRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private final Ts3PropertiesConfig ts3PropertiesConfig;
    private final KafkaTemplate<String, TS3UserInChannel> ts3UserInChannelKafkaTemplate;

    public TS3UserInChannelRetrievalTask(TS3Api ts3Api, Ts3PropertiesConfig ts3PropertiesConfig,
                                         KafkaTemplate<String, TS3UserInChannel> ts3UserInChannelKafkaTemplate) {
        this.ts3Api = ts3Api;
        this.ts3PropertiesConfig = ts3PropertiesConfig;
        this.ts3UserInChannelKafkaTemplate = ts3UserInChannelKafkaTemplate;
    }

    private static TS3UserInChannel mapFromClientAndTimestampToUserInChannel(Client client, Instant timestamp) {
        TS3UserInChannelImpl ts3UserInChannel = new TS3UserInChannelImpl();
        ts3UserInChannel.setUniqueId(client.getUniqueIdentifier());
        ts3UserInChannel.setDateTime(timestamp);
        ts3UserInChannel.setChannelId(client.getChannelId());
        return ts3UserInChannel;
    }

    @Override
    public void run() {
        List<Client> clients = ts3Api.getClients();
        Instant dateTime = Instant.now();

        clients
                .stream()
                .filter(this::isValidClient)
                .map(client -> mapFromClientAndTimestampToUserInChannel(client, dateTime))
                .forEach(ts3UserInChannel -> ts3UserInChannelKafkaTemplate.send("ts3_user_in_channel", ts3UserInChannel));
    }

    private boolean isValidClient(Client client) {
        return !client.isAway() && !client.isServerQueryClient() && !ts3PropertiesConfig.getParsedAfkChannels().contains(client.getChannelId());
    }
}
