<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cell-phone</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<body style="background-color: #cecaff">

<jsp:include page="/header.jsp" />

<div id="wrapper">
    <div id="articles">
<span id="span_big">
<form method="">
    <table class="table table-hover">
    <thead>
    <tr>
        <th>Picture</th>
        <th>Description</th>
        <th>Price</th>
        <th>Selection</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td> <img src="<c:url value="/pictures/phone.jpg"/>" alt=""> </td>
        <td>Unlimited calls within Ukraine</td>
        <td>35</td>
        <td><input type="radio" id="contactChoice3"
                   name="contact" value="email">
                            <label for="contactChoice3">Select</label></td></td>
    </tr>

    <tr>
        <td> <img src="<c:url value="/pictures/phone.jpg"/>" alt=""> </td>
        <td>Unlimited calls within Ukraine + sms</td>
        <td>55</td>
        <td><input type="radio" id="contactChoice2"
                   name="contact" value="email">
                            <label for="contactChoice2">Select</label></td>
    </tr>

    <tr>
        <td> <img src="<c:url value="/pictures/phone.jpg"/>" alt=""> </td>
        <td>Unlimited calls within Ukraine + sms + 50 minutes abroad </td>
        <td>75</td>
        <td>
                            <input type="radio" id="contactChoice1"
                                   name="contact" value="email">
                            <label for="contactChoice1">Select</label>
                            </td>

    </tr>

    </tbody>
</table>
    <input type="button" value="Buy">
    </form>
</span>
    </div>
</div>
</body>
<jsp:include page="/footer.jsp" />
</html>