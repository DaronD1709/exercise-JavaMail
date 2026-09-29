<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <div class="page">
        <h1>Join our email list</h1>
        <p>To join our email list, enter your name and email address below.</p>
        <p><i>${message}</i></p>

        <form action="emailList" method="post">
            <input type="hidden" name="action" value="add">

            <div class="row">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<c:out value='${user.email}'/>" required>
            </div>
            <div class="row">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" value="<c:out value='${user.firstName}'/>" required>
            </div>
            <div class="row">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" value="<c:out value='${user.lastName}'/>" required>
            </div>

            <input type="submit" value="Join Now">
        </form>
    </div>
</body>
</html>
