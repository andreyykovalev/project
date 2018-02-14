<%@ page import="com.epam.rd.model.entity.EntityCustomer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    EntityCustomer customer=(EntityCustomer) session.getAttribute("customer");
    if(customer == null)
    {
        response.sendRedirect("/login");
    }else{ %>

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
<div align="right">  <c:if test="${labelbalance == null}"> Your current balance  </c:if>${labelbalance}: ${balance}</div>
<div align="center">
    <h5 align="left" style="margin-left: 10px">  <c:if test="${workspacemessage == null}"> The tariffs you are currently subscribed </c:if> ${workspacemessage}</h5>
    <span id="span_big">

        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${workspacetitle == null}"> Title </c:if> ${workspacetitle}</th>
                <th><c:if test="${price == null}"> Price </c:if> ${price}</th>
                <th><c:if test="${description == null}"> Description </c:if> ${description}</th>
                <th><c:if test="${startdate == null}"> Start date </c:if> ${startdate}</th>
                <th><c:if test="${enddate == null}"> End date </c:if> ${enddate}</th>
                <th><c:if test="${status == null}"> Status </c:if>${status} </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${listOrders}">
            <tr>
                <td><c:out value="${product.packages.name}"/></td>
                <td><c:out value="${product.packages.price}"/></td>
                <td><c:out value="${product.packages.description}"/></td>
                <td><fmt:formatDate value="${product.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${product.dateEnd}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><c:if test="${product.status == true && lang_number == 1}"> Active </c:if>
                    <c:if test="${product.status == true && lang_number == 2}"> Подключен </c:if>
                <c:if test="${product.status == false && lang_number == 1}"> Cancelled </c:if>
                <c:if test="${product.status == false && lang_number == 2}"> Остановлен </c:if></td>




            </tr>
            </c:forEach>
            </tbody>
        </table>
    </span>

    <span id="span_small">

        <form action="/workspace" method="post">
            <input type="hidden" name="action" value="replenish">

            <label><c:if test="${amount == null}"> Status </c:if>${amount} :</label><br>

            <input min="1" max="9999" type="number" name="amount" value="" required><br>

    <input type="submit" value="<c:if test="${replenish == null}"> Status </c:if>${replenish} " id="submit "/>
</form>

            <img id="img2" src="pictures/cabinet.jpg" alt="Изображение" title="Изображение">

    </span>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
<% } %>