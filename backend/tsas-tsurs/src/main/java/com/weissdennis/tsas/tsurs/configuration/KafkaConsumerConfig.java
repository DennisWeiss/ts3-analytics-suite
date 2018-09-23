package com.weissdennis.tsas.tsurs.configuration;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.TS3UserInChannel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "kafka-conf")
public class KafkaConsumerConfig {

    private String bootstrapAddress;
    private String groupId;

    public KafkaConsumerConfig() {
    }

    private Map<String, Object> getDefaultConfigProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.weissdennis.tsas.common.ts3users");
        return props;
    }

    @Bean
    public ConsumerFactory<String, TS3User> ts3UserConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TS3User> ts3UserConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TS3User> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ts3UserConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TS3UserInChannel> ts3UserInChannelConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TS3UserInChannel>
    ts3UserInChannelConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TS3UserInChannel> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ts3UserInChannelConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TS3ServerUsers> ts3ServerUsersConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getDefaultConfigProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TS3ServerUsers>
    ts3ServerUsersConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TS3ServerUsers> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ts3ServerUsersConsumerFactory());
        return factory;
    }

    public String getBootstrapAddress() {
        return bootstrapAddress;
    }

    public void setBootstrapAddress(String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
