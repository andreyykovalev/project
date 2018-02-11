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

<h5> <c:if test="${welcome == null}"><h5> Welcome </h5>></c:if> ${welcome} ${keycustomer}</h5>

<div id="wrapper">
    <div id="articles">
    <span id="span_big">
        <div style="border: inherit;min-height: 40px; padding: 5px;">
            <h6><c:if test="${sort_by == null}"> Sort by </c:if> ${sort_by}:
    <a style="margin-left: 5px;" href="<c:url value="/price/sort"/>"><c:if test="${price == null}"> Price </c:if> ${price}</a>
    <a style="margin-left: 5px;" href="<c:url value="/name/sort"/>"><c:if test="${sort_format == null}"> a-z </c:if> ${sort_format}</a>
    <a style="margin-left: 5px;" href="<c:url value="/reverse/name/sort"/>"><c:if test="${sort_format_reversed == null}"> a-z </c:if> ${sort_format_reversed}</a>
                <a style="margin-left: 5px; float: right" href="<c:url value="/DownloadServlet"/>"><c:if test="${download == null}"> Download info about packages </c:if> ${download}</a></h6>
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
                <td><img style="max-height: 90%; max-width: 90%;" src="<c:out value="${product.image}" />" alt=""> </td>
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

            <img id="img2" src="<c:url value="/pictures/cabinet.jpg"/>" alt="Изображение" title="Изображение">

    </span>
        </c:if>
</div>

</div>
</body>

<jsp:include page="/footer.jsp" />

</html>