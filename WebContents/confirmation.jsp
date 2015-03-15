<!DOCTYPE html>

<html>
    <head>
        <title> Credit Card Confirmation</title>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    </head>
    <body>
    <h1> Here is the receipt of your transaction </h1> 
    <c:if test="${not empty CCNum}">
        <div id="cInfoDiv">
            <p>Item ID: ${ItemID}</p>
            <p>Item Name: ${ItemName}</p>
            <p>Buyout Price: ${Buyout}</p>
            <p>Credit Card: ${CCNum}</p>
            <p>Time: ${time}</p>
        </div>  
    </c:if> 
    <a href="http://localhost:1448/eBay/keywordSearch.html"> Return to search page </a>
    </body>
</html>