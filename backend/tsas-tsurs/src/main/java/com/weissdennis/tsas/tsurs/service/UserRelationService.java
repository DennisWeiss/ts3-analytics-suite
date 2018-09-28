package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.IpRelation;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.model.Location;
import com.weissdennis.tsas.tsurs.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserRelationService {

    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final UserRelationRepository userRelationRepository;
    private final TS3UserRepository ts3UserRepository;

    @Autowired
    public UserRelationService(TS3UserInChannelRepository ts3UserInChannelRepository,
                               UserRelationRepository userRelationRepository, TS3UserRepository ts3UserRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.userRelationRepository = userRelationRepository;
        this.ts3UserRepository = ts3UserRepository;
    }

    public void updateRelations() {
        while (true) {
            LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
            List<TS3UserEntity> ts3Users = ts3UserRepository.findAllThatHaveBeenOnlineAfterOrUnknownAndIsNotBanned(
                    false, threeMonthsAgo.toInstant(ZoneId.systemDefault().getRules().getOffset(threeMonthsAgo)));

            for (TS3User user1 : ts3Users) {
                for (TS3User user2 : ts3Users) {
                    getAndSaveRelation(user1, user2);
                }
            }
        }
    }

    private void getAndSaveRelation(TS3User user1, TS3User user2) {
        if (!user1.getUniqueId().equals(user2.getUniqueId())) {
            double channelRelation = getChannelRelation(user1, user2);
            UserRelationEntity s = new UserRelationEntity(new UserRelationIdentity(
                    user1.getUniqueId(), user2.getUniqueId()), getGeoRelation(
                    new Location(user1.getLatitude(), user1.getLongitude()),
                    new Location(user2.getLatitude(), user2.getLongitude())), channelRelation,
                    IpRelation.getRelation(user1.getIp(), user2.getIp()));
            userRelationRepository.save(s);
        }
    }

    private double getGeoRelation(Location location1, Location location2) {
        return location1.getLatitude() != null && location2.getLongitude() != null && location1.getLatitude() != null
                && location2.getLongitude() != null ? Math.max(0, -1f / 500 * location1.distanceTo(location2) + 1) : 0;
    }

    private double getChannelRelation(TS3User user1, TS3User user2) {
        Double sameChannelWithThisUser = ts3UserInChannelRepository.weightedCountUsersInSameChannel(user1.getUniqueId(), user2.getUniqueId());
        Double sameChannelWithAnyUser = ts3UserInChannelRepository.weightedCountTotalUsersInSameChannel(user1.getUniqueId());
        return sameChannelWithThisUser != null && sameChannelWithAnyUser != null && sameChannelWithAnyUser != 0 ?
                sameChannelWithThisUser / sameChannelWithAnyUser : 0;
    }
}
