package com.ifactory.service.weatherserver;

import com.ifactory.client.openweather.Client;
import com.ifactory.client.openweather.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    Client c = new Client();    	
    List<Result> results = null;
    
  	try {
		  results = c.coordinate(lat, lng).count(1).get();		  
		/*
		  System.out.println(result.getName() + ": " + result.getTemp() + 
			" C - " + result.getWeathers().get(0).getDescription() + 
			", High:" + result.getTempMax() + ", Low: " + 
			result.getTempMin());
			*/
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
	  
	  if (results != null) {
	    Result result = results.get(0);
  	  PrintWriter out = resp.getWriter();	  
  	  out.print("{\"name\":" + "\"" + result.getName() + 
  	    "\",\"description\":" + "\"" + result.getWeathers().get(0).getDescription() + 
  	    "\",\"temp\":" + result.getTemp() + ",\"high\":" +  
  	    result.getTempMax() + ",\"low\":" +  
  	    result.getTempMin() + "}");  
  	  resp.setStatus(HttpServletResponse.SC_OK);
	  } else {
	    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);	     
	  }
	}
}
