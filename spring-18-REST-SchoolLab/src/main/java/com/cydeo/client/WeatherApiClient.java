package com.cydeo.client;

import com.cydeo.dto.weather.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://api.weatherstack.com", name = "WEATHER-CLIENT")
public interface WeatherApiClient {

    @GetMapping("/current")
    WeatherDTO getCurrentWeather(@RequestParam(value = "access_key") String key,
                                 @RequestParam(value = "query") String city);

}

// access key : 02a009b8e3922c395677a1e85406aca6
