package com.weissdennis.tsas.tsups.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface TS3UserRepository extends CrudRepository<TS3UserEntity, String> {

    Iterable<TS3UserEntity> findAllByBannedAndLastOnlineIsAfter(boolean banned, Instant lastOnline);

    Iterable<TS3UserEntity> findAllByNickName(String nickname);
}
