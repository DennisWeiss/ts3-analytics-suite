package com.weissdennis.ai;

import com.weissdennis.database.DbLoader;
import com.weissdennis.application.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RelationTest {
    @BeforeAll
    static void prepareConfig() {
        Configuration.loadConfig("C:/Users/weiss/IdeaProjects/ts3socialai7/config.cfg");
    }

    @Test
    void getChannelRelation() {
        try {
            DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
            User user1 = dbLoader.loadUser("Dennis").get(0);
            User user2 = dbLoader.loadUser("Hallencosy").get(0);
            User.Relation relation = user1.new Relation(user2);
            assertEquals(0.2135922330, relation.getChannelRelation(), 0.001);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}