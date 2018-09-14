package com.weissdennis.tsuds.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TS3ServerUsersRepository extends CrudRepository<TS3ServerUsers, Instant> {

    List<TS3ServerUsers> findAllByDateTimeBeforeAndDateTimeAfter(Instant from, Instant to);

}
