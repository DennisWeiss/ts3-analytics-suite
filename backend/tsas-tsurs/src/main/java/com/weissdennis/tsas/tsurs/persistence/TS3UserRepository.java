package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TS3UserRepository extends CrudRepository<TS3UserEntity, String> {

    @Query(value = "select * from user u where u.banned=0 and (u.last_online is null or u.last_online > :lastOnline)", nativeQuery = true)
    List<TS3UserEntity> findAllThatHaveBeenOnlineAfterOrUnknownAndIsNotBanned(@Param("lastOnline") Instant lastOnline);

    @Query(value = "select * from user u where u.banned=0 and (u.last_online > :lastOnline)", nativeQuery = true)
    List<TS3UserEntity> findAllThatHaveBeenOnlineAfterAndIsNotBanned(@Param("lastOnline") Instant lastOnline);

    List<TS3UserEntity> findAllByBanned(boolean banned);
}
