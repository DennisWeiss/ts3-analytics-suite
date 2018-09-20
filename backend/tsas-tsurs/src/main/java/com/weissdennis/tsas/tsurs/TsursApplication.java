package com.weissdennis.tsas.tsurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class TsursApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsursApplication.class, args);
    }
}
