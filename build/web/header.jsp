<%--
    Document   : header
    Created on : 
    Author     : Jonathan, nidhu, cathal
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page errorPage="errorPage.jsp" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="img/favicon.ico">

        <title>BookSter</title>
    </head>
    <body>
        <div>
            <div>
                <div >
                    <div>
                        <%--if cart has items, and not already in the chechkout or cart page 
                        show proceed to chechkout--%>
                        <c:if test="${!empty cart && cart.numberOfItems != 0 &&
                                      !fn:contains(pageContext.request.servletPath,'/checkout') &&
                                      requestScope['javax.servlet.forward.servlet_path'] ne '/checkout' &&
                                      !fn:contains(pageContext.request.servletPath,'/cart') &&
                                      requestScope['javax.servlet.forward.servlet_path'] ne '/cart'}">
                              <a href="checkout">
                                  proceed to checkout
                              </a>
                        </c:if>
                    </div>

                    <div>
                        <span >
                            <c:choose>
                                <%-- show number of items in cart when appropriate othewise show zero--%>
                                <c:when test="${cart.numberOfItems == null}">
                                    0
                                </c:when>
                                <c:otherwise>
                                    ${cart.numberOfItems}
                                </c:otherwise>
                            </c:choose>

                            <%-- wether there is one or other amounts --%>
                            <c:choose>
                                <c:when test="${cart.numberOfItems == 1}">
                                    item
                                </c:when>
                                <c:otherwise>
                                    items
                                </c:otherwise>
                            </c:choose>
                        </span>

                            <%--if not already in the cart page show the view cart link--%>
                        <c:if test="${!empty cart && cart.numberOfItems != 0 &&
                                      !fn:contains(pageContext.request.servletPath,'/cart') &&
                                      requestScope['javax.servlet.forward.servlet_path'] ne '/cart'}">
                              <a href="viewCart">view cart</a>
                        </c:if>
                    </div>
                </div>
                            <%--back to index--%>
                <a href="index.jsp">
                   index
                </a>

            </div>