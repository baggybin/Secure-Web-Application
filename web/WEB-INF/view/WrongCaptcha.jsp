<%-- 
    Document   : search
    Created on : 17-Apr-2015, 11:05:17
    Author     : Jonathan, nidhu, cathal
--%>

            <%--            user chechk--%>
<%--shows results for standard search for products based on ID--%>
<%@ page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Product" %> 
<!DOCTYPE html>
<%@include file="header.jsp" %>
<%@ page errorPage="errorPage.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Incorrect Captcha!!</h1>
        <a href=index.jsp>Go Back to Index Page</a>       
    </body>
</html>



