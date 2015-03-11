<!DOCTYPE html> 
<html>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <head>
        <h1> Results </h1>
    </head>
    <body>
        <div>
            <c:if test="${not empty data}">
                <p>${data}</p>
                <p> Note: Data probably isn't visible, you must view page source</p>
            </c:if>
        </div>
    </body>
</html>

