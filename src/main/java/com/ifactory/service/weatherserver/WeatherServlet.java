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

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/weather"}, asyncSupported = true)
public class WeatherServlet extends HttpServlet {
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
