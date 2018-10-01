package com.weissdennis.tsas.tsuds.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationEntity, UserRelationIdentity> {

    @Query(value = "select * from user_relation ur where ur.client1=:uniqueId", nativeQuery = true)
    Iterable<UserRelationEntity> findAllByClient1(@Param("uniqueId") String uniqueId);

}
