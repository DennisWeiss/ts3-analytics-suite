package com.weissdennis.database;

public class ClientIpInfo {
   private String ip;
   private String hostname;
   private String city;
   private String region;
   private String country;
   private String loc;
   private String org;
   private String postal;

   public ClientIpInfo(String ip, String hostname, String city, String region, String country, String loc, String org,
         String postal) {
      this.ip = ip;
      this.hostname = hostname;
      this.city = city;
      this.region = region;
      this.country = country;
      this.loc = loc;
      this.org = org;
      this.postal = postal;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getHostname() {
      return hostname;
   }

   public void setHostname(String hostname) {
      this.hostname = hostname;
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

   public String getLoc() {
      return loc;
   }

   public void setLoc(String loc) {
      this.loc = loc;
   }

   public String getOrg() {
      return org;
   }

   public void setOrg(String org) {
      this.org = org;
   }

   public String getPostal() {
      return postal;
   }

   public void setPostal(String postal) {
      this.postal = postal;
   }
}

