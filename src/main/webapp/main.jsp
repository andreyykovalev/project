<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Internet</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/bootstrap/js/bootstrap.min.js"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<jsp:include page="/header.jsp" />

<body style="background-color: #cecaff">

<h4> <c:if test="${welcome == null}"><h4> Welcome </h4>></c:if> ${welcome} ${keycustomer}</h4>

<div id="wrapper">
    <div id="articles">
    <span id="span_big">
        <div style="border: inherit;min-height: 40px; padding: 5px;">
            <h6>Sort by:
    <a style="margin-left: 5px;" href="<c:url value="/price/sort"/>">price</a>
    <a style="margin-left: 5px;" href="<c:url value="/name/sort"/>">a-z</a>
    <a style="margin-left: 5px;" href="<c:url value="/reverse/name/sort"/>">z-a</a></h6>
</div>

        <table class="table table-hover">
            <thead>
            <tr>
                <th><c:if test="${picture == null}"> Picture </c:if> ${picture}</th>
                <th><c:if test="${description == null}"> Description </c:if> ${description}</th>
                <th><c:if test="${price == null}"> Price </c:if> ${price}</th>
                <th><c:if test="${title == null}"> Title </c:if> ${title}</th>
                <th><c:if test="${details == null}"> Details </c:if> ${details}</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${packages}">
            <tr>
                <td><img src="<c:out value="${product.image}" />" alt=""> </td>
                <td><c:out value="${product.description}" /></td>
                <td><c:out value="${product.price}" /></td>
                <td><c:out value="${product.name}" /></td>

                <td><a href="/internet?id=${product.id}"> <c:if test="${buttondetails == null}"> Details </c:if> ${buttondetails} </a></td>

            </tr>
            </c:forEach>
            </tbody>
        </table>
    </span>
        <c:if test="${keycustomer != null}">
        <span id="span_small">

                <h6><a href="/workspace"> <c:if test="${workspace == null}"> My workspace </c:if> ${workspace} </a></h6>

            <img id="img2" src="pictures/cabinet.jpg" alt="Изображение" title="Изображение">

    </span>
        </c:if>
</div>

</div>
</body>

<jsp:include page="/footer.jsp" />

</html>