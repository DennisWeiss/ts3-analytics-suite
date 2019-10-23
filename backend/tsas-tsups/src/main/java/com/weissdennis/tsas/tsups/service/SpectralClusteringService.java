package com.weissdennis.tsas.tsups.service;

import com.weissdennis.tsas.common.ts3users.TS3User;
import com.weissdennis.tsas.common.ts3users.UserRelation;
import com.weissdennis.tsas.tsups.model.TS3UserGraphLaplacianEigenDecomposition;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Eigenpair;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.EigenDecomposition;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public TS3UserGraphLaplacianEigenDecomposition getEigenDecomposition(Double minRelation) {
        Pair<SimpleMatrix, List<String>> graphLaplacianResult = getGraphLaplacian(minRelation);
        SimpleMatrix graphLaplacian = graphLaplacianResult.getFirst();

        EigenDecomposition<DenseMatrix64F> eigenValueDecomposition = DecompositionFactory.eig(
                graphLaplacian.getMatrix().getNumRows(), true
        );
        eigenValueDecomposition.decompose(graphLaplacian.getMatrix());

        TS3UserGraphLaplacianEigenDecomposition laplacianEigenDecomposition =
                new TS3UserGraphLaplacianEigenDecomposition();

        laplacianEigenDecomposition.setUsers(graphLaplacianResult.getSecond());

        for (int i = 0; i < eigenValueDecomposition.getNumberOfEigenvalues(); i++) {
            laplacianEigenDecomposition.getEigenPairs().add(new Eigenpair(
                    eigenValueDecomposition.getEigenvalue(i).getReal(), eigenValueDecomposition.getEigenVector(i)
            ));
        }

        laplacianEigenDecomposition.getEigenPairs().sort((a, b) -> (int) Math.signum(a.value - b.value));

        return laplacianEigenDecomposition;
    }

    public List<Complex64F> getEigenValues(Double minRelation) {
        Pair<SimpleMatrix, List<String>> graphLaplacianResult = getGraphLaplacian(minRelation);
        SimpleMatrix graphLaplacian = graphLaplacianResult.getFirst();

        EigenDecomposition<DenseMatrix64F> eigenValueDecomposition = DecompositionFactory.eig(
                graphLaplacian.getMatrix().getNumRows(), true
        );
        eigenValueDecomposition.decompose(graphLaplacian.getMatrix());

        TS3UserGraphLaplacianEigenDecomposition laplacianEigenDecomposition =
                new TS3UserGraphLaplacianEigenDecomposition();

        List<Complex64F> eigenValues = new ArrayList<>();

        for (int i = 0; i < eigenValueDecomposition.getNumberOfEigenvalues(); i++) {
            eigenValues.add(eigenValueDecomposition.getEigenvalue(i));
        }

        return eigenValues;
    }

    private Pair<SimpleMatrix, List<String>> getGraphLaplacian(Double minRelation) {
        List<? extends UserRelation> relations = StreamSupport
                .stream(userRelationService.getRelations(null, minRelation).spliterator(), false)
                .collect(Collectors.toList());

        List<String> usersWithRelations = filterUsersWithRelations(relations);

        Pair<SimpleMatrix, List<String>> adjacencyMatrixResult = adjacencyMatrix(usersWithRelations, relations);
//        adjacencyMatrixResult = adjacencyMatrixOfUsersConnectedWith(adjacencyMatrixResult, "U5lc4kJ/JZQW908kxsJ+B64VSp4=");
        SimpleMatrix adjacencyMatrix = adjacencyMatrixResult.getFirst();

        SimpleMatrix graphLaplacian = degreeMatrix(adjacencyMatrix).minus(adjacencyMatrix);
        List<String> users = adjacencyMatrixResult.getSecond();

        return Pair.of(graphLaplacian, users);
    }

    private Pair<SimpleMatrix, List<String>> adjacencyMatrixOfUsersConnectedWith(
            Pair<SimpleMatrix, List<String>> adjacencyMatrixResult, String userId
    ) {
        DenseMatrix64F adjacencyMatrix = adjacencyMatrixResult.getFirst().getMatrix();
        List<String> users = adjacencyMatrixResult.getSecond();

        Map<String, Integer> userToIndex = userToIndex(users);

        Set<String> connectedUsers = new HashSet<>();
        int userIdIndex = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(userId)) {
                userIdIndex = i;
                break;
            }
        }

        if (userIdIndex == -1) {
            return null;
        }

        connectedUsers = connectedUserWithUser(connectedUsers, users, adjacencyMatrix, userIdIndex);

        List<String> connectedUsersList = new ArrayList<>(connectedUsers);

        SimpleMatrix adjacencyMatrixOfConnectedUsers = new SimpleMatrix(connectedUsersList.size(), connectedUsersList.size());

        for (int i = 0; i < adjacencyMatrixOfConnectedUsers.getMatrix().getNumRows(); i++) {
            for (int j = 0; j < adjacencyMatrixOfConnectedUsers.getMatrix().getNumCols(); j++) {
                adjacencyMatrixOfConnectedUsers.set(i, j, adjacencyMatrix.get(
                        userToIndex.get(connectedUsersList.get(i)), userToIndex.get(connectedUsersList.get(j))
                ));
            }
        }

        return Pair.of(adjacencyMatrixOfConnectedUsers, connectedUsersList);
    }

    private Set<String> connectedUserWithUser(
            Set<String> connectedUsers, List<String> users, DenseMatrix64F adjacencyMatrix, int userIndex
    ) {
        for (int i = 0; i < adjacencyMatrix.getNumCols(); i++) {
            String user = users.get(i);
            if (adjacencyMatrix.get(userIndex, i) == 1 && !connectedUsers.contains(user)) {
                connectedUsers.add(user);
                connectedUsers = connectedUserWithUser(connectedUsers, users, adjacencyMatrix, i);
            }
        }
        return connectedUsers;
    }

    private List<String> filterUsersWithRelations(List<? extends UserRelation> relations) {
        Set<String> users = new HashSet<>();
        for (UserRelation relation : relations) {
            users.add(relation.getClient1());
            users.add(relation.getClient2());
        }
        return new ArrayList<>(users);
    }

    private Pair<SimpleMatrix, List<String>> adjacencyMatrix(List<String> users, List<? extends UserRelation> relations) {
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

        return Pair.of(adjacencyMatrix, new ArrayList<>(userToIndex.keySet()));
    }

    private SimpleMatrix degreeMatrix(SimpleMatrix adjacencyMatrix) {
        SimpleMatrix degreeMatrix = new SimpleMatrix(
                adjacencyMatrix.getMatrix().getNumRows(), adjacencyMatrix.getMatrix().getNumCols()
        );
        for (int i = 0; i < adjacencyMatrix.getMatrix().getNumRows(); i++) {
            int degree = 0;
            for (int j = 0; j < adjacencyMatrix.getMatrix().getNumCols(); j++) {
                degree += adjacencyMatrix.get(i, j);
            }
            degreeMatrix.set(i, i, degree);
        }
        return degreeMatrix;
    }

    private Map<String, Integer> userToIndex(List<String> users) {
        Map<String, Integer> userToIndex = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            userToIndex.put(users.get(i), i);
        }
        return userToIndex;
    }
}
