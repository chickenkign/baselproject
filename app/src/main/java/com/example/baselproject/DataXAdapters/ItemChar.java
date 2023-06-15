package com.example.baselproject.DataXAdapters;

public class ItemChar {
    String sensor ;
    String iv ;

    public ItemChar() {
    }
    public ItemChar(String sensor, String iv) {
        this.sensor = sensor;
        this.iv = iv;
    }
    public ItemChar(String iv) {
        this.iv = iv;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    @Override
    public String toString() {
        return "ItemChar{" +
                "sensor='" + sensor + '\'' +
                ", iv='" + iv + '\'' +
                '}';
    }
}
