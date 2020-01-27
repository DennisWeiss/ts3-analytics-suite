package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TS3UserPairTogetherRepository extends CrudRepository<TS3UserPairTogetherEntity, TS3UserPair> {

    @Query(value = "select sum(time_together) from ts3_user_pair_together where user1=:userId", nativeQuery = true)
    Double getTotalTimeInChannel(String userId);

}
