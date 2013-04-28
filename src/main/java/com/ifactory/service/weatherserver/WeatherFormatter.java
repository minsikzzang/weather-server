package com.ifactory.service.weatherserver;

import com.ifactory.client.openweather.Result;

public class WeatherFormatter {
  public String json(Result result) {
    return "{\"name\":" + "\"" + result.getName() + 
	    "\",\"desc\":" + "\"" + result.getWeathers().get(0).getDescription() + 
	    "\",\"temp\":" + result.getTemp() + ",\"high\":" +  
	    result.getTempMax() + ",\"low\":" +  
	    result.getTempMin() + "}";
  }
}