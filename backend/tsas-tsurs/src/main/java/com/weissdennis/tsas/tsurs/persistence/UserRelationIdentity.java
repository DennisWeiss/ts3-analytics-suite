package com.weissdennis.tsas.tsurs.persistence;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserRelationIdentity implements Serializable {

    private String client1;
    private String client2;

    public UserRelationIdentity() {
    }

    public UserRelationIdentity(String client1, String client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    public String getClient1() {
        return client1;
    }

    public void setClient1(String client1) {
        this.client1 = client1;
    }

    public String getClient2() {
        return client2;
    }

    public void setClient2(String client2) {
        this.client2 = client2;
    }
}
