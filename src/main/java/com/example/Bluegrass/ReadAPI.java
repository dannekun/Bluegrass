package com.example.Bluegrass;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Bojic
 * Date: 2022-04-29
 * Time: 09:41
 * Project: Bluegrass
 * Copyright: MIT
 */
public class ReadAPI {

    private String metObsAPI = "https://opendata-download-metobs.smhi.se/api";
    /**
     * Print all available parameters.
     *
     * @return The key for the last parameter.
     * @throws IOException
     * @throws JSONException
     */
     String getParameters() throws IOException, JSONException {

        JSONObject parameterObject = readJsonFromUrl(metObsAPI + "/version/latest.json");
        JSONArray parametersArray = parameterObject.getJSONArray("resource");

        String parameterKey = null;
        for (int i = 0; i < parametersArray.length(); i++) {

            JSONObject parameter = parametersArray.getJSONObject(i);
            parameterKey = parameter.getString("key");
            String parameterName = parameter.getString("title");
            System.out.println(parameterKey + ": " + parameterName);
        }

        return parameterKey;
    }


    /**
     * Print all available stations for the given parameter. Return the id for the last station.
     *
     * @param parameterKey The key for the wanted parameter
     * @return The id for the last station
     * @throws IOException
     * @throws JSONException
     */
     String getStationNames(String parameterKey) throws IOException, JSONException {

        JSONObject stationsObject = readJsonFromUrl(metObsAPI + "/version/latest/parameter/" + parameterKey + ".json");
        JSONArray stationsArray = stationsObject.getJSONArray("station");
        String stationId = null;
        for (int i = 0; i < stationsArray.length(); i++) {
            String stationName = stationsArray.getJSONObject(i).getString("name");
            stationId = stationsArray.getJSONObject(i).getString("key");
            System.out.println(stationId + ": " + stationName);
        }

        return stationId;
    }


    /**
     * Print all available periods for the given parameter and station. Return the key for the last period.
     *
     * @param parameterKey The key for the wanted parameter
     * @param stationKey The key for the wanted station
     * @return The name for the last period
     * @throws IOException
     * @throws JSONException
     */
     String getPeriodNames(String parameterKey, String stationKey) throws IOException, JSONException {

        JSONObject periodsObject = readJsonFromUrl(metObsAPI + "/version/latest/parameter/" + parameterKey + "/station/" + stationKey + ".json");
        JSONArray periodsArray = periodsObject.getJSONArray("period");

        String periodName = null;
        for (int i = 0; i < periodsArray.length(); i++) {
            periodName = periodsArray.getJSONObject(i).getString("key");
            System.out.println(periodName);
        }

        return periodName;
    }


    /**
     * Get the data for the given parameter, station and period.
     *
     * @param parameterKey The key for the wanted parameter
     * @param stationKey The key for the wanted station
     * @param periodName The name for the wanted period
     * @return The data
     * @throws IOException
     * @throws JSONException
     */
    private String getData(String parameterKey, String stationKey, String periodName) throws IOException {
        return readStringFromUrl(metObsAPI + "/version/latest/parameter/" + parameterKey + "/station/" + stationKey + "/period/" + periodName + "/data.csv");
    }


     JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }


    private String readStringFromUrl(String url) throws IOException {

        InputStream inputStream = new URL(url).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            return stringBuilder.toString();
        } finally {
            inputStream.close();
        }
    }

    void sparaObjekt(String station) throws IOException {
        Weather weather = new Weather();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        weather.setTimestamp(formatter.format(date));

        weather.setTemp(hittaObjekt("1", station));
        weather.setWind_direction(hittaObjekt("3", station));
        weather.setWind_speed(hittaObjekt("4", station));

        weather.setStation_id(station);
        weather.setStation_name(hittaStationNamn(station));

        weather.print();


    }


    String hittaObjekt(String parameter, String station) throws IOException {

        String sUrl = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/"+parameter+"/station/"+station+"/period/latest-hour/data.json";

        URL url = new URL(sUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) urlConnection.getContent()));
        JsonObject rootObt = root.getAsJsonObject();

        JsonArray jsonArray = rootObt.getAsJsonArray("value");

        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();


        return jsonObject.get("value").getAsString();

    }

    String hittaStationNamn(String station) throws IOException {

        String sUrl = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/1/station/"+station+"/period/latest-hour/data.json";

        URL url = new URL(sUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) urlConnection.getContent()));
        JsonObject rootObt = root.getAsJsonObject();

        JsonObject jsonArray = rootObt.getAsJsonObject("station");

        return jsonArray.get("name").getAsString();

    }


}
