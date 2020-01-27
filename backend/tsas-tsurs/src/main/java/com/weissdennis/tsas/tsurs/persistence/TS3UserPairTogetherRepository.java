package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TS3UserPairTogetherRepository extends CrudRepository<TS3UserPairTogetherEntity, TS3UserPair> {

    @Query(value = "select sum(time_together) from ts3_user_pair_together upt where upt.user1=:userId", nativeQuery = true)
    Double getTotalTimeInChannel(@Param("userId") String userId);

}
