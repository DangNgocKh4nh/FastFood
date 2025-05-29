<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bảng Điều Khiển Quản Lý</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding: 40px 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        h1 {
            color: #2c3e50;
            font-size: 2.5em;
            margin-bottom: 40px;
            text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .dashboard-container {
            display: flex;
            gap: 30px;
            flex-wrap: wrap;
            justify-content: center;
            max-width: 1200px;
            width: 100%;
        }
        .dashboard-item {
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 250px;
            transition: all 0.3s ease;
            border: 1px solid #e9ecef;
        }
        .dashboard-item:hover {
            transform: scale(1.05);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            background: linear-gradient(135deg, #ffffff 0%, #f0f2f5 100%);
        }
        .dashboard-item a {
            text-decoration: none;
            color: #34495e;
            font-size: 1.2em;
            font-weight: 600;
            display: block;
            padding: 10px 0;
            transition: color 0.3s ease;
        }
        .dashboard-item a:hover {
            color: #2196F3;
        }
        .logout-button {
            margin-top: 40px;
            padding: 12px 25px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            box-shadow: 0 4px 8px rgba(244, 67, 54, 0.2);
        }
        .logout-button:hover {
            background-color: #d32f2f;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(244, 67, 54, 0.3);
        }
        @media (max-width: 768px) {
            h1 {
                font-size: 2em;
            }
            .dashboard-container {
                gap: 15px;
            }
            .dashboard-item {
                width: 200px;
                padding: 20px;
            }
            .dashboard-item a {
                font-size: 1em;
            }
            .logout-button {
                padding: 10px 20px;
                font-size: 14px;
            }
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