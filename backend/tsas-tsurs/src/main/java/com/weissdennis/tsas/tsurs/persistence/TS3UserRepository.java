package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TS3UserRepository extends CrudRepository<TS3UserEntity, String> {
}
