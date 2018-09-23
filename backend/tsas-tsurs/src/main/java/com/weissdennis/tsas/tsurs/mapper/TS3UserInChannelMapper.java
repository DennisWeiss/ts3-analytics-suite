package com.weissdennis.tsas.tsurs.mapper;

import com.weissdennis.tsas.common.ts3users.TS3UserInChannelImpl;
import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TS3UserInChannelMapper {

    TS3UserInChannelMapper INSTANCE = Mappers.getMapper(TS3UserInChannelMapper.class);

    @Mappings({
            @Mapping(target = "ts3UserInChannelIdentity.uniqueId", source = "uniqueId"),
            @Mapping(target = "ts3UserInChannelIdentity.dateTime", source = "dateTime")
    })
    TS3UserInChannelEntity ts3UserInChannelImplToTS3UserInChannelEntity(TS3UserInChannelImpl ts3UserInChannelImpl);

    @InheritInverseConfiguration
    TS3UserInChannelImpl ts3UserInChannelEntityToTS3UserInChannelImpl(TS3UserInChannelEntity ts3UserInChannelEntity);
}
