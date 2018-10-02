package com.weissdennis.tsas.tsups.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;

@Repository
public interface TS3ServerUsersRepository extends CrudRepository<TS3ServerUsersEntity, Instant> {

    Iterable<TS3ServerUsersEntity> findAllByDateTimeAfterAndDateTimeBefore(Instant from, Instant to);

}
