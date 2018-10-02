package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.TS3ServerUsers;
import com.weissdennis.tsas.tsups.persistence.TS3ServerUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TS3ServerUsersService {

    private final TS3ServerUsersRepository ts3ServerUsersRepository;

    @Autowired
    public TS3ServerUsersService(TS3ServerUsersRepository ts3ServerUsersRepository) {
        this.ts3ServerUsersRepository = ts3ServerUsersRepository;
    }

    public Iterable<? extends TS3ServerUsers> getServerUsers(LocalDateTime from, LocalDateTime to) {
        return ts3ServerUsersRepository.findAllByDateTimeBeforeAndDateTimeAfter(from.toInstant(ZoneOffset.systemDefault()
                .getRules().getOffset(from)), to.toInstant(ZoneOffset.systemDefault().getRules().getOffset(to)));
    }

}
