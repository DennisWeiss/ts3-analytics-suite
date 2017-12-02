package com.weissdennis.application;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.weissdennis.database.DbUsersInChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServerQuery {
   private String queryName;
   private TS3Config ts3Config;
   private TS3Query ts3Query;
   private TS3Api ts3Api;

   public ServerQuery(String queryName) {
      this.queryName = queryName;
      ts3Config = new TS3Config();
      ts3Config.setHost(Configuration.serverIP);
      ts3Query = new TS3Query(ts3Config);
      ts3Query.connect();
      ts3Api = ts3Query.getApi();
      ts3Api.login(Configuration.adminName, Configuration.password);
      ts3Api.selectVirtualServerById(Configuration.serverID);
      ts3Api.setNickname(queryName);
   }

   public void login() {
      ts3Api.sendChannelMessage("Test");
   }

   public void getUserData() {
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      ScheduledFuture scheduledFuture =
            scheduler.scheduleAtFixedRate(new DbUsersInChannel(ts3Api), 3, 10, TimeUnit.SECONDS);
   }
}
