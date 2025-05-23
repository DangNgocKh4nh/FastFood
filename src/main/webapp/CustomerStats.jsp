<%@ page import="com.fastfood.model.CustomerRevenueStats" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thống Kê Khách Hàng Theo Doanh Thu</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h1 { text-align: center; color: #333; }
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .logout-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .logout-button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .logout-button:hover {
            background-color: #c82333;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .action-buttons button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }
        .action-buttons .back {
            background-color: #888;
            color: white;
        }
        .action-buttons .back:hover {
            background-color: #666;
        }
        .view-button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .view-button:hover {
            background-color: #1976D2;
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