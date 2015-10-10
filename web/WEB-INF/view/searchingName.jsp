<%-- 
    Document   : search
    Created on : 17-Apr-2015, 11:05:17
    Author     : Jonathan
--%>
<%@page import="javax.persistence.Query"%>
<%@page import="java.util.List"%>
<%@ page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Product" %> 
<!DOCTYPE html>
<%@include file="header.jsp" %>
<%@ page errorPage="errorPage.jsp" %>

<%-- show results for searching for the product by name--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
    </head>
    <body>
        <h3>Result for Name Search</h3>
        <%
            int id = 0;
            // get the found products list
            List<Product> products = (List<Product>) session.getAttribute("products");

            // for each product display the details
            for (Product product : products) {
                out.println("<br> Product Name :" + product.getName());
                out.println("<br> Product Description :" + product.getDescription());
                out.println("<br> Product Price :" + product.getPrice());
                out.println("<br> Product ID :" + product.getId());
                id = product.getId();

                // allow add to cart form
                String s = " <form action=\"addToCart\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                String s2 = " <input type=\"submit\"\n name=\"submit\"\n value=\"add to cart\">\n </form>\n <br>\n\n ";
                out.println(s + s2);
            }
        %>


        <%
            // print out and display results that were simular with a pattern match
            out.println("<h3> Simular Results </h3>");
            List<Product> pPattern = (List<Product>) session.getAttribute("pPattern");
            for (Product pproduct : pPattern) {
                out.println("<br>");
                out.println("<br> Product Name :" + pproduct.getName());
                out.println("<br> Product Description :" + pproduct.getDescription());
                out.println("<br> Product Price :" + pproduct.getPrice());
                out.println("<br> Product ID :" + pproduct.getId());
                id = pproduct.getId();

                String s3 = " <form action=\"addToCart\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                String s4 = " <input type=\"submit\"\n name=\"submit\"\n value=\"add to cart\">\n </form>\n <br>\n\n ";

                out.println(s3 + s4);

            }
        %>       

        <br><a href=index.jsp>Go Back to Index Page</a>       
    </body>
</html>
