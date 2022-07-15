package ru.the_boring_developers.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Configuration
public class JacksonOffsetDateTimeMapper {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(OffsetDateTime.class, new CustomSerializer());
        module.addDeserializer(OffsetDateTime.class, new CustomDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    class CustomDeserializer extends JsonDeserializer<OffsetDateTime> {

        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(parser.getText())), ZoneId.systemDefault());
        }
    }

    class CustomSerializer extends JsonSerializer<OffsetDateTime> {

        @Override
        public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(String.valueOf(value.toInstant().toEpochMilli()));
        }
    }
}
