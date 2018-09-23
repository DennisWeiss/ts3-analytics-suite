package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.tsups.persistence.TS3UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TS3UserService {

    private final TS3UserRepository ts3UserRepository;

    @Autowired
    public TS3UserService(TS3UserRepository ts3UserRepository) {
        this.ts3UserRepository = ts3UserRepository;
    }

    public Iterable<? extends TS3User> getAllUsers() {
        return ts3UserRepository.findAll();
    }

    public Iterable<? extends TS3User> getAllUsersByNickname(String nickname) {
        return ts3UserRepository.findAllByNickName(nickname);
    }

    public Optional<? extends TS3User> getUserByUniqueId(String uniqueId) {
        return ts3UserRepository.findById(uniqueId);
    }
}
