package com.weissdennis.database;

import com.weissdennis.ai.User;
import com.weissdennis.ts3socialai.Configuration;

import java.sql.*;

public class DbRelationLoader {
    private Connection connection;
    private Statement statement;

    public DbRelationLoader() {
        try {
            connection = DriverManager.getConnection(Configuration.mariaDBLocation, Configuration.mariaDBusername,
                    Configuration.mariaDBpassword);
            statement = connection.createStatement();
            statement.execute("USE ts3_social_ai;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int usersInSameChannel(User user1, User user2) {
        String query = "SELECT COUNT(*) AS amount " + "FROM user_in_channel " +
                "JOIN user_in_channel AS channels2 ON channels2.date_time = user_in_channel.date_time " +
                "AND channels2.channel_id = user_in_channel.channel_id " +
                "AND NOT user_in_channel.unique_id = channels2.unique_id " + "WHERE user_in_channel.unique_id = '" +
                user1.getUniqueID() + "' " + "AND channels2.unique_id = '" + user2.getUniqueID() + "' " +
                "GROUP BY user_in_channel.unique_id, channels2.unique_id " + "ORDER BY amount DESC;";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int totalUsersInSameChannel(User user) {
        String query = "SELECT SUM(COUNT(*)) OVER() AS amount " +
                "FROM user_in_channel " +
                "JOIN user_in_channel AS channels2 ON channels2.date_time = user_in_channel.date_time " +
                "AND channels2.channel_id = user_in_channel.channel_id " +
                "AND NOT user_in_channel.unique_id = channels2.unique_id " +
                "WHERE user_in_channel.unique_id = '" + user.getUniqueID() + "' " +
                "GROUP BY user_in_channel.unique_id, channels2.unique_id " +
                "LIMIT 1;";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
