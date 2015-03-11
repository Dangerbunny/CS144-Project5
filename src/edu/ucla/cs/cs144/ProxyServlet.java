package edu.ucla.cs.cs144;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String query = request.getParameter("q");
    	URL url = new URL(URIUtil.encodeQuery("http://google.com/complete/search?output=toolbar&q="+query));
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	String theString;
    	try {
    	    InputStream in = new BufferedInputStream(conn.getInputStream());
    	    theString = IOUtils.toString(in, Charset.defaultCharset().toString());
    	}
    	finally {
    		conn.disconnect();
    	}
//    	request.setAttribute("data", theString);
    	response.setCharacterEncoding("UTF-8"); 
    	response.setContentType("text/xml");
    	response.getWriter().write(theString);
    }
    
    /**
     * String query = request.getParameter("q");
    	URL url = new URL("http://google.com/complete/search?output=toolbar&q="+query);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("GET");
    	conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inline = "";
        while ((inline = br.readLine()) != null) {
          sb.append(inline);
        }
        request.setAttribute("data", sb.toString());
        request.getRequestDispatcher("/temp.jsp").forward(request, response);
     */
}
