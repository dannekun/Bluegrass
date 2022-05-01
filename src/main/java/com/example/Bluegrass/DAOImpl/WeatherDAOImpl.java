package com.example.Bluegrass.DAOImpl;

import com.example.Bluegrass.DAO.WeatherDAO;
import com.example.Bluegrass.FileConfigData;
import com.example.Bluegrass.MySchemaOutputResolver;
import com.example.Bluegrass.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDAOImpl implements WeatherDAO {

    public WeatherDAOImpl(){

    }

    FileConfigData configData = new FileConfigData();
    Document document;
    Element reading;

    /**
     * Find value from given parameter.
     * @param parameter
     * @return
     */
    @Override
    public String findObject(String parameter)  {
        String sUrl = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/"+parameter+"/station/"+configData.getWEATHER_STATION()+"/period/latest-hour/data.json";
        JsonObject rootObt = startConnection(sUrl);
        JsonArray jsonArray = rootObt.getAsJsonArray("value");
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        return jsonObject.get("value").getAsString();
    }


    /**
     * Finds station name from station id.
     * @return
     */
    @Override
    public String findStationName()  {
        JsonObject rootObt = startConnection(configData.getURL());
        JsonObject jsonArray = rootObt.getAsJsonObject("station");
        return jsonArray.get("name").getAsString();
    }


    /**
     * Saves information to single class and generates to different files.
     */
    @Override
    public void saveObject(){
        Weather weather = new Weather();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        weather.setTimestamp(formatter.format(date));
        weather.setTemp(findObject("1"));
        weather.setWind_direction(findObject("3"));
        weather.setWind_speed(findObject("4"));
        weather.setStation_id(configData.getWEATHER_STATION());
        weather.setStation_name(findStationName());
        weather.print();
        saveDataToDB(weather);
        saveXML(weather);
        printTxt(weather);
        saveXSD();
    }

    /**
     * Starts the application.
     * Configured how frequent the application will run.
     */
    @Override
    public void start() {
        while (true){
            saveObject();
            configData.sleep();
        }
    }

    /**
     * Saves class information to database.
     * @param weather
     */
    @Override
    public void saveDataToDB(Weather weather)  {
        try {
            Class.forName("org.h2.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:h2:mem:testdb","sa","password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO WEATHER (station_id, station_name, timestamp, temp, wind_direction, wind_speed) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, weather.getStation_id());
            ps.setString(2, weather.getStation_name());
            ps.setString(3, weather.getTimestamp());
            ps.setString(4, weather.getTemp());
            ps.setString(5, weather.getWind_direction());
            ps.setString(6, weather.getWind_speed());
            ps.executeUpdate();
            System.out.println("Saved to DB successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves class to an XML file.
     * @param weather
     */
    @Override
    public void saveXML(Weather weather) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = null;
        try {
            dbuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = dbuilder.newDocument();

        Element weatherData = document.createElement("WeatherData");
        document.appendChild(weatherData);

        reading = document.createElement("Reading");
        weatherData.appendChild(reading);

        createElement("StationId", weather.getStation_id());
        createElement("StationName", weather.getStation_name());
        createElement("Timestamp", weather.getTimestamp());

        createParameter("Temp",weather.getTemp());
        createParameter("WindDirection", weather.getWind_direction());
        createParameter("WindSpeed", weather.getWind_speed());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(new File("./"+configData.getFileName()+".xml"));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("XML created successfully");
    }

    /**
     * Prints class information to an TXT file.
     * @param weather
     */
    @Override
    public void printTxt(Weather weather) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./"+ configData.getFileName()+".txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(weather.getTimestamp()+ "," + weather.getStation_name()+","+ weather.getTemp()+ "," + weather.getWind_direction() + ","+ weather.getWind_speed());
        writer.close();
        System.out.println("TXT created successfully");
    }

    /**
     * Saves class information to an XSD file.
     */
    @Override
    public void saveXSD() {
        try {
            JAXBContext jc = JAXBContext.newInstance(Weather.class);
            SchemaOutputResolver sor = new MySchemaOutputResolver();
            jc.generateSchema(sor);
            System.out.println("XSD created successfully");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start connection for API call.
     * @param sUrl
     * @return
     */
    @Override
    public JsonObject startConnection(String sUrl) {
        URL url = null;
        try {
            url = new URL(sUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) urlConnection.getContent()));
            return root.getAsJsonObject();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates parameter for XML file.
     * @param theName
     * @param theValue
     */
    @Override
    public void createParameter(String theName, String theValue) {
        Element parameter = document.createElement("Parameter");
        reading.appendChild(parameter);

        Element name = document.createElement("Name");
        name.appendChild(document.createTextNode(theName));
        parameter.appendChild(name);

        Element value = document.createElement("Value");
        value.appendChild(document.createTextNode(theValue));
        parameter.appendChild(value);
    }

    /**
     * Creates element for XML file.
     * @param name
     * @param value
     */
    @Override
    public void createElement(String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        reading.appendChild(element);
    }
}
