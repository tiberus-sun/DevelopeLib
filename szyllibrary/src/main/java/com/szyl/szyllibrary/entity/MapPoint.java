package com.szyl.szyllibrary.entity;

//定义MapPoint类，存储点的经纬度坐标
public class MapPoint {

    public double Lon;
    public double Lat;

    public MapPoint(String Lon, String Lat) {

        double Lon_ = Double.parseDouble(Lon);
        double Lat_ = Double.parseDouble(Lat);
        this.Lon = Lon_;
        this.Lat = Lat_;
    }

    public MapPoint() {

    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }
}
