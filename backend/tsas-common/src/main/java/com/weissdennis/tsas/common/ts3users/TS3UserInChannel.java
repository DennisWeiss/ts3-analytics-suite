package com.weissdennis.tsas.common.ts3users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weissdennis.tsas.common.ts3users.serializers.TS3UserInChannelDeserializer;
import com.weissdennis.tsas.common.ts3users.serializers.TS3UserInChannelSerializer;

import java.time.Instant;

@JsonSerialize(using = TS3UserInChannelSerializer.class)
@JsonDeserialize(using = TS3UserInChannelDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface TS3UserInChannel {

    Integer getChannelId();

    String getUniqueId();

    Instant getDateTime();

    Integer getInterval();

}
