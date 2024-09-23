<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <c:set var="path" value="${pageContext.request.contextPath}"/>
</head>
<body>
<c:set var="path" value="${pageContext.request.contextPath}"/>

    <label for="username">Username:</label>
    <input type="text" id="username">
    <br>
    <label for="password">Password:</label>
    <input type="password" id="password">
    <br>
<div style="display: flex">
    <button id="loginBtn" onclick="login()">Login</button>
    <button id="logoutBtn" onclick="logout()">Logout</button>
    <button id="registerBtn" onclick="register()">Register</button>
</div>
</body>
<script>
    function login() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        fetch('${path}/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-CSRF-TOKEN': '${_csrf.token}'
            },
            body: new URLSearchParams({
                username: username,
                password: password
            })
        }).then(response => {
            if (response.ok) {
                window.location.href = '${path}/';
            } else {
                alert('Login failed');
            }
        });
    }

    function logout() {
        fetch('${path}/logout', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': '${_csrf.token}'
            }
        }).then(() => {
            window.location.href = '${path}/login';
        });
    }

    function register() {
        window.location.href = '${path}/register';
    }
</script>
</html>
