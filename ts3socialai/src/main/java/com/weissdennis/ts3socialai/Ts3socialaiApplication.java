package com.weissdennis.ts3socialai;

import com.weissdennis.ai.Channel;
import com.weissdennis.database.DbUserInfoWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Ts3socialaiApplication {
   static String configLocation = "C:/Users/weiss/IdeaProjects/ts3socialai/ts3socialai/config.cfg";
   static String queryName = "TS3SocialAI";

   public static void main(String[] args) {
      SpringApplication.run(Ts3socialaiApplication.class, args);
      loadConfig(configLocation);
      writeUserInfoIntoDB();
      ServerQuery serverQuery = new ServerQuery(queryName);
      serverQuery.login();
      serverQuery.getUserData();
   }

   private static void loadConfig(String configLocation) {
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

   private static void writeUserInfoIntoDB() {
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      ScheduledFuture scheduledFuture = scheduler
            .scheduleAtFixedRate(new DbUserInfoWriter(Configuration.dbLocation, Configuration.mariaDBLocation), 0,
                  60, TimeUnit.SECONDS);
   }
}
