<%-- 
    Document   : home
    Created on : 25-mar-2012, 18:43:41
    Author     : juancavallotti
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>jDTO Binder Pizza Orders!</title>
    </head>
    <body>
        <h1>New Pizza Order</h1>
        <div><a href="/orders.htm">Pizza Orders List</a></div>
        <div>
            <p>Available Pizzas</p>
            <ul>
                <c:forEach items="${pizzas}" var="pizza">
                    <li><c:out value="${pizza.description}" /> <a href="/addToOrder?pizzaID=${pizza.pizzaId}">Add To Order</a></li>
                </c:forEach>
            </ul>
        </div>

        <h2>Current Order</h2>
        <form:form modelAttribute="order" action="/placeOrder">
            <c:if test="${!valid}">
                <div style="border: 1px solid #AFAFAF; background-color: #FFE8E8;">
                    The inputted data is not valid.
                </div>
            </c:if>
            <div><label>Customer Name:</label><form:input path="customerName" /></div>
            <div><label>Customer Phone:</label><form:input path="customerPhone" /></div>
            <div><label>Customer Address:</label><form:input path="customerAddress" /></div>
            <table style="width: 100%; border: 1px solid black">
                <thead>
                    <tr>
                        <th>Pizza Name</th>
                        <th>Rating</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${order.pizzas}" var="pizza" varStatus="stat">
                        <tr>
                            <td><c:out value="${pizza.description}" /></td>
                            <td><c:out value="${pizza.rating}" /></td>
                            <td><a href="/removeFromOrder?itemID=${stat.index}">Remove</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div>Total: ${total}</div>
            <div><button type="submit">Place Order</button></div>
        </form:form>
    </body>
</html>
