package com.weissdennis.tsas.tsups.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserRelationIdentity implements Serializable {

    @Column(nullable = false)
    private String client1;

    @Column(nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRelationIdentity that = (UserRelationIdentity) o;

        if (client1 != null ? !client1.equals(that.client1) : that.client1 != null) return false;
        return client2 != null ? client2.equals(that.client2) : that.client2 == null;
    }

    @Override
    public int hashCode() {
        int result = client1 != null ? client1.hashCode() : 0;
        result = 31 * result + (client2 != null ? client2.hashCode() : 0);
        return result;
    }
}
