<%-- 
    Document   : search
    Created on : 17-Apr-2015, 11:05:17
    Author     : Jonathan, nidhu, cathal
--%>

<%--            user check--%>
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
        <h3>Result for ID Search</h3>

        <%
            // get reulsts fo searched  products from session
            Product product = (Product) session.getAttribute("products");
            String result = (String) session.getAttribute("r");
            String name = "not found";

            // if found set details or if not set defualts
            int id = 0;
            if (result.equals("true")) {
                id = product.getId();
                name = product.getName();
            } else if (result.equals("false")) {
                id = -1;
                name = "No Products Found!";
            }

            // show the details 
        %>

        <p>The Name of the Product</p>
        <%= name%>
        <p>The ID of the product</p>
        <%= id%>


        <!--add the product to cart-->
        <%String s3 = " <form action=\"addToCart\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
            String s4 = " <input type=\"submit\"\n name=\"submit\"\n value=\"add to cart\">\n </form>\n <br>\n\n ";

            out.println(s3 + s4);%>

        <br>
        <a href=index.jsp>Go Back to Index Page</a>       

    </body>
</html>




