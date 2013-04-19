package com.ifactory.service.weatherserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CurrentWeatherServlet extends HttpServlet {
	static final String ATTRIBUTE_STATUS = "status";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		resp.setContentType("text/html");
	    PrintWriter out = resp.getWriter();

	    out.print("<html><body>");
	    out.print("<head>");
	    out.print("  <title>GCM Demo</title>");
	    out.print("  <link rel='icon' href='favicon.png'/>");
	    out.print("</head>");
	    String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
	    if (status != null) {
	      out.print(status);
	    }
	    
	    // List<String> devices = Datastore.getDevices();
	    // if (devices.isEmpty()) {
	      out.print("<h2>No devices registered!</h2>");
	    // } else {
	    //  out.print("<h2>" + devices.size() + " device(s) registered!</h2>");
	    //  out.print("<form name='form' method='POST' action='sendAll'>");
	    //  out.print("<input type='submit' value='Send Message' />");
	    //  out.print("</form>");
	    // }
	    out.print("</body></html>");
	    resp.setStatus(HttpServletResponse.SC_OK);
	}
}
