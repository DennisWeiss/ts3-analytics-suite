package com.weissdennis.tsuds.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TS3UserRepository extends CrudRepository<TS3User, String> {

    Optional<TS3User> findByUniqueId(String uniqueId);

    Optional<TS3User> findByClientId(Long clientId);

    List<TS3User> findAllByNickName(String nickName);
}
