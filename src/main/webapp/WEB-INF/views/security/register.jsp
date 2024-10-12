<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="path" value="${pageContext.request.contextPath}"/>
<sec:authentication var="loginMember" property="principal"/>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="${path}/css/header.css?ver=<%= System.currentTimeMillis()%>">
    <link rel="stylesheet" type="text/css" href="${path}/css/register.css?ver=<%= System.currentTimeMillis()%>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<c:import url="${path}/WEB-INF/views/common/header.jsp"/>
<body style="margin: 0">

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
<h1>Register</h1>
<div id="login-form-div">
    <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" action="${path}/register" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="floatingInput" placeholder="Username"
                   name="username">
            <label for="floatingInput">Username</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                   name="password">
            <label for="floatingPassword">Password</label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" id="email" placeholder="email"
                   name="email">
            <label for="email">email</label>
        </div>

        <button class="w-100 btn btn-lg btn-dark" type="submit">Sign up</button>

        <hr class="my-3">
        <small class="text-body-secondary">By clicking Sign up, you agree to the terms of use.</small>
    </form>

</div>

</body>
</html>
