package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.tsurs.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CreateUserPairTogetherTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserPairTogetherTableService.class);

    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final TS3UserRepository ts3UserRepository;
    private final TS3UserPairTogetherRepository ts3UserPairTogetherRepository;

    @Autowired
    public CreateUserPairTogetherTableService(TS3UserInChannelRepository ts3UserInChannelRepository,
                                              TS3UserRepository ts3UserRepository,
                                              TS3UserPairTogetherRepository ts3UserPairTogetherRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3UserPairTogetherRepository = ts3UserPairTogetherRepository;
    }

    public void createTable() {
        Iterable<TS3UserEntity> users = ts3UserRepository.findAllThatHaveBeenOnlineAfterAndIsNotBanned(
                Instant.now().minus(6, ChronoUnit.MONTHS)
        );

        LOGGER.info("Computing time together for " + ((Collection<?>) users).size() + " users");

        for (TS3UserEntity user1 : users) {
            LOGGER.info("Computing time together for user " + user1.getNickName());
            for (TS3UserEntity user2 : users) {
                if (!user1.getUniqueId().equals(user2.getUniqueId())) {
                    TS3UserPair userPair = new TS3UserPair(user1.getUniqueId(), user2.getUniqueId());
                    TS3UserPairTogetherEntity ts3UserPairTogetherEntity = new TS3UserPairTogetherEntity();
                    ts3UserPairTogetherEntity.setUserPair(userPair);
                    ts3UserPairTogetherEntity.setTimeTogether(
                            ts3UserInChannelRepository.weightedCountUsersInSameChannel(
                                    user1.getUniqueId(),
                                    user2.getUniqueId()
                            )
                    );

                    ts3UserPairTogetherRepository.save(ts3UserPairTogetherEntity);
                }
            }
        }
    }
}
