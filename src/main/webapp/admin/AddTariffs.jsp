<%@ page import="com.epam.rd.model.entity.EntityUser" %>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    EntityUser admin=(EntityUser)session.getAttribute("admin");
    if(admin == null)
    {
        response.sendRedirect("Login.jsp");
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

<jsp:include page="HeaderAdmin.jsp"/>
<body>
<h5 style="margin-left: 10px;">
    <a style="margin-left: 15px;" href="<c:url value="/admin/main"/>">List of packages</a>
</h5>
<div align="center">
    <c:if test="${product != null}">
    <form action="update" method="post">
        </c:if>

            <c:if test="${product == null}">

            <form action="insert" method="post">
            </c:if>


            <table border="2" cellpadding="5">
                <caption>
                    <h3>
                        <c:if test="${product != null}">
                            Edit package
                        </c:if>
                        <c:if test="${product == null}">
                            Add New package
                        </c:if>
                    </h3>
                </caption>


                <c:if test="${product != null}">
                    <input type="hidden" name="id" value="<c:out value='${product.id}' />" />
                </c:if>
                <tr>
                    <th style="padding: 5px;">Name</th>
                    <td>
                        <input style="padding: 5px;" type="text" name="name" size="45"
                               value="<c:out value='${product.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th style="padding: 5px;">Description </th>
                    <td>
                        <input style="padding: 5px;" type="text" name="description" size="45"
                               value="<c:out value='${product.description}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th style="padding: 5px;">Image </th>
                    <td>
                        <input style="padding: 5px;" type="text" name="image" size="45"
                               value="<c:out value='${product.image}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th style="padding: 5px;">Price </th>
                    <td>
                        <input style="padding: 5px;" type="text" name="price" size="45"
                               value="<c:out value='${product.price}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th style="padding: 5px;">Type </th>
                    <td>
                        <input style="padding: 5px;" type="text" name="type" size="45"
                               value="<c:out value='${product.type}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th style="padding: 5px;">LangId </th>
                    <td>
                        <input style="padding: 5px;" type="text" name="langId" size="45"
                               value="<c:out value='${product.languageId}' />"
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