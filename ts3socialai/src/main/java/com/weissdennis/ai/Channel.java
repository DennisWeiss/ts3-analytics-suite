package com.weissdennis.ai;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import java.util.ArrayList;
import java.util.List;

public class Channel {
   public static List<Integer> afkChannels = new ArrayList<>();

   public static void addChannelToAfk(int channelID) {
      afkChannels.add(channelID);
   }

   public static boolean clientIsAfk(Client client) {
      return afkChannels.contains(client.getChannelId());
   }
}
