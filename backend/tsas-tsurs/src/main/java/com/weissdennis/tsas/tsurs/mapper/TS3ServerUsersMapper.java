package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3ServerUsersMapper {

    TS3ServerUsersMapper INSTANCE = Mappers.getMapper(TS3ServerUsersMapper.class);

    TS3ServerUsersEntity ts3ServerUsersImplToTS3ServerUsersEntity(TS3ServerUsersImpl ts3ServerUsersImpl);

    TS3ServerUsersImpl ts3ServerUsersEntityToTS3ServerUsersImpl(TS3ServerUsersEntity ts3ServerUsersEntity);
}
