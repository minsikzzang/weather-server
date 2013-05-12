package com.ifactory.service.weatherserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import com.ifactory.client.greenwich.Client;
import com.ifactory.client.greenwich.Connection;
import com.ifactory.client.greenwich.DailyForecast;
import com.ifactory.client.greenwich.ForecastResult;
import com.ifactory.client.greenwich.IWeatherListener;
import com.ifactory.client.greenwich.Result;
import com.ifactory.client.greenwich.HourlyForecast;

public class WeatherService implements IWeatherListener {
  
  private AsyncContext aCtx;
  
  public WeatherService(AsyncContext aCtx) {
    this.aCtx = aCtx;
  }
  
  public void onDailyForecast(ForecastResult forecast, Connection conn) {
    System.out.println(forecast.getName() + ", count: " + 
      forecast.getDaily().size());
    for (DailyForecast f: forecast.getDaily()) {
      /*
      SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
      System.out.println(sdf.format(new Date(f.getTimestamp() * 1000)) + 
        " - day:" + f.getTemp().getDay() + ", High:" + f.getTemp().getMax() + 
        ", Low:" + f.getTemp().getMin());
        */
    }
  }

  public void onHourlyForecast(ForecastResult forecast, Connection conn) {
    System.out.println(forecast.getName() + ", count: " + 
      forecast.getHourly().size());      
    for (HourlyForecast f: forecast.getHourly()) {
      /*
      SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
      System.out.println(sdf.format(new Date(f.getTimestamp() * 1000)) + 
        " - day:" + f.getMain().getTemp() + ", High:" + f.getMain().getTempMax() + 
        ", Low:" + f.getMain().getTempMin());
        */
    }    
  }
  
	public void onWeather(Result weather, Connection conn) {
    System.out.println(weather.getName() + ": " + weather.getMain().getTemp() + 
			" C - " + weather.getWeathers().get(0).getDescription() + 
			", High:" + weather.getMain().getTempMax() + ", Low: " + 
			weather.getMain().getTempMin());
		
		String response = new WeatherFormatter().json(weather);	
		HttpServletResponse resp = (HttpServletResponse)aCtx.getResponse();
		if (response != null) {
		  try {
		    resp.getWriter().print(response);  	    
		    resp.setStatus(HttpServletResponse.SC_OK);
		  }	catch (IOException e) {
		    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);	     
		  }	  
  	} else {
  	  resp.setStatus(HttpServletResponse.SC_NOT_FOUND);	     
  	}
  	  
		conn.close();
		this.aCtx.complete();
	}
	
	public void onError() {
	  this.aCtx.complete();
	}
  
  public void getDailyForecast(double lat, double lng) {
  
  }
  
  public void getHourlyForecast(double lat, double lng) {

  }
  
  public void getCurrentWeather(double lat, double lng) {
    Client c = new Client();   
    c.coordinate(lat, lng).weather().get(this);      
  }
}