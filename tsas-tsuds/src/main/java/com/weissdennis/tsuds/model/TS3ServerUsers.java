package com.weissdennis.tsuds.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weissdennis.tsuds.model.serializers.TS3ServerUsersDeserializer;
import com.weissdennis.tsuds.model.serializers.TS3ServerUsersSerializer;

import java.time.Instant;

@JsonSerialize(using = TS3ServerUsersSerializer.class)
@JsonDeserialize(using = TS3ServerUsersDeserializer.class)
public interface TS3ServerUsers {

    Instant getDateTime();

    Long getUsers();
}
