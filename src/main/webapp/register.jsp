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
<form action="register" method="get">
    <input type="hidden" name="action" value="add">

    <label>First Name:</label><br>
    <input type="text" name="firstName" value="${requestScope['customer'].firstName}" required><br>

    <label>Last Name:</label><br>
    <input type="text" name="lastName" value="${requestScope['customer'].lastName}" required><br>

    <label>Email:</label><br>
    <input type="email" name="email" value="${requestScope['customer'].email}" required><br>

    <label>Password:</label><br>
    <input type="password" name="password" value="${requestScope['customer'].password}" required><br>

    <input type="submit" value="Join Now" id="submit "/>
</form>
</body>
<jsp:include page="/footer.jsp" />
</html>
