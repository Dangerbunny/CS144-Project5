<<!DOCTYPE html>
<html>
    <head>
        <title></title>
    </head>
    <body>
    <h1> Please enter your credit card number </h1> 
    <c:if test="${not empty ItemID}">
        <div id="cInfoDiv">
            <p>Item ID: ${ItemID}</p>
            <p>Item Name: ${ItemName}</p>
            <p>Buyout Price: ${Buyout}</p>
            <form action="processCard" method="GET">
                <input name="CCNum" type="text">
                <input type="submit" value="Purchase">
            </form>
        </div>  
    </c:if> 
    </body>
</html>