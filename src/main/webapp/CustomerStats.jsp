<%@ page import="com.fastfood.model.CustomerRevenueStats" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thống Kê Khách Hàng Theo Doanh Thu</title>
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
        }
        h1 {
            color: #2c3e50;
            font-size: 2.5em;
            margin-bottom: 30px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        th {
            background: linear-gradient(90deg, #3498db 0%, #2980b9 100%);
            color: #fff;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #e9ecef;
            transition: background-color 0.3s ease;
        }
        .logout-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .logout-button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 4px rgba(220, 53, 69, 0.2);
        }
        .logout-button:hover {
            background-color: #c82333;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(220, 53, 69, 0.3);
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }
        .action-buttons button {
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .action-buttons .back {
            background-color: #6c757d;
            color: white;
        }
        .action-buttons .back:hover {
            background-color: #5a6268;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
        }
        .view-button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 4px rgba(33, 150, 243, 0.2);
        }
        .view-button:hover {
            background-color: #1976D2;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(33, 150, 243, 0.3);
        }
        @media (max-width: 768px) {
            table {
                width: 100%;
                font-size: 14px;
            }
            th, td {
                padding: 10px;
            }
            h1 {
                font-size: 2em;
            }
            .action-buttons button {
                padding: 10px 20px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="logout-container">
    <form action="login.jsp" method="get">
        <button type="submit" class="logout-button">Đăng xuất</button>
    </form>
</div>
<h1>Thống Kê Khách Hàng Theo Doanh Thu</h1>
<table>
    <tr>
        <th>Mã KH</th>
        <th>Tên</th>
        <th>Số điện thoại</th>
        <th>Email</th>
        <th>Doanh thu (VNĐ)</th>
        <th>Hành động</th>
    </tr>
    <%
        List<CustomerRevenueStats> statsList = (List<CustomerRevenueStats>) request.getAttribute("statsList");
        if (statsList != null) {
            for (CustomerRevenueStats stats : statsList) {
    %>
    <tr>
        <td><%= stats.getIdCustomer() %></td>
        <td><%= stats.getName() %></td>
        <td><%= stats.getPhoneNumber() %></td>
        <td><%= stats.getEmail() %></td>
        <td><%= String.format("%,.0f", stats.getRevenue()) %></td>
        <td>
            <form action="CustomerOrderListServlet" method="get">
                <input type="hidden" name="idCustomer" value="<%= stats.getIdCustomer() %>">
                <button type="submit" class="view-button">Xem</button>
            </form>
        </td>
    </tr>
    <% }
    } else { %>
    <tr>
        <td colspan="6" style="text-align: center;">Không có dữ liệu thống kê.</td>
    </tr>
    <% } %>
</table>
<div class="action-buttons">
    <a href="ManagerDashboard.jsp"><button class="back">Quay lại</button></a>
</div>
</body>
</html>