package com.weissdennis.database;

import com.weissdennis.ai.User;
import com.weissdennis.ts3socialai.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DbRelationLoaderTest {
    @BeforeAll
    static void prepareConfig() {
        Configuration.loadConfig("C:/Users/weiss/IdeaProjects/ts3socialai7/config.cfg");
    }

    @Test
    void usersInSameChannel() {
        try {
            DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
            User user1 = dbLoader.loadUser("Dennis").get(0);
            User user2 = dbLoader.loadUser("Hallencosy").get(0);
            DbRelationLoader  dbRelationLoader = new DbRelationLoader();
            long channelRelation = dbRelationLoader.usersInSameChannel(user1, user2);
            assertEquals(22, channelRelation);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException");
        }
    }
}