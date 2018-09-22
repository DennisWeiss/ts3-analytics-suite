package com.weissdennis.tsups.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TS3UserInChannelRepository extends CrudRepository<TS3UserInChannelEntity, TS3UserInChannelIdentity> {

    //TODO: these queries need to be fixed

    @Query(value = "select count(uic1.unique_id) from user_in_channel uic1, user_in_channel uic2 where " +
            "uic1.date_time=uic2.date_time " +
            "and uic1.unique_id=:user1 and uic2.unique_id=:user2 and uic1.channel_id=uic2.channel_id", nativeQuery =
            true)
    Long countUsersInSameChannel(@Param("user1") String user1, @Param("user2") String user2);

    @Query(value = "select count(uic1.unique_id) from user_in_channel uic1, user_in_channel uic2 where " +
            "uic1.date_time=uic2.date_time " +
            "and uic1.unique_id=:user1 and uic1.channel_id=uic2.channel_id and not uic1.unique_id=uic2.unique_id",
            nativeQuery = true)
    Long countTotalUsersInSameChannel(@Param("user1") String user);
}
