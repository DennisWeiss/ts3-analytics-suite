package com.weissdennis.tsuds.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weissdennis.tsuds.model.serializers.TS3UserInChannelDeserializer;
import com.weissdennis.tsuds.model.serializers.TS3UserInChannelSerializer;

import java.time.Instant;

@JsonSerialize(using = TS3UserInChannelSerializer.class)
@JsonDeserialize(using = TS3UserInChannelDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface TS3UserInChannel {

    Integer getChannelId();

    String getUniqueId();

    Instant getDateTime();
}
