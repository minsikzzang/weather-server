package com.ifactory.service.weatherserver;

import java.io.IOException;
import java.io.PrintWriter;

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
    
	  PrintWriter out = resp.getWriter();	  
	  out.print("{\"lat\":" + lat + "}");
	  resp.setStatus(HttpServletResponse.SC_OK);
	}
}
