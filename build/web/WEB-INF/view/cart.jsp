<%--
    Document   : cart
    Created on : 12-Apr-2015, 9:01:01
    Author     : jonathan, nidhu, cathal
--%>
<%@include file="header.jsp" %>
<%@ page errorPage="errorPage.jsp" %>

<%--reference - followed the netbeans affable bean tutorial--%>
<div>   
    
    <c:choose>
        <%--        when no items in cat is greater than one show plural--%>
        <c:when test="${cart.numberOfItems > 1}">
            <p>Shopping cart contains ${cart.numberOfItems} items.</p>
        </c:when>
            <%--            when this is on one show singular--%>
        <c:when test="${cart.numberOfItems == 1}">
            <p>Shopping cart contains ${cart.numberOfItems} item.</p>
        </c:when>
        <c:otherwise>
            <%--            otherwise show empty --%>
            <p>Shopping cart is empty.</p>
        </c:otherwise>
    </c:choose>

    <div>
        <%-- clears the  the carts contents --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <a href="viewCart?clear=true" >clear cart</a>
        </c:if>

        <%-- continue with shppping --%>
        <c:set var="value">
            <c:choose>
                <%-- if a session object exists then send to the previously viewed category --%>
                <c:when test="${!empty selectedCategory}">
                    category
                </c:when>
                <%-- otherwise back to the  index page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <a href="${value}" >continue shopping</a>
        <%-- for  checkout  t occur this cannot  be empty--%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <a href="checkout" >proceed to checkout</a>
        </c:if>
    </div>

        <%--    if not empty calculate a total--%>
    <c:if test="${!empty cart && cart.numberOfItems != 0}">
        <h4 >total: &euro; ${cart.subtotal}</h4>
        <table>
            <tr class="header">
                <th>name</th>
                <th>price</th>
                <th>quantity</th>
            </tr>

            <%--  allow to the change of the quantity of items in the cart--%>
            <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">
                <c:set var="product" value="${cartItem.product}"/>
                <tr>
                    <td>${product.name}</td>
                    <td>
                        &euro; ${cartItem.total}
                        <br>
                        <span>( &euro; ${product.price})</span>
                    </td>

                    <td>
                        <form action="updateCart" method="post">
                            <input type="hidden"
                                   name="productId"
                                   value="${product.id}">
                            <input type="text"
                                   maxlength="2"
                                   size="2"
                                   value="${cartItem.quantity}"
                                   name="quantity"
                                   style="margin:5px">
                            <input type="submit"
                                   name="submit"
                                   value="update">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>