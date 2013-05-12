package com.ifactory.service.weatherserver;

import com.ifactory.client.greenwich.Result;

public class WeatherFormatter {
  public String json(Result result) {
    return "{\"name\":" + "\"" + result.getName() + "\",\"weatherId\":" +
      result.getWeathers().get(0).getId() + 
	    ",\"desc\":" + "\"" + result.getWeathers().get(0).getDescription() + 
	    "\",\"temp\":" + result.getMain().getTemp() + ",\"high\":" +  
	    result.getMain().getTempMax() + ",\"low\":" +  
	    result.getMain().getTempMin() + "}";
  }
}