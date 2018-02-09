<%@ page import="com.epam.rd.model.entity.EntityCustomer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign in</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<jsp:include page="/header.jsp" />

<body style="background-color: #cecaff">

<p><i>${message}</i></p>
<form action="register" method="post">
    <input type="hidden" name="action" value="add">

    <label><c:if test="${name == null}">First Name</c:if>${name}:</label><br>
    <input type="text" name="firstName" value="${sessionScope['customer'].firstname}" required><br>

    <label><c:if test="${surname == null}">Last Name</c:if>${surname}:</label><br>
    <input type="text" name="lastName" value="${sessionScope['customer'].lastname}" required><br>

    <label><c:if test="${labelemail == null}">Email</c:if>${labelemail}:</label><br>
    <input type="email" name="email" value="${sessionScope['customer'].mail}" required><i><c:if test="${registeremailissue == true}">${existsemail}</c:if></i><br>

    <label><c:if test="${labelpassword == null}">Password</c:if>${labelpassword}:</label><br>
    <input type="password" name="password" value="${sessionScope['customer'].password}" required><br>

    <input style="margin-top: 5px" type="submit" value="<c:if test="${join == null}">Email</c:if>${join}" id="submit "/>
</form>
</body>
<jsp:include page="/footer.jsp" />
</html>
