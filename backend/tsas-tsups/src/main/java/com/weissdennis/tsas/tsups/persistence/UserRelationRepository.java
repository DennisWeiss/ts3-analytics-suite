package com.weissdennis.tsas.tsups.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationEntity, UserRelationIdentity> {

    @Query(value = "select * from user_relation ur where ur.client1=:uniqueId", nativeQuery = true)
    Iterable<UserRelationEntity> findAllByClient1(@Param("uniqueId") String uniqueId);

    @Query(value = "SELECT relations.client1, relations.client2, relations.geo_relation, " +
            "(relations.channel_relation+relations2.channel_relation)/2 AS channel_relation, relations.ip_relation FROM user_relation AS relations " +
            "JOIN user_relation AS relations2 ON relations.client2 = relations2.client1 AND relations.client1 = relations2.client2 " +
            "WHERE relations.channel_relation > 0.01 AND relations2.channel_relation > 0.01 " +
            "ORDER BY channel_relation DESC", nativeQuery = true)
    Iterable<UserRelationEntity> findAllRelations();

}
