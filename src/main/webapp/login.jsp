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

<jsp:include page="/header.jsp" />

<body style="background-color: #cecaff;">

<p><i>${message}</i></p>
<h6><c:if test="${login_welcome == null}">Please, enter your email and password to login</c:if>${login_welcome}</h6>
<form action="<c:url value="/login"/>" method="post">
    <input type="hidden" name="action" value="log">

    <label><c:if test="${labelemail == null}">Email</c:if>${labelemail}:</label><br>
    <input type="email" name="email" value="${sessionScope['user'].mail}" required> <i><c:if test="${loginemailissue == true}">${nosuchemail}</c:if></i><br>

    <label><c:if test="${labelpassword == null}">Password</c:if>${labelpassword}:</label><br>
    <input type="password" name="password" value="${sessionScope['user'].password}" required> <c:if test="${loginpasswordissue == true}">${wrongpassword}</c:if><br>

    <input type="submit" value="<c:if test="${login == null}">Login</c:if>${login}" id="submit "/>


</form>

</body>

<jsp:include page="/footer.jsp" />

<html/>