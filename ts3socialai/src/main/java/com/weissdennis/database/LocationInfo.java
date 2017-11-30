package com.weissdennis.database;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LocationInfo {
   static ClientIpInfo getClientIpInfo(String ip) {
      try {
         //Reader reader = new InputStreamReader(LocationInfo.class.getResourceAsStream("http://ipinfo.io/" + ip + "/json"), "UTF-8");
         URL url = new URL("http://ipinfo.io/" + ip + "/json");
         BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
         Gson gson = new GsonBuilder().create();
         return gson.fromJson(reader, ClientIpInfo.class);
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (MalformedURLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }
}