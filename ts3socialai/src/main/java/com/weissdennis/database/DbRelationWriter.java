package com.weissdennis.database;

import com.weissdennis.ai.User;
import com.weissdennis.application.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbRelationWriter {
    Connection connection;
    Statement statement;

    public DbRelationWriter(String internDbUrl) {
        try {
            this.connection = DriverManager.getConnection(internDbUrl, Configuration.mariaDBusername, Configuration.mariaDBpassword);
            this.statement = connection.createStatement();
            statement.execute("USE ts3_social_ai");
            statement.execute("CREATE TABLE relations(client1 VARCHAR(200) NOT NULL, client2 VARCHAR(200) NOT NULL, geo_relation DECIMAL(25,20), " +
                    "channel_relation DECIMAL(25,20), ip_relation DECIMAL(25,20), total_relation DECIMAL(25,20), CONSTRAINT " +
                    "PK_Relations PRIMARY KEY(client1, client2));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeRelationIntoDb(User.Relation userRelation) {
        String query = "INSERT INTO relations(client1, client2, geo_relation, channel_relation, ip_relation, total_relation) VALUES ('" +
                userRelation.getUniqueIdOfUser() + "', '" + userRelation.getOtherUser().getUniqueID() + "', " + userRelation.getGeoRelation() + ", " +
                userRelation.getChannelRelation() + ", " + userRelation.getIpMatch().getValue() + ", " + userRelation.getTotalRelation() + ");";
        try {
            statement.execute(query);
        } catch (SQLException e) {
            //System.out.println("entry exists already");

        } finally {
            String updateQuery =
                    "UPDATE relations SET geo_relation=" + userRelation.getGeoRelation() + ", channel_relation=" + userRelation.getChannelRelation() +
                            ", ip_relation=" + userRelation.getIpMatch().getValue() + ", total_relation=" + userRelation.getTotalRelation() +
                            " WHERE client1='" + userRelation.getUniqueIdOfUser() + "' AND client2='" + userRelation.getOtherUser().getUniqueID() +
                            "';";

            System.out.println(updateQuery);
            try {
                statement.execute(updateQuery);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
