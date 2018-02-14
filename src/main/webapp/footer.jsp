<%@ taglib uri="/WEB-INF/customtags/currectDate.tld" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<footer style="position: relative; float: left; margin-top: 10%">
    <div class="container">
        <div class="row">
            <p><c:if test="${currentdate == null}">Current date is</c:if>${currentdate} <custom:date/>.</p>
            <custom:ifWeekday>
                <p><c:if test="${support == null}">Live support available at 1-800-555-2222</c:if> ${support}</p>
            </custom:ifWeekday>
        </div>
    </div>
</footer>

