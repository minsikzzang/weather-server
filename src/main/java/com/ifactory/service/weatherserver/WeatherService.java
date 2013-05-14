/**
 * Copyright 2013 iFactory Ltd.
 * 
 * Min Kim (minsikzzang@gmail.com)
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
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
    
    String response = new ForecastFormatter(true).json(forecast);
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
    String response = new ForecastFormatter(false).json(forecast);
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
  
	public void onWeather(Result weather, Connection conn) {
    // System.out.println(weather.getName() + ": " + weather.getMain().getTemp() + 
		// 	" C - " + weather.getWeathers().get(0).getDescription() + 
		// 	", High:" + weather.getMain().getTempMax() + ", Low: " + 
		// 	weather.getMain().getTempMin());
		
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
  
  public void getDailyForecast(double lat, double lng, int count) {
    Client c = new Client();   
    c.coordinate(lat, lng).count(count).forecast(false).get(this);
  }
  
  public void getHourlyForecast(double lat, double lng, int count) {
    Client c = new Client();   
    c.coordinate(lat, lng).count(count).forecast(true).get(this);
  }
  
  public void getCurrentWeather(double lat, double lng) {
    Client c = new Client();   
    c.coordinate(lat, lng).weather().get(this);
  }
}