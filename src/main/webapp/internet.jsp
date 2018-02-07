<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet</title>
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

<body style="background-color: #cecaff">

<h6>${message}</h6>
<div id="wrapper">
    <div id="articles">
<span id="span_big">
<form>
    <table class="table table-hover">
    <thead>
    <tr>
        <th>Picture</th>
        <th>Description</th>
        <th>Price</th>
        <th>Selection</th>
        <c:if test="${keycustomer != null}">
        <th>Details</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
        <tr>
                <td><img src="<c:out value="${pack.image}" />" alt=""> </td>
                <td><c:out value="${pack.description}"/></td>
                <td><c:out value="${pack.price}"/></td>
                <td><c:out value="${pack.name}"/></td>
                <td><form action="buy" method="get">
            <input type="hidden" name="action" value="buy">
            <input type="hidden" name="id" value="${pack.id}">
           <c:if test="${keycustomer != null}">
               <input type="submit" value="buy" id="submit "/>
           </c:if>
</form></td>
            </tr>
        </tr>
    </tbody>
</table>
    </form>
</span>
    </div>
</div>
</body>

<jsp:include page="/footer.jsp"/>
</html>