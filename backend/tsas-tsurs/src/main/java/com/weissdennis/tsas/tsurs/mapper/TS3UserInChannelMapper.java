package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;
import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3UserInChannelMapper {

    TS3UserInChannelMapper INSTANCE = Mappers.getMapper(TS3UserInChannelMapper.class);

    TS3UserInChannelEntity ts3UserInChannelToTS3UserInChannelEntity(TS3UserInChannel ts3UserInChannel);

    TS3UserInChannel ts3UserInChannelEntityToTS3UserInChannel(TS3UserInChannelEntity ts3UserInChannelEntity);
}
