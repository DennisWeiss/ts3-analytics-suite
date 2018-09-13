package com.weissdennis.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsuds.persistence.TS3UserInChannel;
import com.weissdennis.tsuds.persistence.TS3UserInChannelIdentity;
import com.weissdennis.tsuds.persistence.TS3UserInChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TS3UserInChannelRetrievalTask implements Runnable {

    private final TS3Api ts3Api;
    private TS3UserInChannelRepository ts3UserInChannelRepository;

    @Autowired
    public TS3UserInChannelRetrievalTask(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    @Autowired
    public void setTs3UserInChannelRepository() {

    }

    private static TS3UserInChannel mapFromClientAndTimestampToUserInChannel(Client client, Instant timestamp) {
        TS3UserInChannel TS3UserInChannel = new TS3UserInChannel();
        TS3UserInChannel.setTS3UserInChannelIdentity(new TS3UserInChannelIdentity(client.getUniqueIdentifier(), client.getChannelId()));
        TS3UserInChannel.setDateTime(timestamp);
        return TS3UserInChannel;
    }

    @Override
    public void run() {
        List<Client> clients = ts3Api.getClients();
        Instant dateTime = Instant.now();

        List<TS3UserInChannel> TS3UserInChannels = clients
                .parallelStream()
                .map(client -> mapFromClientAndTimestampToUserInChannel(client, dateTime))
                .collect(Collectors.toList());

        ts3UserInChannelRepository.saveAll(TS3UserInChannels);
    }
}
