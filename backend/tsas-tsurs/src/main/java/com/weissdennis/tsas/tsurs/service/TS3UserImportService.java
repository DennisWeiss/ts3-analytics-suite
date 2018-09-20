package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsurs.mapper.TS3UserMapper;
import com.weissdennis.tsas.tsurs.persistence.TS3UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TS3UserImportService {

    private final TS3UserRepository ts3UserRepository;

    @Autowired
    public TS3UserImportService(TS3UserRepository ts3UserRepository) {
        this.ts3UserRepository = ts3UserRepository;
    }

    @KafkaListener(topics = "ts3_user", containerFactory = "ts3UserConcurrentKafkaListenerContainerFactory")
    public void listen(TS3User ts3User) {
        ts3UserRepository.save(TS3UserMapper.INSTANCE.ts3UserToTS3UserEntity(ts3User));
    }
}
