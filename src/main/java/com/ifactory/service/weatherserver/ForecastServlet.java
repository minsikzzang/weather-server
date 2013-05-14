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
import java.lang.NumberFormatException;

@WebServlet(urlPatterns = {"/forecast"}, asyncSupported = true)             
public class ForecastServlet extends HttpServlet {
  static final String ATTRIBUTE_LAT = "lat";
	static final String ATTRIBUTE_LNG = "lng";
	static final String ATTRIBUTE_DAILY = "daily";
	static final String ATTRIBUTE_CNT = "cnt";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	    throws IOException {
	  resp.setContentType("application/json");
    final AsyncContext aCtx = req.startAsync(req, resp);
    aCtx.start(new GetForecastService(aCtx));
	}
	
	private class GetForecastService implements Runnable {
	  AsyncContext aCtx;

    public GetForecastService(AsyncContext aCtx) {
      this.aCtx = aCtx;
    }

    @Override
    public void run() {
      HttpServletRequest req = (HttpServletRequest)aCtx.getRequest();
      try {
        double lat = Double.parseDouble(req.getParameter(ATTRIBUTE_LAT));
        double lng = Double.parseDouble(req.getParameter(ATTRIBUTE_LNG));
        boolean daily = Boolean.valueOf(req.getParameter(ATTRIBUTE_DAILY))
          .booleanValue();  
        int count = Integer.parseInt(req.getParameter(ATTRIBUTE_CNT));

        WeatherService service = new WeatherService(aCtx);   
        if (daily) {
          service.getDailyForecast(lat, lng, count);
        } else {
          service.getHourlyForecast(lat, lng, count);
        }  
      } catch (NumberFormatException e) {
        ((HttpServletResponse)aCtx.getResponse())
          .setStatus(HttpServletResponse.SC_BAD_REQUEST);
        aCtx.complete();
      }      
    }	  
	}
}
