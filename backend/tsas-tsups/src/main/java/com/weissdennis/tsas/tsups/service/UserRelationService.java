package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.UserRelation;
import com.weissdennis.tsas.tsups.persistence.UserRelationEntity;
import com.weissdennis.tsas.tsups.persistence.UserRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserRelationService {

    private final UserRelationRepository userRelationRepository;

    @Autowired
    public UserRelationService(UserRelationRepository userRelationRepository) {
        this.userRelationRepository = userRelationRepository;
    }

    public Iterable<? extends UserRelation> getRelations(String userUniqueId, double minRelation) {
        Iterable<UserRelationEntity> userRelationEntities;
        if (userUniqueId == null || userUniqueId.equals("")) {
            userRelationEntities = userRelationRepository.findAllRelations();
        } else {
            userRelationEntities = userRelationRepository.findAllByClient1(userUniqueId);
        }

        return StreamSupport.stream(userRelationEntities.spliterator(), false)
                        .filter(userRelationEntity -> userRelationEntity.getTotalRelation() >= minRelation)
                        .sorted((a, b) -> (int) Math.signum(b.getTotalRelation() - a.getTotalRelation()))
                        .collect(Collectors.toList());
    }
}
