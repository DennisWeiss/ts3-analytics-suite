package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.common.ts3users.TS3ServerUsersImpl;
import com.weissdennis.tsas.tsurs.mapper.TS3ServerUsersMapper;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersEntity;
import com.weissdennis.tsas.tsurs.persistence.TS3ServerUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TS3ServerUsersImportService {

    private final TS3ServerUsersRepository ts3ServerUsersRepository;

    @Autowired
    public TS3ServerUsersImportService(TS3ServerUsersRepository ts3ServerUsersRepository) {
        this.ts3ServerUsersRepository = ts3ServerUsersRepository;
    }

    @KafkaListener(topics = "ts3_server_users", containerFactory = "ts3ServerUsersConcurrentKafkaListenerContainerFactory")
    public void listen(TS3ServerUsers ts3ServerUsers) {
        TS3ServerUsersEntity ts3ServerUsersEntity = new TS3ServerUsersEntity();
        ts3ServerUsersEntity.setDateTime(ts3ServerUsers.getDateTime());
        ts3ServerUsersEntity.setUsers(ts3ServerUsers.getUsers());

        ts3ServerUsersRepository.save(ts3ServerUsersEntity);
    }
}
