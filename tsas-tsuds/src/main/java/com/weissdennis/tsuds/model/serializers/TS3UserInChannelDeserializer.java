package com.weissdennis.tsuds.model.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.weissdennis.tsuds.model.TS3UserInChannel;
import com.weissdennis.tsuds.model.TS3UserInChannelImpl;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.io.IOException;
import java.time.Instant;

public class TS3UserInChannelDeserializer extends StdDeserializer<TS3UserInChannel> {

    public TS3UserInChannelDeserializer() {
        this(null);
    }

    public TS3UserInChannelDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TS3UserInChannel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String userUniqueId = node.get("user").asText();
        Instant timestamp = Instant.parse(node.get("timestamp").asText());
        int channelId = node.get("channel").asInt();

        return new TS3UserInChannelImpl(userUniqueId, timestamp, channelId);
    }
}
