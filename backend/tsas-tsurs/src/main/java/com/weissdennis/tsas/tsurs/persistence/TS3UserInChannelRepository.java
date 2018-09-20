package com.weissdennis.tsas.tsurs.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TS3UserInChannelRepository extends CrudRepository<TS3UserInChannelEntity, TS3UserInChannelIdentity> {
}
