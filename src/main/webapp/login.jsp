<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
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
        .login-container {
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
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            width: 45%;
            padding: 10px;
            margin: 10px 2%;
            border: none;
            border-radius: 5px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            font-size: 14px;
        }
        button[type="button"] {
            background-color: #2196F3;
        }
        button:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Đăng nhập</h2>
    <form action="LoginServlet" method="post">
        <input type="text" name="username" placeholder="Tên đăng nhập" required>
        <input type="password" name="password" placeholder="Mật khẩu" required>
        <div style="text-align:center;">
            <button type="submit" name="action" value="login">Đăng nhập</button>
            <button type="button" onclick="window.location.href='register.jsp'">Đăng ký</button>
        </div>
    </form>
</div>

</body>
</html>
