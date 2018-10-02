package com.weissdennis.tsas.common.ts3users.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;
import com.weissdennis.tsas.common.ts3users.TS3UserInChannelImpl;

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
        int dataInterval = node.get("data_interval").asInt();

        return new TS3UserInChannelImpl(userUniqueId, timestamp, channelId, dataInterval);
    }
}
