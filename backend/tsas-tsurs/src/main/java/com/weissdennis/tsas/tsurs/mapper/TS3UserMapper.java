package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.persistence.TS3UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3UserMapper {

    TS3UserMapper INSTANCE = Mappers.getMapper(TS3UserMapper.class);

    TS3UserEntity ts3UserToTS3UserEntity(TS3User ts3User);

    TS3User ts3UserEntityToTS3USer(TS3UserEntity ts3UserEntity);
}
