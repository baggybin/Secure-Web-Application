<%-- 
    Document   : pManageConfirm
    Created on : 17-Apr-2015, 19:01:12
    Author     : Jonathan, nidhu, cathal
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@include file="header.jsp" %>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--admin managemtn confirmation page--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

                <h3>Action Confirmed</h3>
                
                <br><a href=index.jsp>Go Back to Index Page</a>         

            </c:when> 
            <c:otherwise>
                You are a Standard User - Un-authorised Access
            </c:otherwise>   
        </c:choose>    
    </body>
</html>
