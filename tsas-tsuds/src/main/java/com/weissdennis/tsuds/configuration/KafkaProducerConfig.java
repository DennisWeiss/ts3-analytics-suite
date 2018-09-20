package com.weissdennis.tsuds.configuration;

import com.weissdennis.tsuds.model.TS3ServerUsers;
import com.weissdennis.tsuds.model.TS3User;
import com.weissdennis.tsuds.model.TS3UserInChannel;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerConfig {

    private String bootstrapAddress;

    private Map<String, Object> getDefaultConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, TS3User> ts3UserProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public KafkaTemplate<String, TS3User> ts3UserKafkaTemplate() {
        return new KafkaTemplate<>(ts3UserProducerFactory());
    }

    @Bean
    public ProducerFactory<String, TS3UserInChannel> ts3UserInChannelProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public KafkaTemplate<String, TS3UserInChannel> ts3UserInChannelKafkaTemplate() {
        return new KafkaTemplate<>(ts3UserInChannelProducerFactory());
    }

    @Bean
    public ProducerFactory<String, TS3ServerUsers> ts3ServerUsersProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public KafkaTemplate<String, TS3ServerUsers> ts3ServerUsersKafkaTemplate() {
        return new KafkaTemplate<>(ts3ServerUsersProducerFactory());
    }

    public String getBootstrapAddress() {
        return bootstrapAddress;
    }

    public void setBootstrapAddress(String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }
}
