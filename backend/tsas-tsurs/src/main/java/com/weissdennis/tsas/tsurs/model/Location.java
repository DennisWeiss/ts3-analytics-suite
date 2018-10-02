package com.weissdennis.tsas.tsurs.model;

public class Location {

    public static double EARTH_POLAR_RADIUS = 6356.8;
    public static double EARTH_EQUATORIAL_RADIUS = 6371.0;

    private Double latitude;
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double distanceTo(Location otherLocation) {
        //TODO: improve precision
        return Math.sqrt(Math.pow((latitude - otherLocation.getLatitude()) * 0.5 * Math.PI * EARTH_POLAR_RADIUS / 90, 2)
                + Math.pow((longitude - otherLocation.longitude) * 2 * Math.PI * EARTH_EQUATORIAL_RADIUS /
                360 * Math.cos(degreeToRadian((longitude + otherLocation.longitude) / 2)), 2));
    }

    private static double degreeToRadian(double degree) {
        return degree / 180 * Math.PI;
    }
}
