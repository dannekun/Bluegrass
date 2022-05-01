package com.example.Bluegrass;

import com.example.Bluegrass.DAO.WeatherDAO;
import com.example.Bluegrass.DAOImpl.WeatherDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class BluegrassApplication {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		SpringApplication.run(BluegrassApplication.class, args);
		WeatherDAO weatherDAO = new WeatherDAOImpl();
		weatherDAO.start();
	}


}
