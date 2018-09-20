package com.weissdennis.tsuds.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.weissdennis.tsuds.model.TS3UserInChannel;

import java.io.IOException;

public class TS3UserInChannelSerializer extends StdSerializer<TS3UserInChannel> {

    public TS3UserInChannelSerializer() {
        this(null);
    }

    public TS3UserInChannelSerializer(Class<TS3UserInChannel> t) {
        super(t);
    }

    @Override
    public void serialize(TS3UserInChannel ts3UserInChannel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("user", ts3UserInChannel.getUniqueId());
        jsonGenerator.writeStringField("timestamp", ts3UserInChannel.getDateTime().toString());
        jsonGenerator.writeNumberField("channel", ts3UserInChannel.getChannelId());
        jsonGenerator.writeEndObject();
    }
}
