package com.example.Bluegrass.DAO;

import com.example.Bluegrass.Weather;
import com.google.gson.JsonObject;


public interface WeatherDAO {

    String findObject(String parameter);

    String findStationName();

    void saveObject();

    void start();

    void saveDataToDB(Weather weather);

    void saveXML(Weather weather);

    void printTxt(Weather weather);

    void saveXSD();

    JsonObject startConnection(String sUrl);

    void createParameter(String theName, String theValue);

    void createElement(String name, String value);
}
