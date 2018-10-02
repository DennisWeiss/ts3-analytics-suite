package com.weissdennis.tsas.common.ts3users.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;

import java.io.IOException;
import java.time.Instant;

public class TS3ServerUsersDeserializer extends StdDeserializer<TS3ServerUsers> {

    public TS3ServerUsersDeserializer() {
        this(null);
    }

    public TS3ServerUsersDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TS3ServerUsers deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Instant dateTime = Instant.ofEpochMilli(node.get(0).asLong());
        long users = node.get(1).asLong();

        return new TS3ServerUsersImpl(dateTime, users);
    }
}
