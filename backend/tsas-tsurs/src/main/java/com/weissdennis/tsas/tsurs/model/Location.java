package com.weissdennis.tsas.tsurs.model;

public class Location {

    public static double EARTH_POLAR_RADIUS = 6356.8;
    public static double EARTH_EQUATORIAL_RADIUS = 6371.0;

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distanceTo(Location otherLocation) {
        //TODO: improve precision
        return Math.sqrt(Math.pow((latitude - otherLocation.getLatitude()) * 0.5 * Math.PI * EARTH_POLAR_RADIUS / 90, 2)
                + Math.pow((longitude - otherLocation.longitude) * 2 * Math.PI * EARTH_EQUATORIAL_RADIUS /
                360 * Math.cos(degreeToRadian((longitude + otherLocation.longitude) / 2)), 2));
    }

    private static double degreeToRadian(double degree) {
        return degree / 180 * Math.PI;
    }
}
