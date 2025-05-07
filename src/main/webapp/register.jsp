<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register Page</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .register-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            width: 300px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        p {
            text-align: center;
            color: red;
            margin-bottom: 10px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: none;
            border-radius: 5px;
            background-color: #2196F3;
            color: white;
            cursor: pointer;
            font-size: 14px;
        }
        button:hover {
            opacity: 0.9;
        }
    </style>
    <script>
        function validateForm() {
            var password = document.forms["registerForm"]["password"].value;
            var confirmPassword = document.forms["registerForm"]["confirmPassword"].value;
            if (password !== confirmPassword) {
                alert("Mật khẩu xác nhận không khớp!");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<div class="register-container">
    <h2>Đăng ký</h2>
    <% String error = request.getParameter("error"); %>
    <% if ("confirm".equals(error)) { %>
    <p>Mật khẩu xác nhận không khớp!</p>
    <% } else if ("exists".equals(error)) { %>
    <p>Tên người dùng đã tồn tại!</p>
    <% } %>
    <form name="registerForm" action="RegisterServlet" method="post" onsubmit="return validateForm()">
        <input type="text" name="username" placeholder="Tên đăng nhập" required>
        <input type="password" name="password" placeholder="Mật khẩu" required>
        <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
        <button type="submit">Đăng ký</button>
    </form>
    <a href="login.jsp"><button class="back">Quay lại</button></a>
</div>

</body>
</html>
