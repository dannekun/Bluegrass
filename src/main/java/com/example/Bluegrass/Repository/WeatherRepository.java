package com.example.Bluegrass.Repository;

import com.example.Bluegrass.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Daniel Bojic
 * Date: 2022-04-29
 * Time: 13:41
 * Project: Bluegrass
 * Copyright: MIT
 */


public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
