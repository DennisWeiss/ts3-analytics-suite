package com.weissdennis.tsas.tsurs.service;

import com.weissdennis.tsas.tsurs.persistence.TS3UserInChannelRepository;
import com.weissdennis.tsas.tsurs.persistence.TS3UserRepository;
import com.weissdennis.tsas.tsurs.persistence.UserRelationRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class UserRelationService implements InitializingBean {

    private final TS3UserInChannelRepository ts3UserInChannelRepository;
    private final UserRelationRepository userRelationRepository;
    private final TS3UserRepository ts3UserRepository;

    public UserRelationService(TS3UserInChannelRepository ts3UserInChannelRepository,
                               UserRelationRepository userRelationRepository, TS3UserRepository ts3UserRepository) {
        this.ts3UserInChannelRepository = ts3UserInChannelRepository;
        this.userRelationRepository = userRelationRepository;
        this.ts3UserRepository = ts3UserRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateRelations();
    }

    private void updateRelations() {
        while (true) {

        }
    }
}
