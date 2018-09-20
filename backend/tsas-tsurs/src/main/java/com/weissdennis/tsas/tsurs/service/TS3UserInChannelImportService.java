package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;
import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TS3UserInChannelImportService {

    private final TS3UserInChannelRepository ts3UserInChannelRepository;

    @Autowired
    public TS3UserInChannelImportService(TS3UserInChannelRepository ts3UserInChannelRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
    }

    @KafkaListener(topics = "ts3_user_in_channel")
    public void listen(TS3UserInChannel ts3UserInChannel) {
        ts3UserInChannelRepository.save(ts3UserInChannel);
    }
}
