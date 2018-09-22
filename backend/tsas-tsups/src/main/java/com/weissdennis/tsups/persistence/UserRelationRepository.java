package com.weissdennis.tsups.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationEntity, UserRelationIdentity> {

    Iterable<UserRelationEntity> findAllByClient1(String userUniqueId);

}
