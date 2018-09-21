package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.model.Location;
import com.weissdennis.tsas.tsurs.persistence.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserRelationService implements InitializingBean {

    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final UserRelationRepository userRelationRepository;
    private final TS3UserRepository ts3UserRepository;

    public UserRelationService(TS3UserInChannelRepository ts3UserInChannelRepository,
                               UserRelationRepository userRelationRepository, TS3UserRepository ts3UserRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.userRelationRepository = userRelationRepository;
        this.ts3UserRepository = ts3UserRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateRelations();
    }

    private void updateRelations() {
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
        UserRelationEntity userRelationEntity = new UserRelationEntity();
        userRelationEntity.setClient1(user1.getUniqueId());
        userRelationEntity.setClient2(user2.getUniqueId());
        userRelationEntity.setGeoRelation(getGeoRelation(new Location(user1.getLatitude(), user1.getLongitude()), new
                Location(user2.getLatitude(), user2.getLongitude())));
        userRelationEntity.setChannelRelation(getChannelRelation(user1, user2));

    }

    private double getGeoRelation(Location location1, Location location2) {
        return Math.max(0, -1f / 500 * location1.distanceTo(location2) + 1);
    }

    private double getChannelRelation(TS3User user1, TS3User user2) {
        return (double) (ts3UserInChannelRepository.countUsersInSameChannel
                (user1.getUniqueId(), user2.getUniqueId())) / ts3UserInChannelRepository.countTotalUsersInSameChannel
                (user1.getUniqueId());
    }
}
