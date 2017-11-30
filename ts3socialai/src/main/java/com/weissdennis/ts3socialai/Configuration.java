package com.weissdennis.ts3socialai;

import com.weissdennis.database.ClientIpInfo;

import java.util.HashMap;
import java.util.Map;

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
}
