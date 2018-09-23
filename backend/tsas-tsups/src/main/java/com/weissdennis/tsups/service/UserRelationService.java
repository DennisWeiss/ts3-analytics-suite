package com.weissdennis.tsups.service;

import com.weissdennis.tsas.common.ts3users.UserRelation;
import com.weissdennis.tsups.persistence.UserRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRelationService {

    private final UserRelationRepository userRelationRepository;

    @Autowired
    public UserRelationService(UserRelationRepository userRelationRepository) {
        this.userRelationRepository = userRelationRepository;
    }

    public Iterable<? extends UserRelation> getRelations(String userUniqueId) {
        return userUniqueId == null || userUniqueId.equals("") ? userRelationRepository.findAll() :
                userRelationRepository.findAllByClient1(userUniqueId);
    }
}
