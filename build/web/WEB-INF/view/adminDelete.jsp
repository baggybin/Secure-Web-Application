<%-- 
    Document   : adminDelete - admin page for modifying prodcuts - delete - increase- decrease
    Created on : 18-Apr-2015, 15:50:31
    Author     : Jonathan, nidhu , catahl
--%>



<%@ page errorPage="errorPage.jsp" %>
<%@page import="javax.persistence.Query"%>
<%@page import="java.util.List"%>
<%@ page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Product" %> 
<!DOCTYPE html>
<%@include file="header.jsp" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ADMIN Delete Search Results</title>
    </head>
    <body>
        <!--Stop gap measure to disable code generation on client browser for unauthorised
        had issues finding a better role based access session method to enforce secure zone
        
        https://www.owasp.org/index.php/Top_10_2013-A7-Missing_Function_Level_Access_Control
        
        
        the user role is extracted from the session and checked to verify if they are and administrator (true)
        code will not be generated in the client browser is this is not the case
        this page can be accessed directly by url, but this stops the admin functions being accessed by standard users
        -->
        <c:choose>
            <c:when test="${user.role}">                
                <h1>Result for Name Search</h1>

                <%
                    // get the procucts found by the controller from the session
                    // shows if there is an exact match 
                    try {
                        int id = 0;
                        List<Product> products = (List<Product>) session.getAttribute("products");

                        // for each product print, the products details,
                        // and offer a increase, decrease and delete option
                        for (Product product : products) {
                            out.println("<br><a> Product Name :" + product.getName());
                            out.println("<br><a> Product Description :" + product.getDescription());
                            out.println("<br><a> Product Price :" + product.getPrice());
                            out.println("<br><a> Product ID :" + product.getId());
                            id = product.getId();

                            // remove the product
                            String s = " <form action=\"removeProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String s2 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Delete\">\n </form>\n <br>\n\n ";
                            out.println(s + s2);
                            // decrease the prodcut
                            String s3 = " <form action=\"decreaseProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String s4 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Decrease\">\n </form>\n <br>\n\n ";
                            out.println(s3 + s4);
                            // increase product
                            String s5 = " <form action=\"increaseProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String s6 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Increase\">\n </form>\n <br>\n\n ";
                            out.println(s5 + s6);
                        }

                    } catch (NullPointerException ex) {
                        ex.getMessage();
                    }
                %>


                <%       try {
                        // shows if the search string is a component of the name of a prodcut, found by regular expression matching
                        out.println("<h3> Simular Results <h3>");
                        // return the products list from the session
                        List<Product> pPattern = (List<Product>) session.getAttribute("pPattern");
                        // print details and again the  same admin funtions
                        for (Product pproduct : pPattern) {
                            out.println("<br>");
                            out.println("<br><a> Product Name :" + pproduct.getName());
                            out.println("<br><a> Product Description :" + pproduct.getDescription());
                            out.println("<br><a> Product Price :" + pproduct.getPrice());
                            out.println("<br><a> Product ID :" + pproduct.getId());
                            int id = pproduct.getId();
                            // remove
                            String t1 = " <form action=\"removeProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String t2 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Delete\">\n </form>\n <br>\n\n ";
                            out.println(t1 + t2);
                            // decrease
                            String t3 = " <form action=\"decreaseProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String t4 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Decrease\">\n </form>\n <br>\n\n ";
                            out.println(t3 + t4);
                            // increase
                            String t5 = " <form action=\"increaseProduct\" method=\"post\">\n <input type=\"hidden\"\n name=\"productId\"\n value=\"" + id + "\">";
                            String t6 = " <input type=\"submit\"\n name=\"submit\"\n value=\"Increase\">\n </form>\n <br>\n\n ";
                            out.println(t5 + t6);

                        }
                    } catch (NullPointerException ex) {
                        ex.getMessage();};
                %>       


                <br><a href=index.jsp>Go Back to Index Page</a>       

            </c:when> 
            <%--shown if unauthoriased access--%>
            <c:otherwise>
                You are a Standard User - Un-authorised Access
            </c:otherwise>   
        </c:choose>      

    </body>
</html>