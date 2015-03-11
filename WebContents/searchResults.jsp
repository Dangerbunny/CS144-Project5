<!DOCTYPE html> 
<html>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <head>
        <script type="text/javascript">
            function getQueryVariable(variable)
            {
                var query = window.location.search.substring(1);
                var vars = query.split("&");
                for(var i=0; i<vars.length; i++)
                {
                    var pair = vars[i].split("=");
                    if(pair[0] == variable)
                    {
                        return pair[1];
                    }
                }
                return(false);
            }
       </script>
        <script type="text/javascript">
            function initialize()
            {
                var newStyle = "";
                var prevURL = "search?q=";
                var nextURL = "search?q=";

                var qValue = getQueryVariable("q");

                var skipValue = getQueryVariable("numResultsToSkip");
                if(skipValue == 0)
                    document.getElementById("previousLink").setAttribute("style","visibility:hidden;");
                var prevSkipValue = parseInt(skipValue)-10;
                var nextSkipValue = parseInt(skipValue)+10;
                var retValue = getQueryVariable("numResultsToReturn");

                prevURL = prevURL + qValue + "&numResultsToSkip=" + prevSkipValue + "&numResultsToReturn=" + retValue;
                nextURL = nextURL + qValue + "&numResultsToSkip=" + nextSkipValue + "&numResultsToReturn=" + retValue;

                document.getElementById("previousLink").setAttribute("href",prevURL);
                document.getElementById("nextLink").setAttribute("href",nextURL);


                if(qValue != false)
                    document.getElementById("linksDiv").setAttribute("style","");
                document.getElementById("test").innerHTML = document.getElementById("test").innerHTML+" "+qValue;
                // if(skipValue==0)
                //     newStyle = document.getElementById("previousLink").getAttribute("style");
                //     newStyle += "visibility:hidden;"
                //     document.getElementById("previousLink").setAttribute("style",newStyle);
            }
        </script>
        <link rel="stylesheet" type="text/css" href="suggestion.css"> 
    </head>
    <body>
        <form action='search' method='GET'>
            <h3> Please enter a query to search for and click submit </h3>
            <input name='q' type='text' id="searchBox"/>
            <input name='numResultsToSkip' value='0' type="hidden"/>
            <input name='numResultsToReturn' value='10' type="hidden"/>
            <input type='submit'/>
        </form>
        <hr/>
        <h1> Results </h1>
        <h3> For query: ${q} </h3>
        <ol>
            <c:forEach items="${results}" var="res">
                <li>
                    <strong>Item: </strong>${res.name} <strong>ItemId: </strong>${res.itemId} <a style="background-color:#CCCCCC;width:75px;text-decoration:none;color:black;display:inline-block;padding:5px;text-align:center;" href="item?id=${res.itemId}">View Item</a>
                </li>
            </c:forEach>
        </ol> 
        <br/>
        <div id="linksDiv" style="visibility:hidden;">
            <a id="previousLink" style="background-color:#CCCCCC;width:75px;text-decoration:none;color:black;display:inline-block;padding:5px;text-align:center;" href="">Previous</a>
            <c:if test="${not empty results}">
                <a id="nextLink" style="background-color:#CCCCCC;width:75px;text-decoration:none;color:black;display:inline-block;padding:5px;text-align:center;" href="">Next</a>
            </c:if>
        </div>
        <script src="suggestion.js"></script>
        <script type="text/javascript">
            window.onload = function(){
                var textBox = new AutoSuggestControl(
                        document.getElementById("searchBox")
                    );
                initialize();
            }
        </script>
    </body>
</html>