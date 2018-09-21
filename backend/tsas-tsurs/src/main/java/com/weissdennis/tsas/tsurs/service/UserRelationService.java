package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.IpRelation;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.model.Location;
import com.weissdennis.tsas.tsurs.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class UserRelationService  {

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

    @PostConstruct
    public void updateRelations() {
        while (true) {
            LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
            List<TS3UserEntity> ts3Users = ts3UserRepository.findAllByBannedAndLastOnlineIsAfter(false, threeMonthsAgo
                    .toInstant(ZoneId.systemDefault().getRules().getOffset(threeMonthsAgo)));

            ts3Users.parallelStream()
                    .forEach(user1 -> ts3Users.parallelStream()
                            .forEach(user2 -> getAndSaveRelation(user1, user2)));
        }
    }

    private void getAndSaveRelation(TS3User user1, TS3User user2) {
        if (user1.getUniqueId() != user2.getUniqueId()) {
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
        Long sameChannelWithThisUser = ts3UserInChannelRepository.countUsersInSameChannel(user1.getUniqueId(),
                user2.getUniqueId());
        Long sameChannelWithAnyUser = ts3UserInChannelRepository.countTotalUsersInSameChannel
                (user1.getUniqueId());
        return sameChannelWithAnyUser != 0 ? (double) sameChannelWithThisUser / sameChannelWithAnyUser : 0;
    }
}
