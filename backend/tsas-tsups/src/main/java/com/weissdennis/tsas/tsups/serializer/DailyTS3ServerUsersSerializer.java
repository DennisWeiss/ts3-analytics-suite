package com.weissdennis.tsas.tsups.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.weissdennis.tsas.tsups.model.DailyTS3ServerUsers;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class DailyTS3ServerUsersSerializer extends StdSerializer<DailyTS3ServerUsers> {

    private static String DATE_PATTERN = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    public DailyTS3ServerUsersSerializer() {
        this(null);
    }

    public DailyTS3ServerUsersSerializer(Class<DailyTS3ServerUsers> t) {
        super(t);
    }

    @Override
    public void serialize(DailyTS3ServerUsers dailyTS3ServerUsers, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("date", simpleDateFormat.format(dailyTS3ServerUsers.getDate()));
        jsonGenerator.writeNumberField("users", dailyTS3ServerUsers.getUsers());
        jsonGenerator.writeEndObject();
    }
}
