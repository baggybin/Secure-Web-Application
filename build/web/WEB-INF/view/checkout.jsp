<%--
    Document   : checkout
    Created on : 11-Apr-2015, 20:15:05
    Author     : Jonathan , Nidhu , Cathal
--%>
<%@include file="header.jsp" %>
<%@ page errorPage="errorPage.jsp" %>

<div>
    <h2>checkout</h2>
    <p></p>
    <div>
        <table >
            <tr>
                <%--                   total--%>
                <td>total:</td>
                <td>
                    &euro; ${cart.subtotal}</td>
            </tr>
            <tr>
            </tr>
            <%-- accept order, cancel (dont proceed), clear cart--%>
        </table>
        <br>
        <a href="cancel">Cancel</a>     
        <br>
        <a href="final">Accept</a>
        <br>
        <a href="viewCart?clear=true" >clear cart</a>
    </div>
</div>