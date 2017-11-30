package com.weissdennis.database;

import com.weissdennis.ai.Client;
import com.weissdennis.ts3socialai.Configuration;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DbUserInfoWriter implements Runnable {
   private Connection connection;
   private Statement statement;
   private Connection tsDbConnection;
   private Statement tsDbStatement;

   public DbUserInfoWriter(String tsDbUrl, String internDbUrl) {
      System.out.println(internDbUrl);
      try {
         this.connection = DriverManager.getConnection(internDbUrl, Configuration.mariaDBusername, Configuration.mariaDBpassword);
         this.statement = connection.createStatement();
         this.tsDbConnection = DriverManager.getConnection(tsDbUrl);
         this.tsDbStatement = tsDbConnection.createStatement();
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

   @Override
   public void run() {
      initializeDB();
      writeClientsIntoDB();
   }

   private void initializeDB() {
      try {
         statement.execute("CREATE DATABASE ts3_social_ai;");
      } catch (SQLException e) {

      }

      try {
         statement.execute("USE ts3_social_ai");
         System.out.println("successful");
      } catch (SQLException e) {
         System.out.println("failed");
      }

      try {
         statement.execute(
               "CREATE TABLE Users(ClientID INTEGER, " + "UniqueID VARCHAR(40) PRIMARY KEY, " +
                     "nick_name VARCHAR(100), " + "total_upload INTEGER, " + "total_download INTEGER, " +
                     "ip VARCHAR(15), " + "hostname VARCHAR(100), " + "city VARCHAR(100), " + "region VARCHAR(100), " +
                     "country VARCHAR(100), " + "longitude DECIMAL(8, 5), " + "latitude DECIMAL(8, 5), " + "postal VARCHAR(20), " +
                     "org VARCHAR(100));");
      } catch (SQLException e) {
         System.out.println("Table users exists already!");
      }
   }

   private void writeClientsIntoDB() {
      try {
         ResultSet resultSet = tsDbStatement.executeQuery(
               "SELECT client_id, client_unique_id, client_nickname, client_total_upload, client_total_download, " +
                     "client_lastip FROM clients WHERE server_id = " + Configuration.serverID + ";");

         while (resultSet.next()) {
            int clientID = resultSet.getInt(1);
            String uniqueID = resultSet.getString(2);
            String nickName = resultSet.getString(3);
            int totalUpload = resultSet.getInt(4);
            int totalDownload = resultSet.getInt(5);
            String ip = resultSet.getString(6);
            if (ip != null) {
               ClientIpInfo clientIpInfo;
               if (Configuration.ipToInfo.containsKey(ip)) {
                  clientIpInfo = Configuration.ipToInfo.get(ip);
                  System.out.println("used HashMap");
               } else {
                  clientIpInfo = LocationInfo.getClientIpInfo(ip);
                  Configuration.ipToInfo.put(ip, clientIpInfo);
                  System.out.println("used API data");
               }
               if (clientIpInfo.getHostname() == null) {
                  clientIpInfo.setHostname("");
               }
               if (clientIpInfo.getCity() == null) {
                  clientIpInfo.setCity("");
               }
               if (clientIpInfo.getRegion() == null) {
                  clientIpInfo.setRegion("");
               }
               if (clientIpInfo.getCountry() == null) {
                  clientIpInfo.setCountry("");
               }
               if (clientIpInfo.getLoc() == null) {
                  clientIpInfo.setLoc("");
               }
               if (clientIpInfo.getPostal() == null) {
                  clientIpInfo.setPostal("");
               }
               if (clientIpInfo.getOrg() == null) {
                  clientIpInfo.setOrg("");
               }
               Client client = new Client(clientID, uniqueID, nickName, totalUpload, totalDownload, ip,
                     clientIpInfo.getHostname(), clientIpInfo.getCity(), clientIpInfo.getRegion(),
                     clientIpInfo.getCountry(), clientIpInfo.getLoc(), clientIpInfo.getPostal(), clientIpInfo.getOrg());
               writeClientIntoDB(client);
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private void writeClientIntoDB(Client client) {
      List<String> locs = Arrays.asList(client.getLocation().split(","));
      Location location = new Location(Double.parseDouble(locs.get(0)), Double.parseDouble(locs.get(1)));

      try {
         statement.execute(
               "INSERT INTO Users(ClientID, UniqueID, nick_name, total_upload, total_download, ip, hostname, " +
                     "city, region, country, longitude, latitude, postal, org) VALUES(" + client.getId() + ", '" +
                     client.getUniqueID() + "', '" + client.getNickname() + "', " + client.getTotalUpload() + ", " +
                     client.getTotalDownload() + ", '" + client.getIp() + "', '" + client.getHostName() + "', '" +
                     client.getCity() + "', '" + client.getRegion() + "', '" + client.getCountry() + "', " +
                     location.getLongitude() + ", " + location.getLatitude() + ", '" + client.getPostalCode() + "', '" +
                     client.getAssociatedOrg() + "');");
      } catch (SQLException e) {
         try {
            /*System.out.println("UPDATE Users SET ClientID = " + client.getId() + ", UniqueID = '" + client.getUniqueID() +
                  "', nick_name = '" + client.getNickname() + "', total_upload = " + client.getTotalUpload() + ", total_download = " +
                  client.getTotalDownload() + ", '" + client.getIp() + "', hostname = '" + client.getHostName() + "', city = '" +
                  client.getCity() + "', region = '" + client.getRegion() + "', country = '" + client.getCountry() +
                  "', longitude = " + location.getLongitude() + ", latitude = " + location.getLatitude() + ", postal = '" +
                  client.getPostalCode() + "', org = '" + client.getAssociatedOrg() + "' WHERE UniqueID = '" + client.getUniqueID() +
                  "';");*/
            statement.execute("UPDATE Users SET ClientID = " + client.getId() + ", UniqueID = '" + client.getUniqueID() +
                  "', nick_name = '" + client.getNickname() + "', total_upload = " + client.getTotalUpload() + ", total_download = " +
                  client.getTotalDownload() + ", ip = '" + client.getIp() + "', hostname = '" + client.getHostName() + "', city = '" +
                  client.getCity() + "', region = '" + client.getRegion() + "', country = '" + client.getCountry() +
                  "', longitude = " + location.getLongitude() + ", latitude = " + location.getLatitude() + ", postal = '" +
                  client.getPostalCode() + "', org = '" + client.getAssociatedOrg() + "' WHERE UniqueID = '" + client.getUniqueID() +
                  "';"
            );
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
      }
   }
}
