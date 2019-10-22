package com.weissdennis.tsas.tsups.model;

import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Eigenpair;

import java.util.ArrayList;
import java.util.List;

public class TS3UserGraphLaplacianEigenDecomposition {

    private List<String> users = new ArrayList<>();
    private List<Eigenpair> eigenPairs = new ArrayList<>();

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Eigenpair> getEigenPairs() {
        return eigenPairs;
    }

    public void setEigenPairs(List<Eigenpair> eigenPairs) {
        this.eigenPairs = eigenPairs;
    }
}
