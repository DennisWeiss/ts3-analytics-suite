package com.weissdennis.tsuds.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TS3UserInChannelRepository extends CrudRepository<TS3UserInChannel, TS3UserInChannelIdentity> {

}
