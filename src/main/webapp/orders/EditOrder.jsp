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
    <title>Admin</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/bootstrap/js/bootstrap.min.js"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<jsp:include page="/admin/HeaderAdmin.jsp"/>
<body>
    <h1>Tariffs management</h1>
    <h2>
        &nbsp;&nbsp;&nbsp;
        <a href="/list">List All tariffs</a>

    </h2>
<div align="center">
    <c:if test="${product != null}">
    <form action="update" method="post">
        </c:if>

            <c:if test="${product == null}">

            <form action="insert" method="post">
            </c:if>


            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${product != null}">
                            Edit Book
                        </c:if>
                        <c:if test="${product == null}">
                            Add New Book
                        </c:if>
                    </h2>
                </caption>

                <c:if test="${product != null}">
                    <input type="hidden" name="id" value="<c:out value='${product.id}' />" />
                </c:if>
                <tr>
                    <th>Name</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${product.customer.id}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Description </th>
                    <td>
                        <input type="text" name="description" size="45"
                               value="<c:out value='${product.packages.id}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th>Image </th>
                    <td>
                        <input type="text" name="image" size="45"
                               value=<fmt:formatDate value="${product.dateEnd}" pattern="yyyy-MM-dd HH:mm:ss" />
                                       />
                                       </td>"

                    </td>
                </tr>

                <tr>
                    <th>Price </th>
                    <td>
                        <input type="text" name="price" size="45"
                               value="<c:out value='${product.id}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save" />
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>
<% } %>