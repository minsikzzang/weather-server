package com.ifactory.service.weatherserver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.ifactory.client.openweather.Client;
import com.ifactory.client.openweather.Result;

public class WeatherService {
  
  public WeatherService() {
    
  }
  
  public String getCurrentWeather(double lat, double lng) {
    Client c = new Client();    	
    Result result = null;
    
  	try {
		  result = c.coordinate(lat, lng).get();		  
	  } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  } catch (InterruptedException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  } catch (ExecutionException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
  	
    return new WeatherFormatter().json(result);
  }
}