package com.example.Bluegrass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.SQLOutput;

/**
 * Created by Daniel Bojic
 * Date: 2022-04-29
 * Time: 11:31
 * Project: Bluegrass
 * Copyright: MIT
 */
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String station_id;
    String station_name;
    String timestamp;
    String temp;
    String wind_direction;
    String wind_speed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }


    void print(){
        System.out.println(getStation_id());
        System.out.println(getStation_name());
        System.out.println(getTimestamp());
        System.out.println(getTemp());
        System.out.println(getWind_direction());
        System.out.println(getWind_speed());
    }
}
