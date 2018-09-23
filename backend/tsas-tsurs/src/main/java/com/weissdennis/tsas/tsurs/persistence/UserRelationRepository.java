package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationEntity, UserRelationIdentity> {

}
