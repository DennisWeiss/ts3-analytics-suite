package com.weissdennis.tsas.tsuds.mapper;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.tsas.tsuds.model.CurrentUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentUserMapper {

    CurrentUserMapper INSTANCE = Mappers.getMapper(CurrentUserMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "uniqueIdentifier"),
            @Mapping(target = "channel", source = "channelId")
    })
    CurrentUser clientToCurrentUser(Client client);
}
