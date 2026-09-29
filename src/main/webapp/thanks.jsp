<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP - Chapter 14</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <div class="page">
        <h1>Thanks for joining our email list</h1>
        <p>Here is the information that you entered:</p>

        <div class="row">
            <label>Email:</label>
            <span><c:out value="${user.email}"/></span>
        </div>
        <div class="row">
            <label>First Name:</label>
            <span><c:out value="${user.firstName}"/></span>
        </div>
        <div class="row">
            <label>Last Name:</label>
            <span><c:out value="${user.lastName}"/></span>
        </div>

        <c:if test="${not empty mailStatus}">
            <p><strong><c:out value="${mailStatus}"/></strong></p>
        </c:if>
        <c:if test="${not empty mailError}">
            <p><strong><c:out value="${mailError}"/></strong></p>
        </c:if>

        <p>To enter another email address, click on the Return button.</p>

        <form action="emailList" method="get">
            <input type="hidden" name="action" value="join">
            <input type="submit" value="Return">
        </form>
    </div>
</body>
</html>
