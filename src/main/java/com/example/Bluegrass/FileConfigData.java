package com.example.Bluegrass;

import java.util.concurrent.TimeUnit;

public class FileConfigData {

    String WEATHER_STATION = "97400";
    String URL = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/1/station/"+getWEATHER_STATION()+"/period/latest-hour/data.json";
    String fileName = "Weather";
    int second = 20;
    int minute;
    int hour;
    int day;
    int week;

    /**
     * Calculates how frequent the application should run.
     */
    public void sleep(){
        try {

        if (getSecond() > 0){
                TimeUnit.SECONDS.sleep(getSecond());
        }
        if (getMinute() > 0){
                TimeUnit.SECONDS.sleep(getMinute());
        }
        if (getHour() > 0){
                TimeUnit.HOURS.sleep(getHour());
        }
        if (getDay() > 0){
                TimeUnit.DAYS.sleep(getDay());
        }
        if (getWeek() > 0){
            int weekInDays = getWeek()*7;
                TimeUnit.DAYS.sleep(weekInDays);
        }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getWEATHER_STATION() {
        return WEATHER_STATION;
    }

    public void setWEATHER_STATION(String WEATHER_STATION) {
        this.WEATHER_STATION = WEATHER_STATION;
    }
}
