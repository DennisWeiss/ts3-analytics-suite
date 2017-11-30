package com.weissdennis.database;

import com.weissdennis.ts3socialai.Configuration;
import com.weissdennis.ts3socialai.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbLoader {
   private Statement statement;

   public DbLoader(String internDbUrl) throws SQLException {
      Connection connection = DriverManager.getConnection(internDbUrl, Configuration.mariaDBusername, Configuration.mariaDBpassword);
      statement = connection.createStatement();
      statement.execute("USE ts3_social_ai;");
   }

   public List<User> load() throws SQLException {
      //statement.execute("USE DATABASE ts3_social_ai;");
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
      return processResultSet(resultSet);
   }

   public List<User> loadUser(String nickname) throws SQLException {
      //statement.execute("USE DATABASE ts3_social_ai;");
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE nick_name = '" + nickname + "';");
      System.out.println(resultSet.getFetchSize());
      return processResultSet(resultSet);
   }

   public User loadUserById(String uniqueId) throws SQLException {
      System.out.println("Searching for " + encoded(uniqueId));
      //statement.execute("USE DATABASE ts3_social_ai;");
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE UniqueID = '" + encoded(uniqueId) + "';");
      resultSet.next();
      return processSingleResultSet(resultSet);
   }

   private User processSingleResultSet(ResultSet resultSet) throws SQLException {
      int clientID = resultSet.getInt(1);
      String uniqueID = resultSet.getString(2);
      String nickName = resultSet.getString(3);
      int totalUpload = resultSet.getInt(4);
      int totalDownload = resultSet.getInt(5);
      String ip = resultSet.getString(6);
      String hostname = resultSet.getString(7);
      String city = resultSet.getString(8);
      String region = resultSet.getString(9);
      String country = resultSet.getString(10);
      double longitude = resultSet.getDouble(11);
      double latitude = resultSet.getDouble(12);
      String postal = resultSet.getString(13);
      String org = resultSet.getString(14);
      return new User(clientID, uniqueID, nickName, totalUpload, totalDownload, ip, hostname, city, region, country,
            new Location(longitude, latitude), postal, org);
   }

   private List<User> processResultSet(ResultSet resultSet) throws SQLException {
      List<User> users = new ArrayList<>();
      while (resultSet.next()) {
         users.add(processSingleResultSet(resultSet));
      }
      return users;
   }

   private String encoded(String parameterValue) {
      char[] pVarr = new char[parameterValue.length()];
      for (int i = 0; i < parameterValue.length(); i++) {
         pVarr[i] = parameterValue.charAt(i);
         if (pVarr[i] == ' ') {
            pVarr[i] = '+';
         }
      }
      return String.valueOf(pVarr);
   }
}
