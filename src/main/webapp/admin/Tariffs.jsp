<%@ page import="com.epam.rd.model.entity.EntityUser" %>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>

<%
    EntityUser admin=(EntityUser)session.getAttribute("admin");
    if(admin == null)
    {
        response.sendRedirect("Login.jsp");
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
<h5>Current date: <tf:tagfile/> </h5>
    <h1 style="margin-left: 10px">Packages</h1>
    <h5 style="margin-left: 10px;">
        <a style="margin-left: 5px;" href="<c:url value="/admin/new"/>">Add new package</a>
        <a style="margin-left: 15px;" href="<c:url value="/orders/main"/>">List of orders</a>
        <a style="margin-left: 15px;" href="<c:url value="/customer/main"/>">List of customers</a>
    </h5>
<div style="float:left">

        <div style="width: 40%; float: left">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${picture == null}"> ID </c:if> ${picture}</th>
                <th><c:if test="${picture == null}"> Name </c:if> ${picture}</th>
                <th><c:if test="${description == null}"> Price </c:if> ${description}</th>
                <th><c:if test="${price == null}"> Package </c:if> ${price}</th>
                <th><c:if test="${title == null}"> Action </c:if> ${title}</th>
                <c:if test="${emailSes != null}">
                    <th><c:if test="${details == null}"> Action </c:if> ${details}</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
             <c:forEach var="product" items="${listPackages}">
            <tr>
                <td><c:out value="${product.id}" /></td>
                <td><c:out value="${product.name}" /></td>
                <td><c:out value="${product.price}" /></td>
                <td><c:out value="${product.description}" /></td>

                <td>
                    <a href="/admin/edit?id=<c:out value='${product.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/admin/delete?id=<c:out value='${product.id}' />">Delete</a>
                </td>
            </tr>
             </c:forEach>
            </tbody>
        </table>
        </div>
    <div style="width: 40%; float: left; margin-left: 100px; ">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${picture == null}"> ID </c:if> ${picture}</th>
                <th><c:if test="${picture == null}"> Name </c:if> ${picture}</th>
                <th><c:if test="${description == null}"> Price </c:if> ${description}</th>
                <th><c:if test="${price == null}"> Package </c:if> ${price}</th>
                <th><c:if test="${title == null}"> Action </c:if> ${title}</th>
                <c:if test="${emailSes != null}">
                    <th><c:if test="${details == null}"> Action </c:if> ${details}</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
             <c:forEach var="product" items="${listPackagesRus}">
            <tr>
                <td><c:out value="${product.id}" /></td>
                <td><c:out value="${product.name}" /></td>
                <td><c:out value="${product.price}" /></td>
                <td><c:out value="${product.description}" /></td>

                <td>
                    <a href="/admin/edit?id=<c:out value='${product.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/admin/delete?id=<c:out value='${product.id}' />">Delete</a>
                </td>
            </tr>
             </c:forEach>
            </tbody>
        </table>
    </div>


</div>
</body>

</html>
<% } %>