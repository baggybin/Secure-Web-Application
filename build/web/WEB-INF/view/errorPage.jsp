<%-- 
    Document   : errorPage
    Created on : 18-Apr-2015, 17:01:01
    Author     : Jonathan
--%>

<%--error page --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<html>
<head>
<title>Show Error Page</title>
</head>
<body>
<h1>Oooooooopps..........................!!!!!!</h1>
<p>Sorry, an error occurred.</p>
<p>Here is the exception stack trace: </p>
<pre>
<% exception.printStackTrace(response.getWriter()); %>
</pre>
</body>
</html>
