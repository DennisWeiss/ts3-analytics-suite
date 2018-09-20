package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3ServerUsersMapper {

    TS3ServerUsersMapper INSTANCE = Mappers.getMapper(TS3ServerUsersMapper.class);

    TS3ServerUsersEntity ts3ServerUsersToTS3ServerUsersEntity(TS3ServerUsers ts3ServerUsers);

    TS3ServerUsers ts3ServerUsersEntityToTS3ServerUsers(TS3ServerUsersEntity ts3ServerUsersEntity);
}
