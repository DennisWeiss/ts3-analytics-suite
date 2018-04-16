package com.weissdennis.database;

import com.weissdennis.application.Configuration;
import com.weissdennis.ai.User;

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
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
      return processResultSet(resultSet);
   }

   public List<User> loadUser(String nickname) throws SQLException {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE nick_name = '" + nickname + "';");
      System.out.println(resultSet.getFetchSize());
      return processResultSet(resultSet);
   }

   public User loadUserById(String uniqueId) throws SQLException {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE UniqueID = '" + encoded(uniqueId) + "';");
      resultSet.next();
      return processSingleResultSet(resultSet);
   }

   public List<RelationWrapper> loadRelations() throws SQLException {
      ResultSet resultSet = statement.executeQuery("SELECT relations.client1, relations.client2, relations.geo_relation, " +
              "(relations.channel_relation+relations2.channel_relation)/2 AS channel_relation, relations.ip_relation," +
              "(relations.total_relation+relations2.total_relation)/2 AS total_relation\n" +
              "FROM relations \n" +
              "JOIN relations AS relations2 ON relations.client2 = relations2.client1 AND relations.client1 = relations2.client2\n" +
              "WHERE relations.channel_relation > 0.01 AND relations2.channel_relation > 0.01\n" +
              "ORDER BY total_relation DESC;");
      return processRelations(resultSet);
   }

   public List<RelationWrapper> loadRelationOfUser(String user) throws SQLException {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM relations WHERE client1='" + user + "' " +
              "AND channel_relation > 0.01 ORDER BY total_relation DESC;");
      return processRelations(resultSet);
   }

   private List<RelationWrapper> processRelations(ResultSet resultSet) throws SQLException {
      List<RelationWrapper> relations = new ArrayList<>();
      while (resultSet.next()) {
         String user = resultSet.getString(1);
         String otherUser = resultSet.getString(2);
         double geoRelation = resultSet.getDouble(3);
         double channelRelation = resultSet.getDouble(4);
         double ipRelation = resultSet.getDouble(5);
         double totalRelation = resultSet.getDouble(6);
         relations.add(new RelationWrapper(user, otherUser, geoRelation, channelRelation, ipRelation, totalRelation));
      }
      return relations;
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
      double latitude = resultSet.getDouble(11);
      double longitude = resultSet.getDouble(12);
      String postal = resultSet.getString(13);
      String org = resultSet.getString(14);

      return new User(clientID, uniqueID, nickName, totalUpload, totalDownload, ip, hostname, city, region, country,
            new Location(latitude, longitude), postal, org);
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
