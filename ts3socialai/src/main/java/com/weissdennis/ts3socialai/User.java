package com.weissdennis.ts3socialai;

import com.weissdennis.database.Location;

public class User {
   private int id;
   private String uniqueID;
   private String nickname;
   private int totalUpload;
   private int totalDownload;
   private String ip;
   private String hostName;
   private String city;
   private String region;
   private String country;
   private Location location;
   private String postalCode;
   private String associatedOrg;

   public User(int id, String uniqueID, String nickname, int totalUpload, int totalDownload, String ip, String hostName,
         String city, String region, String country, Location location, String postalCode, String associatedOrg) {
      this.id = id;
      this.uniqueID = uniqueID;
      this.nickname = nickname;
      this.totalUpload = totalUpload;
      this.totalDownload = totalDownload;
      this.ip = ip;
      this.hostName = hostName;
      this.city = city;
      this.region = region;
      this.country = country;
      this.location = location;
      this.postalCode = postalCode;
      this.associatedOrg = associatedOrg;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUniqueID() {
      return uniqueID;
   }

   public void setUniqueID(String uniqueID) {
      this.uniqueID = uniqueID;
   }

   public String getNickname() {
      return nickname;
   }

   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   public int getTotalUpload() {
      return totalUpload;
   }

   public void setTotalUpload(int totalUpload) {
      this.totalUpload = totalUpload;
   }

   public int getTotalDownload() {
      return totalDownload;
   }

   public void setTotalDownload(int totalDownload) {
      this.totalDownload = totalDownload;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getHostName() {
      return hostName;
   }

   public void setHostName(String hostName) {
      this.hostName = hostName;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getRegion() {
      return region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public String getPostalCode() {
      return postalCode;
   }

   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   public String getAssociatedOrg() {
      return associatedOrg;
   }

   public void setAssociatedOrg(String associatedOrg) {
      this.associatedOrg = associatedOrg;
   }
}
