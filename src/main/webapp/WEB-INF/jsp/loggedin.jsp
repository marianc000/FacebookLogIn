<%-- 
    Document   : index
    Created on : 22 janv. 2018, 14:25:10
    Author     : mcaikovs
--%>

<%@page import="static login.Constants.USER_SESSION_ATTRIBUTE"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>TEST</title>
        <link rel="stylesheet" href="css/buttonOnly.css">
        <script src="lib/jquery.js"></script>
        <script   src="facebook.js"></script>
    </head> 
    <body>  
        <h1>Hello <%=session.getAttribute(USER_SESSION_ATTRIBUTE)%></h1>
        <a href="logout">Logout</a>
    </body>
</html>
