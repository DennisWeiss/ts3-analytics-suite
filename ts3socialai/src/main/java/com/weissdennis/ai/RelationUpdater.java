package com.weissdennis.ai;

import com.weissdennis.application.Configuration;
import com.weissdennis.database.DbLoader;
import com.weissdennis.database.DbRelationWriter;

import java.sql.SQLException;
import java.util.List;

public class RelationUpdater implements Runnable {

    @Override
    public void run() {
        DbRelationWriter dbRelationWriter = new DbRelationWriter(Configuration.mariaDBLocation);
        try {
            DbLoader dbLoader = new DbLoader(Configuration.mariaDBLocation);
            List<User> users = dbLoader.load();
            for (int i = 0; i < users.size(); i++) {
                for (int j = 0; j < users.size(); j++) {
                    if (i != j) {
                        User.Relation userRelation = users.get(i).new Relation(users.get(j));
                        dbRelationWriter.writeRelationIntoDb(userRelation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
