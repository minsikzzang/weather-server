package com.ifactory.service.weatherserver;

import com.ifactory.client.openweather.Client;
import com.ifactory.client.openweather.Result;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WeatherServlet extends HttpServlet {
  static final String ATTRIBUTE_STATUS = "status";
  static final String ATTRIBUTE_LAT = "lat";
	static final String ATTRIBUTE_LNG = "lng";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	    throws IOException {
    resp.setContentType("application/json");
    double lat = Double.parseDouble(req.getParameter(ATTRIBUTE_LAT));
    double lng = Double.parseDouble(req.getParameter(ATTRIBUTE_LNG));

	  WeatherService service = new WeatherService();
	  String response = service.getCurrentWeather(lat, lng);
	  if (response != null) {
	    PrintWriter out = resp.getWriter();	  
  	  out.print(response);  
  	  resp.setStatus(HttpServletResponse.SC_OK);
	  } else {
	    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);	     
	  }
	}
}
