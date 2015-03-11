package edu.ucla.cs.cs144;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CardServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String desiredScheme = "https" ; // or "http"
	    String usingScheme = request.getScheme();
	    if (!desiredScheme.equals(usingScheme)) {
	    	ServletContext context = request.getServletContext();
	    	String httpsPort = context.getInitParameter("listenPort_https");
	    	String httpPort = context.getInitParameter("listenPort_http");
	    	
		    String uri = request.getRequestURI();

		    uri.replaceAll("http", "https");
		    uri.replaceAll(httpPort, httpsPort);
		    response.sendRedirect(response.encodeRedirectURL(uri.toString()));
		    return;
	    } else{
			request.getRequestDispatcher("/cardInput.jsp").forward(request, response);
		} 
    }
}
