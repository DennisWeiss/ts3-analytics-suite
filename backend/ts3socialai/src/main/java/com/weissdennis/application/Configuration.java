package com.weissdennis.application;

import com.weissdennis.ai.Channel;
import com.weissdennis.database.ClientIpInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Configuration {
   public static String serverIP;
   public static String adminName;
   public static String password;
   public static int serverID = 1;
   public static String dbLocation = "jdbc:sqlite:ts3server.sqlitedb";
   public static String mariaDBLocation = "jdbc:mysql://localhost:3306";
   public static String mariaDBusername = "root";
   public static String mariaDBpassword = "";

   public static Map<String, ClientIpInfo> ipToInfo = new HashMap<>();

   public static void loadConfig(String configLocation) {
      Properties properties = new Properties();
      try {
         InputStream inputStream = new FileInputStream(configLocation);
         properties.load(inputStream);
         Configuration.serverIP = properties.getProperty("ts3-server-address");
         Configuration.adminName = properties.getProperty("ts3-server-query-name");
         Configuration.password = properties.getProperty("ts3-server-query-password");
         Configuration.serverID = Integer.parseInt(properties.getProperty("ts3-server-id"));
         Configuration.dbLocation = "jdbc:sqlite:" + properties.getProperty("ts3-server-db-location");
         if (properties.getProperty("mariaDB-location") != null && !properties.getProperty("mariaDB-location").equals("")) {
            Configuration.mariaDBLocation = "jdbc:mysql://" + properties.getProperty("mariaDB-location");
         }
         if (properties.getProperty("mariaDB-username") != null && !properties.getProperty("mariaDB-username").equals("")) {
            Configuration.mariaDBusername = properties.getProperty("mariaDB-username");
         }
         if (properties.getProperty("mariaDB-password") != null && !properties.getProperty("mariaDB-password").equals("")) {
            Configuration.mariaDBpassword = properties.getProperty("mariaDB-password");
         }
         if (properties.getProperty("ts3-afk-channels") != null && !properties.getProperty("ts3-afk-channels").equals("")) {
            String channelsStr = properties.getProperty("ts3-afk-channels");
            List<String> afkChannelsStr = Arrays.asList(channelsStr.split("\\s*,\\s*"));
            for (String channel : afkChannelsStr) {
               Channel.addChannelToAfk(Integer.parseInt(channel));
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("Config file was not found!");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
