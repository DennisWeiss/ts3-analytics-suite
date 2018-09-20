package com.weissdennis.database;

public class Location {
    private double latitude;
    private double longitude;

    private static double earthPolarRadius = 6356.8;
    private static double earthEquatorialRadius = 6371.0;

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
        double distance = Math.sqrt(Math.pow((latitude - otherLocation.getLatitude()) * 0.5 * Math.PI * earthPolarRadius / 90, 2) + Math.pow(
                (longitude - otherLocation.longitude) * 2 * Math.PI * earthEquatorialRadius / 360 *
                        Math.cos(degreeToRadian((longitude + otherLocation.longitude) / 2)), 2));

        return distance;
    }

    private static double degreeToRadian(double degree) {
        return degree / 180 * Math.PI;
    }
}