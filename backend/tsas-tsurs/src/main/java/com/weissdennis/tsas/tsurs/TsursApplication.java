package com.weissdennis.tsas.tsurs;

import com.weissdennis.tsas.tsurs.service.CreateUserPairTogetherTableService;
import com.weissdennis.tsas.tsurs.service.UserRelationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class TsursApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TsursApplication.class, args);

//        context.getBean(CreateUserPairTogetherTableService.class).createTable();

        context.getBean(UserRelationService.class).updateRelations();
    }

}
