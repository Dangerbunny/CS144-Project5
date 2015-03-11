package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    public class Location{
    	public Double getLat() {
			return lat;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public Double getLon() {
			return lon;
		}
		public void setLon(Double lon) {
			this.lon = lon;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		Double lat, lon;
		String text;
    	String country;
    	
    	public Location(double lat, double lon, String text, String country) {
			super();
			this.lat = lat;
			this.lon = lon;
			this.text = text;
			this.country = country;
		}
    	public Location(String text, String country) {
			super();
			this.text = text;
			this.country = country;
			lat = null;
			lon = null;
		}
    }
    
    public class Bid{
    	String bRating;
		String userId;
    	Location bLoc;
    	Date time;
    	public String getbRating() {
			return bRating;
		}

		public void setbRating(String bRating) {
			this.bRating = bRating;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Location getbLoc() {
			return bLoc;
		}

		public void setbLoc(Location bLoc) {
			this.bLoc = bLoc;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		String amount;
    	
    	public Bid(String bRating, String userId, Date time,
				String amount) {
			super();
			this.bRating = bRating;
			this.userId = userId;
			this.time = time;
			this.amount = amount;
		}
    }
    
    public class ItemData{
    	String itemId;
    	public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String[] getCategories() {
			return categories;
		}

		public void setCategories(String[] categories) {
			this.categories = categories;
		}

		public String getCurrBid() {
			return currBid;
		}

		public void setCurrBid(String currBid) {
			this.currBid = currBid;
		}

		public String getInitialBid() {
			return initialBid;
		}

		public void setInitialBid(String initialBid) {
			this.initialBid = initialBid;
		}

		public String getNumBids() {
			return numBids;
		}

		public void setNumBids(String numBids) {
			this.numBids = numBids;
		}

		public Bid[] getBids() {
			return bids;
		}

		public void setBids(Bid[] bids) {
			this.bids = bids;
		}

		public Location getiLoc() {
			return iLoc;
		}

		public void setiLoc(Location iLoc) {
			this.iLoc = iLoc;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public String getSellId() {
			return sellId;
		}

		public void setSellId(String sellId) {
			this.sellId = sellId;
		}

		public String getsRating() {
			return sRating;
		}

		public void setsRating(String sRating) {
			this.sRating = sRating;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

    	public String getBuyout() {
			return buyout;
		}

		public void setBuyout(String buyout) {
			this.buyout = buyout;
		}

		String name;
    	String[] categories;
    	String currBid;
    	String initialBid;
    	String numBids;
    	Bid[] bids;
    	Location iLoc;
    	Date startTime;
    	Date endTime;
    	String sellId;
    	String sRating;
    	String description;
    	String buyout;
    	
		public void sortBids(){
    		Bid temp;
    		for(int i = 0; i < bids.length/2; i++){
    			temp = bids[i];
    			bids[i] = bids[bids.length-i-1];
    			bids[bids.length-i-1] = temp;
    		}
    	}
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	if(request.getParameter("id") == "" || !(request.getParameter("id").matches("[0-9]+"))){
    		request.getRequestDispatcher("/itemData.jsp").forward(request, response);
    		return;
    	}
        String xmlData = AuctionSearchClient.getXMLDataForItemId(request.getParameter("id"));
        if(xmlData.length() == 0){
        	request.getRequestDispatcher("/itemData.jsp").forward(request, response);
        	return;
        }
        Document doc = null;
        try {
			doc = loadXMLFromString(xmlData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SimpleDateFormat inFormatter =
                new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        
        Element root = doc.getDocumentElement();
        ItemData data = new ItemData();
        data.itemId = root.getAttribute("ItemId");
        
        data.name = getElementTextByTagNameNR(root, "Name");
        ArrayList<String> categories = new ArrayList<String>();
        for(Element e : getElementsByTagNameNR(root, "Category")){
        	categories.add(getElementText(e));
        }
        
        data.categories = new String[categories.size()];
        data.categories = categories.toArray(data.categories);
        data.currBid = getElementTextByTagNameNR(root, "Currently");
        data.initialBid = getElementTextByTagNameNR(root, "First_Bid");
        data.numBids = getElementTextByTagNameNR(root, "Number_of_Bids");
        data.buyout = getElementTextByTagNameNR(root, "Buy_Price");
        
        ArrayList<Bid> bids = new ArrayList<Bid>();
        for(Element bid : getElementsByTagNameNR(getElementByTagNameNR(root, "Bids"), "Bid")){
        	Bid b = null;
        	Element bidder = getElementByTagNameNR(bid, "Bidder");
        	try {
				b = new Bid(bidder.getAttribute("Rating"), 
							bidder.getAttribute("UserID"), 
							inFormatter.parse(getElementTextByTagNameNR(bid, "Time")),
							getElementTextByTagNameNR(bid, "Amount"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	Location bidL = new Location(getElementTextByTagNameNR(bidder, "Location"), getElementTextByTagNameNR(bidder, "Country"));
        	b.bLoc = bidL;
        	bids.add(b);
        }
        data.bids = new Bid[bids.size()];
        data.bids = bids.toArray(data.bids);
        data.sortBids();
        
        Element itemLocElement = getElementByTagNameNR(root, "Location");
        Location itemL = null;
        try{
        	itemL = new Location(Double.parseDouble(itemLocElement.getAttribute("Latitude")), 
        		Double.parseDouble(itemLocElement.getAttribute("Longitude")),
        		getElementText(itemLocElement),
        		getElementTextByTagNameNR(root, "Country"));
        } catch (NumberFormatException nf){
        	itemL = new Location(getElementText(itemLocElement),
        		getElementTextByTagNameNR(root, "Country"));
        }
        data.iLoc = itemL;
        try{
	        data.startTime = inFormatter.parse(getElementTextByTagNameNR(root, "Started"));
	        data.endTime = inFormatter.parse(getElementTextByTagNameNR(root, "Ends"));
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        Element sellerElement = getElementByTagNameNR(root, "Seller");
        data.sellId = sellerElement.getAttribute("UserID");
        data.sRating = sellerElement.getAttribute("Rating");
        data.description = getElementTextByTagNameNR(root, "Description");
        
        if(data.buyout != null){
        	HttpSession session = request.getSession(true);
        	session.setAttribute("ItemID", data.itemId);
        	session.setAttribute("ItemName", data.name);
        	session.setAttribute("Buyout", data.buyout);
        }
        
        request.setAttribute("data", data);
        request.setAttribute("raw", xmlData);
        request.getRequestDispatcher("/itemData.jsp").forward(request, response);
 
    }
    
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    
  static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
               "in the supplied XML files.");
        System.exit(3);
    }
    
}

/* Non-recursive (NR) version of Node.getElementsByTagName(...)
 */
static Element[] getElementsByTagNameNR(Element e, String tagName) {
    Vector< Element > elements = new Vector< Element >();
    Node child = e.getFirstChild();
    while (child != null) {
        if (child instanceof Element && child.getNodeName().equals(tagName))
        {
            elements.add( (Element)child );
        }
        child = child.getNextSibling();
    }
    Element[] result = new Element[elements.size()];
    elements.copyInto(result);
    return result;
}

/* Returns the first subelement of e matching the given tagName, or
 * null if one does not exist. NR means Non-Recursive.
 */
static Element getElementByTagNameNR(Element e, String tagName) {
    Node child = e.getFirstChild();
    while (child != null) {
        if (child instanceof Element && child.getNodeName().equals(tagName))
            return (Element) child;
        child = child.getNextSibling();
    }
    return null;
}

/* Returns the text associated with the given element (which must have
 * type #PCDATA) as child, or "" if it contains no text.
 */
static String getElementText(Element e) {
    if (e.getChildNodes().getLength() == 1) {
        Text elementText = (Text) e.getFirstChild();
        return elementText.getNodeValue();
    }
    else
        return "";
}

/* Returns the text (#PCDATA) associated with the first subelement X
 * of e with the given tagName. If no such X exists or X contains no
 * text, "" is returned. NR means Non-Recursive.
 */
static String getElementTextByTagNameNR(Element e, String tagName) {
    Element elem = getElementByTagNameNR(e, tagName);
    if (elem != null)
        return getElementText(elem);
    else
        return "";
}
    
}
