package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.IpRelation;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.model.Location;
import com.weissdennis.tsas.tsurs.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRelationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRelationService.class);

    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final UserRelationRepository userRelationRepository;
    private final TS3UserRepository ts3UserRepository;
    private final TS3UserPairTogetherRepository ts3UserPairTogetherRepository;

    @Autowired
    public UserRelationService(TS3UserInChannelRepository ts3UserInChannelRepository,
                               UserRelationRepository userRelationRepository, TS3UserRepository ts3UserRepository,
                               TS3UserPairTogetherRepository ts3UserPairTogetherRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.userRelationRepository = userRelationRepository;
        this.ts3UserRepository = ts3UserRepository;
        this.ts3UserPairTogetherRepository = ts3UserPairTogetherRepository;
    }

    public void updateRelations() {
        while (true) {
            List<TS3UserEntity> ts3Users = ts3UserRepository.findAllByBanned(false);

            for (TS3User user1 : ts3Users) {
                for (TS3User user2 : ts3Users) {
                    getAndSaveRelation(user1, user2);
                }
            }
        }
    }

    private void getAndSaveRelation(TS3User user1, TS3User user2) {
        if (!user1.getUniqueId().equals(user2.getUniqueId())) {
            LOGGER.info("Updating relation values for users " + user1.getUniqueId() + " and " + user2.getUniqueId());

            UserRelationIdentity userRelationIdentity = new UserRelationIdentity(user1.getUniqueId(), user2.getUniqueId());

            double channelRelation = getChannelRelation(user1, user2);
            double geoRelation = getGeoRelation(new Location(user1.getLatitude(), user1.getLongitude()),
                    new Location(user2.getLatitude(), user2.getLongitude()));
            IpRelation ipRelation = IpRelation.getRelation(user1.getIp(), user2.getIp());

            userRelationRepository.findById(userRelationIdentity)
                    .map(userRelationEntity -> {
                        userRelationEntity.setChannelRelation(channelRelation);
                        userRelationEntity.setGeoRelation(geoRelation);
                        userRelationEntity.setIpRelation(ipRelation);
                        return userRelationRepository.save(userRelationEntity);
                    })
                    .orElseGet(() -> userRelationRepository.save(
                            new UserRelationEntity(userRelationIdentity, geoRelation, channelRelation, ipRelation))
                    );
        }
    }

    private double getGeoRelation(Location location1, Location location2) {
        return location1.getLatitude() != null && location2.getLongitude() != null && location1.getLatitude() != null
                && location2.getLongitude() != null ? Math.max(0, -1f / 500 * location1.distanceTo(location2) + 1) : 0;
    }

    private double getChannelRelation(TS3User user1, TS3User user2) {
        Optional<TS3UserPairTogetherEntity> userPair = ts3UserPairTogetherRepository.findById(
                new TS3UserPair(user1.getUniqueId(), user2.getUniqueId())
        );

        if (userPair.isPresent()) {
            Double totalTimeInChannel = ts3UserPairTogetherRepository.getTotalTimeInChannel(user1.getUniqueId());
            LOGGER.info(totalTimeInChannel.toString());
            if (totalTimeInChannel > 0) {
                return userPair.get().getTimeTogether() / totalTimeInChannel;
            }
        }

        return 0;
    }
}
