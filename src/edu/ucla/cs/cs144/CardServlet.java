package edu.ucla.cs.cs144;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CardServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    if (request.isSecure() && request.getParameter("CCNum") != null) {
	    	HttpSession session = request.getSession();
	    	String CCNum = request.getParameter("CCNum");
	    	Date d = new Date();
	    	session.setAttribute("time", d.toString());
	    	session.setAttribute("CCNum", CCNum);
	    	//request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
	    	response.sendRedirect("confirmation.jsp");
	    } else if (request.isSecure()){
			request.getRequestDispatcher("/cardInput.jsp").forward(request, response);
		}
    }
}
