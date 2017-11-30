package com.weissdennis.database;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.weissdennis.ts3socialai.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DbUsersInChannel implements Runnable {
   private TS3Api ts3Api;
   private Statement statement;

   public DbUsersInChannel(TS3Api ts3Api) {
      this.ts3Api = ts3Api;
      try {
         Connection connection = DriverManager
               .getConnection(Configuration.mariaDBLocation, Configuration.mariaDBusername, Configuration.mariaDBpassword);
         statement = connection.createStatement();
         statement.execute("USE ts3_social_ai");
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

   @Override
   public void run() {
      initializeDB();
      writeClientChannelIntoDatabase();
   }

   private void initializeDB() {
      System.out.println("CREATE TABLE user_in_channel(unique_id INTEGER REFERENCES Users(UniqueID), " + "channel_id INTEGER, " +
            "current_time TIMESTAMP, CONSTRAINT PK_UserInChannel PRIMARY KEY(unique_id, channel_id, current_time));");
      try {
         statement.execute(
               "CREATE TABLE user_in_channel(unique_id VARCHAR(50) REFERENCES Users(UniqueID), " + "channel_id INTEGER, " +
                     "date_time TIMESTAMP, CONSTRAINT PK_UserInChannel PRIMARY KEY(unique_id, channel_id, date_time));");
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private void writeClientChannelIntoDatabase() {
      List<Client> clients = ts3Api.getClients();
      LocalDateTime dateTime = LocalDateTime.now();
      for (Client client : clients) {
         if (!client.isAway() && !client.isServerQueryClient() && !com.weissdennis.ai.Channel.clientIsAfk(client)) {
            System.out.printf("%s: %d\n", client.getNickname(), client.getChannelId());
            try {
               DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               statement.execute(
                     "INSERT INTO user_in_channel(unique_id, channel_id, date_time) VALUES('" + client.getUniqueIdentifier() +
                           "', " + client.getChannelId() + ", '" + dateTimeFormatter.format(dateTime) + "');");
         } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }
}
