<%-- 
    Document   : admin  --- administratorive page
    Created on : 18-Apr-2015, 13:39:21
    Author     : Jonathan, Nidhu, Cathal
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@include file="header.jsp" %>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>


        <%--Stop gap measure to disable code generation on client browser for unauthorised
        had issues finding a better role based access session method to enforce secure zone
        
        https://www.owasp.org/index.php/Top_10_2013-A7-Missing_Function_Level_Access_Control
        
        
        the user role is extracted from the session and checked to verify if they are and administrator (true)
        code will not be generated in the client browser is this is not the case
        this page can be accessed directly by url, but this stops the admin functions being accessed by standard users
        --%>

        <c:choose>
            <c:when test="${user.role}">
                <h3>Admin page</h3> 
                You are an Administrator 
                <div>
                    <%--allow admin to add new products--%>
                    <h4>Add New Product</h4>
                    <form action="addProduct" method="post">
                        <br>
                        Name :<br><input type="text"  name="name"><br>
                        Amount :<br><input type="text"  name="amount"><br>
                        Price :<br><input type="text"  name="price"><br>
                        Description :<br> <input type="text"  name="desc"><br>
                        Category :
                        <input type="radio" name="cat" value="1">1
                        <input type="radio" name="cat" value="2">2
                        <input type="radio" name="cat" value="3">3
                        <input type="radio" name="cat" value="4">4
                        <input type="submit" value="Submit">    
                    </form>
                </div>  

                <br> 
                <%--allow the admin to search for product by name and then modify
                that product after a redirect in the controller--%>
                <h4>Search to Modify</h4>
                <form action="searchingDelete" method="post">
                    Search Entry:<br>
                    <input type="text"  name="search">
                    <br>
                    <input type="submit" value="Submit">
                </form>  

                <br><a href=index.jsp>Go Back to Index Page</a>  
            </c:when> 
            <%--shows if the user accessing is unauthoraised --%>
            <c:otherwise>
                You are a Standard User - Un-authorised Access
            </c:otherwise>   
        </c:choose>       




    </body>
</html>
