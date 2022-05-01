package com.example.Bluegrass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@XmlRootElement(name = "WeatherData")
@XmlType(name = "", propOrder={"station_id", "station_name" , "timestamp", "temp", "wind_direction", "wind_speed" })
public class Weather implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String station_id;

    String station_name;

    String timestamp;

    String temp;

    String wind_direction;

    String wind_speed;

    @XmlSchemaType(name="integer")
    @XmlElement(name ="StationId")
    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    @XmlSchemaType(name="string")
    @XmlElement(name ="StationName")
    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    @XmlSchemaType(name="dateTime")
    @XmlElement(name ="Timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @XmlSchemaType(name="string")
    @XmlElement(name ="Temp")
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @XmlSchemaType(name="string")
    @XmlElement(name ="WindDirection")
    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    @XmlSchemaType(name="string")
    @XmlElement(name ="WindSpeed")
    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Weather() {
    }

    /**
     * Prints information from class.
     */
    public void print(){
        System.out.println("-------------------------------------------");
        System.out.println("Information");
        System.out.println("-------------------------------------------");
        System.out.println("StationID: "+getStation_id());
        System.out.println("StationName: "+getStation_name());
        System.out.println("Timestamp: "+getTimestamp());
        System.out.println("Temp: "+getTemp());
        System.out.println("WindDirection: "+getWind_direction());
        System.out.println("WindSpeed: "+getWind_speed());
        System.out.println("-------------------------------------------");
    }
}
