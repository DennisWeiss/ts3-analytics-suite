package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TS3UserRepository extends CrudRepository<TS3UserEntity, String> {

    List<TS3UserEntity> findAllByBannedAndLastOnlineIsAfter(boolean banned, Instant lastOnline);
}
