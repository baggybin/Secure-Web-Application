<%--
    Document   : category
    Created on : 11-Apr-2015, 19:13:01
    Author     : Jonathan, nidhu, cathal
--%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@include file="header.jsp" %>
<%@ page errorPage="errorPage.jsp" %>
<%--reference - followed the netbeans affable bean tutorial--%>

<div >
<%--    show the catagories taken from the session that are generated in the controller servlet via a session facade
    then session using the servlets  context --%>
    <c:forEach var="category" items="${categories}">
        <c:choose>
            <c:when test="${category.name == selectedCategory.name}">
                <div >
                    <span >
                        ${category.name}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="category?${category.id}" >
                    <span >
                        ${category.name}
                    </span>
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<div >
<%--    for the selected catagory show the products that it contains
    with the details, price
    comments and ability to add comments
    the ability to add the product to the cart--%>
    <p >${selectedCategory.name}</p>
    <table style="width:100%">
        <c:forEach var="product" items="${categoryProducts}" varStatus="iter">
            <tr>
                <td>
                    ${product.name}
                    <br>
                    <span >${product.description}</span>
                </td>

                <td>&euro; ${product.price}</td>
                <td>
                    <form action="addToCart" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${product.id}">
                        <input type="submit"
                               name="submit"
                               value="add to cart">
                    </form>
                </td>
            </tr>
            <tr>
                <th>
                    <%-- view comments extracted from the database for each product via key lookup--%>
                    <c:forEach items="${commentsProducts}" var="entry">
                        <c:if test="${not empty entry.value and entry.key == product.id}">
                            <c:forEach items="${entry.value}" var="val"> 
                                ${val}
                                <br>
                            </c:forEach>
                        </c:if>
                    </c:forEach>

                </th>    
            </tr>

            <tr>
                 <td>
                     <%--                     add comment via id--%>
                    <form action="addComment" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${product.id}">
                        <input type ="text"
                               name ="comment">                              
                        <input type="submit"
                               name="submit"
                               value="add comment">
                    </form>


                </td>               
                
            </tr>

        </c:forEach>
    </table>
</div>