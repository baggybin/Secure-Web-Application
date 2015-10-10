<%--
    Document   : index
    Created on : 
    Author     : Jonthan, Nidhu, Cathal
--%>

<%--Main landing page--%>

<%@ page errorPage="errorPage.jsp" %>
<%--import javafaces and jstl support--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@include file="header.jsp" %>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%> 
<%@ page language="java" import="captchas.CaptchasDotNet" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Shop Welcome</title>

    </head>
    <body onload="Captcha();">
        <div>
            <div>
                <p style="font-size: large">Welcome</p>
            </div>
        </div>

        <div>
            <%---            for each category made availible by the controller--%>
            <c:forEach var="category" items="${categories}">
                <div>
                    <a href="category?${category.id}">
                        <br>
                        <span>${category.name}</span>
                    </a>
                </div>
            </c:forEach>
        </div>

        <br>
        <br>

        <%--        allow search for product by id or name--%>

        <%
            // Construct the captchas object (Default Values)
            CaptchasDotNet captchas = new captchas.CaptchasDotNet(
                    request.getSession(true), // Ensure session
                    "cathaldcronin1", // client
                    "pMEUfYtuPU9Uku8X0OxdFrFaoXDkI7PNq5hLFIdS" // secret
            );
            // Construct the captchas object (Extended example)
            // CaptchasDotNet captchas = new captchas.CaptchasDotNet(
            //  request.getSession(true),     // Ensure session
            //  "demo",                       // client
            //  "secret",                     // secret
            //  "01",                         // alphabet
            //  16,                           // letters
            //  500,                          // width
            //  80                            // height
            //  );
%>
        <%-- 
         % encodeUrl produces jsessionid=xyz in case of disabled cookies
         % Please test your implementation also with disabled cookies
        --%>
        <form action="searching" method="post">
            Search Entry:<br>
            <input type="text"  name="search">
            <br>
            <input type="radio" name="type" value="1">ID
            <br>
            <input type="radio" name="type" value="2">Name

            <br>Enter the captcha before submit:<input name="password" size="16" />

            <%= captchas.image()%>

            <br>
            <input type="submit" value="Submit">
        </form>
    </form>

    <%--        faces logout function for the current user session--%>
    <f:view>
        <!-- <p><h:outputText value="--------------------" /></p>.-->
        <br> 
        <h:form>
            <h:commandButton id="logout" value="Logout" action="#{usermanager.logout}"/>
        </h:form>

        <%--            welcome the user--%>
        <p> Welcome ${user.username}!  You've been registered since ${user.since} and Role is ${user.role}<p/>
    </f:view>     


    <%--        show the admin link if there is an admin role logged in--%>
    <c:choose>
        <c:when test="${user.role}">
            You are an Administrator 
            <a href="admin">Click to Enter Admin Console</a>
        </c:when> 
        <c:otherwise>
            You are a Standard User 
        </c:otherwise>   

    </c:choose>         

</body>
</html>

