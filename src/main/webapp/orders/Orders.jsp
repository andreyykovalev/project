<%@ page import="com.epam.rd.model.entity.EntityUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    EntityUser admin=(EntityUser)session.getAttribute("admin");
    if(admin == null)
    {
        response.sendRedirect("/admin/Login.jsp");
    }else{ %>
<html>
<head>
    <head>
        <title>Admin</title>
        <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/bootstrap/js/bootstrap.min.js"/>">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
</head>
<jsp:include page="/admin/HeaderAdmin.jsp" />

<body>
<h1 style="margin-left: 10px;">Orders</h1>
<h5 style="margin-left: 10px;">
    <a style="margin-left: 15px;" href="<c:url value="/customer/main"/>">List of customers</a>
    <a style="margin-left: 15px;" href="<c:url value="/admin/main"/>">List of packages</a>
</h5>
<div align="center">
    <div id="articles">
    <span id="span_big">

        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${picture == null}"> Customer Email </c:if> ${picture}</th>
                <th><c:if test="${picture == null}"> Package name </c:if> ${picture}</th>
                <th><c:if test="${description == null}"> Created at </c:if> ${description}</th>
                <th><c:if test="${price == null}"> Date end </c:if> ${price}</th>
                <th><c:if test="${title == null}"> Status </c:if> ${title}</th>
                <th><c:if test="${details == null}"> Action </c:if> ${details}</th>
            </tr>
            </thead>
            <tbody>
             <c:forEach var="product" items="${listProduct}">
            <tr>
                <td><c:out value="${product.customer.mail}" /></td>
                <td><c:out value="${product.packages.name}" /></td>
                <td><c:out value="${product.createdAt}" /></td>
                <td><fmt:formatDate value="${product.dateEnd}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><c:out value="${product.status}" /></td>
                <td>
                    <a href="/orders/edit?id=${product.id}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/orders/delete?id=${product.id}">Delete</a>
                </td>
            </tr>
             </c:forEach>
            </tbody>
        </table>
    </span>
</div>
</div>
</body>

<jsp:include page="/footer.jsp" />
</html>
<% } %>