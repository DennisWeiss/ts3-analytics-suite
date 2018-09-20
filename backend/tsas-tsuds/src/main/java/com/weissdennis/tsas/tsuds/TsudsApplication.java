package com.weissdennis.tsas.tsuds;

import com.weissdennis.tsas.tsuds.configuration.Ts3PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication
@EnableConfigurationProperties(Ts3PropertiesConfig.class)
public class TsudsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsudsApplication.class, args);
    }
}
