<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bảng Điều Khiển Quản Lý</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f4f6f8;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        h1 {
            color: #333;
            margin-bottom: 40px;
        }
        .dashboard-container {
            display: flex;
            gap: 30px;
        }
        .dashboard-item {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            text-align: center;
            width: 200px;
            transition: transform 0.2s;
        }
        .dashboard-item:hover {
            transform: scale(1.05);
        }
        .dashboard-item a {
            text-decoration: none;
            color: #333;
            font-size: 18px;
            font-weight: bold;
        }
        .dashboard-item a:hover {
            color: #2196F3;
        }
        .logout-button {
            margin-top: 40px;
            padding: 10px 20px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .logout-button:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<%
    if (session.getAttribute("manager") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h1>Bảng Điều Khiển Quản Lý</h1>
<div class="dashboard-container">
    <div class="dashboard-item">
        <a href="SelectSupplier.jsp">Nhập Nguyên Liệu</a>
    </div>
    <div class="dashboard-item">
        <a href="CustomerStatsServlet">Thống Kê Khách Hàng</a>
    </div>
</div>
<form action="login.jsp" method="get" style="margin-top: 20px;">
    <button type="submit" class="logout-button">Đăng Xuất</button>
</form>
</body>
</html>