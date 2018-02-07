<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header>
    <nav class="navbar navbar-default navbar-static" style="background-color: #a4a0d3">
        <div class="container-fluid">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".js-navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="/main"/>" style="padding:15px 15px;"> <c:if
                        test="${main == null}"> Internet provider </c:if>${main}</a>
            </div>
            <div class="collapse navbar-collapse js-navbar">

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a id="drop2" href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <c:if test="${settings == null}"> Settings </c:if> ${settings}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">

                            <c:if test="${keycustomer == null}">
                                <li><a href="<c:url value="/login?lang"/>">

                                    <c:if test="${login == null}"> Login </c:if>${login} </a></li>

                                <li><a href="<c:url value="/register?lang&action=show"/>">
                                    <c:if test="${register == null}"> Sign in </c:if>${register} </a></li>
                            </c:if>
                            <li>
                            <c:if test="${keycustomer != null}">
                                <a href="<c:url value="/main?action=invalidate"/>">Log out</a>
                            </c:if>
                            </li>
                        
                            <li class="divider"></li>
                            <li>
                                <a href="?lang=en">English</a>
                                <a href="?lang=ru">Русский</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>