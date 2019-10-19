package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.UserRelation;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpectralClusteringService {

    private final TS3UserService ts3UserService;
    private final UserRelationService userRelationService;

    @Autowired
    public SpectralClusteringService(TS3UserService ts3UserService, UserRelationService userRelationService) {
        this.ts3UserService = ts3UserService;
        this.userRelationService = userRelationService;
    }


    public SimpleMatrix getGraphLaplacian(Double minRelation) {
        List<? extends TS3User> users = StreamSupport
                .stream(ts3UserService.getAllUsers().spliterator(), false)
                .collect(Collectors.toList());
        List<? extends UserRelation> relations = StreamSupport
                .stream(userRelationService.getRelations(null, minRelation).spliterator(), false)
                .collect(Collectors.toList());

        SimpleMatrix adjacencyMatrix = new SimpleMatrix(users.size(), users.size());

        Map<String, Integer> userToIndex = userToIndex(users);

        for (UserRelation relation : relations) {
            Integer index1 = userToIndex.get(relation.getClient1());
            Integer index2 = userToIndex.get(relation.getClient2());
            if (index1 != null && index2 != null) {
                adjacencyMatrix.set(index1, index2, 1);
                adjacencyMatrix.set(index2, index1, 1);
            }
        }

        return adjacencyMatrix;
    }

    private Map<String, Integer> userToIndex(List<? extends TS3User> users) {
        Map<String, Integer> userToIndex = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            userToIndex.put(users.get(i).getUniqueId(), i);
        }
        return userToIndex;
    }
}
