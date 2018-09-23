package com.weissdennis.tsas.tsups.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface TS3ServerUsersRepository extends CrudRepository<TS3ServerUsersEntity, Instant> {

}
