package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3UserImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3UserMapper {

    TS3UserMapper INSTANCE = Mappers.getMapper(TS3UserMapper.class);

    TS3UserEntity ts3UserImplToTS3UserEntity(TS3UserImpl ts3UserImpl);

    TS3UserImpl ts3UserEntityToTS3UserImpl(TS3UserEntity ts3UserEntity);
}
