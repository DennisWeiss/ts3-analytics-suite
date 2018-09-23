package com.weissdennis.tsas.tsuds.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Ban;
import com.weissdennis.tsas.tsuds.mapper.CurrentUserMapper;
import com.weissdennis.tsas.tsuds.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CurrentUserService {

    private final TS3Api ts3Api;

    @Autowired
    public CurrentUserService(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public Iterable<CurrentUser> getCurrentUsers() {
        return ts3Api.getClients()
                .stream()
                .map(CurrentUserMapper.INSTANCE::clientToCurrentUser)
                .collect(Collectors.toList());
    }

    public Iterable<Ban> getBans() {
        return ts3Api.getBans();
    }
}
