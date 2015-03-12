<!DOCTYPE html> 
<html>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
        <style type="text/css"> 
          html { height: 100% } 
          body { height: 100%; margin: 0px; padding: 0px } 
          #map_canvas { 
            height: 100%;
        } 
        </style> 
        <script type="text/javascript" 
            src="http://maps.google.com/maps/api/js?sensor=false"> 
        </script> 
        <script type="text/javascript"> 
          function initialize() { 
            var lat = '${data.iLoc.lat}';
            var lon = '${data.iLoc.lon}';
            var latlng;
            if(lat == '' || lon == ''){
                var req = {'address': '${data.iLoc.text}'};
                geocoder = new google.maps.Geocoder();
                geocoder.geocode(req, function(results, status){
                    if (status == google.maps.GeocoderStatus.OK) {
                        latlng = results[0].geometry.location;
                        var myOptions = { 
                          zoom: 14, // default is 8  
                          center: latlng, 
                          mapTypeId: google.maps.MapTypeId.ROADMAP 
                        }; 
                        var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions); 
                    }
                });
            } else{
               latlng = new google.maps.LatLng(lat,lon); 
               var myOptions = { 
                  zoom: 14, // default is 8  
                  center: latlng, 
                  mapTypeId: google.maps.MapTypeId.ROADMAP 
                }; 
                var map = new google.maps.Map(document.getElementById("map_canvas"), 
                    myOptions); 
            }
          } 

        </script> 
        <h1> Results </h1>
    </head>
    <body onLoad="initialize()">
        <form action='item' method='GET'>
            <h3> Search for another item </h3>
            <input name='id' type='text'/>
            <input type='submit'/>
        </form>
        <h3> Item Results </h3>
        <c:choose>
            <c:when test="${not empty data}">
                <h4>Item: ${data.name} with ID: ${data.itemId} </h4>
                <strong>General Information: </strong>
                <ul>
                    <c:if test="${not empty data.buyout}">
                        <li>Buyout Price: ${data.buyout}
                            <form style="display: inline-block" action='processCard' method='GET'>
                                <input type='submit' value="Buy Now"/>
                            </form>
                        </li>
                    </c:if>
                    <li>Initial Bid: ${data.initialBid}</li>
                    <li>Current Bid: ${data.currBid}</li>
                    <li>Number of Bids: ${data.numBids}</li>
                    <li>Start Time: ${data.startTime}</li>
                    <li>End Time: ${data.endTime}</li>
                </ul> 
                <strong>Categories</strong>
                <ol>
                    <c:forEach items="${data.categories}" var="cat">
                        <li>
                            ${cat}
                        </li>
                    </c:forEach>
                </ol>

                <p> 
                    <strong>Item Location</strong>
                    <em> Name: </em>${data.iLoc.text}
                    <em> Country: </em>${data.iLoc.country} 
                    <c:if test="${not empty data.iLoc.lat}">
                        <em> Latitude: </em>${data.iLoc.lat} 
                        <em> Longitude: </em>${data.iLoc.lon} 
                    </c:if>
                </p>
               
                <p>
                    <strong>Item Description: </strong>
                    ${data.description}
                </p>
                <c:if test="${not empty data.bids}">
                    <strong>Bid Information: </strong> 
                    <ol>
                        <c:forEach items="${data.bids}" var="bid">
                            <li>
                                <em>User Id: </em>${bid.userId} 
                                <em> Rating: </em>${bid.bRating} 
                                <em> Location: </em>${bid.bLoc.text}
                                <em> Country: </em>${bid.bLoc.country} 
                                <em> Time: </em>${bid.time} 
                                <em> Amount: </em>${bid.amount}   
                            </li>
                        </c:forEach>
                    </ol>
                </c:if>
                <div id="map_canvas" style="width:100%; height:100%"></div> 
            </c:when>
            <c:otherwise>
                <h4> Item corresponding to entered item ID was not found </h4>
            </c:otherwise>
        </c:choose>
    </body>
</html>