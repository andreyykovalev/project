<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<jsp:include page="/admin/HeaderAdmin.jsp" />

<body style="background-color: #cecaff">

<p><i>${message}</i></p>
<h6><c:if test="${login_welcome == null}">Please, enter your email and password to login</c:if>${login_welcome}</h6>
<form action="login" method="get">
    <input type="hidden" name="action" value="log">

    <label>Email:</label><br>
    <input type="email" name="email" value="${requestScope['user'].login}" required><br>

    <label>Password:</label><br>
    <input type="password" name="password" value="${requestScope['user'].password}" required><br>

    <input type="submit" value="Login" id="submit "/>
</form>

</body>

<jsp:include page="/footer.jsp" />

<html/>