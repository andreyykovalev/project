<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</head>
<jsp:include page="/header.jsp"/>
<body>
<div align="right"> Your current balance is: ${balance} </div>
<div align="center">
    <h5 align="left" style="margin-left: 10px">  The tariffs you are currently subscribed</h5>
    <span id="span_big">

        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${details == null}"> Title </c:if> ${details}</th>
                <th><c:if test="${picture == null}"> Price </c:if> ${picture}</th>
                <th><c:if test="${description == null}"> Description </c:if> ${description}</th>
                <th><c:if test="${price == null}"> Start date </c:if> ${price}</th>
                <th><c:if test="${title == null}"> End date </c:if> ${title}</th>
                <th><c:if test="${details == null}"> Status </c:if> </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${listOrders}">
            <tr>
                <td><c:out value="${product.packages.name}"/></td>
                <td><c:out value="${product.packages.price}"/></td>
                <td><c:out value="${product.packages.description}"/></td>
                <td><c:out value="${product.createdAt}"/></td>
                <td><c:out value="${product.dateEnd}"/></td>
                <td><c:if test="${product.status == true}"> Active </c:if>
                <c:if test="${product.status == false}"> Cancelled </c:if></td>


            </tr>
            </c:forEach>
            </tbody>
        </table>
    </span>

    <span id="span_small">

        <form action="replenish" method="get">
            <input type="hidden" name="action" value="replenish">

            <label>Amount:</label><br>

            <input type="number" name="amount" value="" required><br>

    <input type="submit" value="replenish" id="submit "/>
</form>

            <img id="img2" src="pictures/cabinet.jpg" alt="Изображение" title="Изображение">

    </span>
</div>
</body>
</html>
