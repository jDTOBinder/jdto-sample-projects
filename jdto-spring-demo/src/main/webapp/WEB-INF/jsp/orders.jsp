<%-- 
    Document   : orders
    Created on : 25-mar-2012, 22:08:27
    Author     : juancavallotti
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pizza Orders!</title>
    </head>
    <body>
        <h1>List Of Pizza Orders</h1>
        <div><a href="/home.htm">Place New Order</a></div>
        <table style="width: 100%; border: 1px solid black">
            <thead>
                <tr>
                    <th>Customer Name</th>
                    <th>Customer Address</th>
                    <th>Customer Phone</th>
                    <th>Order Date</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${orderList}" var="order">
                    <tr>
                        <td>${order.customerName}</td>
                        <td>${order.customerAddress}</td>
                        <td>${order.customerPhone}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.orderPrice}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
