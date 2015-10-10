<%-- 
    Document   : pManageIssue
    Created on : 18-Apr-2015, 16:59:53
    Author     : Jonathan
--%>


<!--display if there is an issue-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="errorPage.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:choose>
            <!--            user chechk-->
            <c:when test="${user.role}">    
                <h1> Issue Encountered!!!!!!!</h1>
                
                <br><a href=index.jsp>Go Back to Index Page</a>         
            </c:when> 
            <c:otherwise>
                You are a Standard User - Un-authorised Access
            </c:otherwise>   
        </c:choose>    
    </body>
</html>
