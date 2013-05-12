package com.ifactory.service.weatherserver;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/weather"}, asyncSupported = true)
public class WeatherServlet extends HttpServlet {
  static final String ATTRIBUTE_STATUS = "status";
  static final String ATTRIBUTE_LAT = "lat";
	static final String ATTRIBUTE_LNG = "lng";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	    throws IOException {
	  resp.setContentType("application/json");
    final AsyncContext aCtx = req.startAsync(req, resp);
    aCtx.start(new GetWeatherService(aCtx));
	}
	
	private class GetWeatherService implements Runnable {
	  AsyncContext aCtx;

    public GetWeatherService(AsyncContext aCtx) {
      this.aCtx = aCtx;
    }

    @Override
    public void run() {
      HttpServletRequest req = (HttpServletRequest)aCtx.getRequest();
      double lat = Double.parseDouble(req.getParameter(ATTRIBUTE_LAT));
      double lng = Double.parseDouble(req.getParameter(ATTRIBUTE_LNG));
      
      WeatherService service = new WeatherService(aCtx);            
      service.getCurrentWeather(lat, lng);      	        
    }	  
	}
}
