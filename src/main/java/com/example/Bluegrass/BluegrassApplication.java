package com.example.Bluegrass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BluegrassApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BluegrassApplication.class, args);

		ReadAPI api = new ReadAPI();

		//api.getParameters();
		//api.getStationNames("1");
		//api.hittaObjekt();

		api.sparaObjekt("97400");
		//JAG BEHÖVER HITTA FRÅN PARAMETER 1 (temperatur), 3 (direction), 4 (hastighet).

		//JAG SKA KUNNA VÄLJA STATION. SKRIV UT LISTA OCH MAN FÅR SKRIVA IN SJÄLV.

		//JAG BEHÖVER SPARA NER station_id (station -> key), station_name (station -> name), timestamp (new Date), temp (1), direction (3), speed (4).

		//LÄGG TILL HUR OFTA PROGRAMMET SKA GÖRA ANROP

		//SPARA ALLT SOM EN XML

		//KONVERTERA XML TILL FLAT FIL

		//SPARA XML DATA TILL EN DATABAS


		String url = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/4/station/97400/period/latest-hour/data.json";
	}


}
