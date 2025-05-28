<%@ page import="com.fastfood.model.Order" %>
<%@ page import="com.fastfood.model.OrderDetail" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh Sách Đơn Hàng Của Khách Hàng</title>
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
        .order-container {
            max-width: 1200px;
            width: 90%;
            margin: 20px auto;
        }
        .filter-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        .filter-form label {
            font-weight: 500;
            color: #34495e;
            margin-right: 10px;
        }
        .filter-form input[type="date"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
        }
        .filter-form button {
            padding: 10px 20px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 4px rgba(33, 150, 243, 0.2);
        }
        .filter-form button:hover {
            background-color: #1976D2;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(33, 150, 243, 0.3);
        }
        .order-item {
            background-color: #fff;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .order-item:hover {
            transform: translateY(-5px);
        }
        .order-item h3 {
            margin: 0 0 15px 0;
            color: #34495e;
            font-size: 1.5em;
            border-bottom: 2px solid #3498db;
            padding-bottom: 5px;
        }
        .order-item p {
            margin: 8px 0;
            color: #7f8c8d;
            font-size: 1.1em;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background-color: #f8f9fa;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: linear-gradient(90deg, #2ecc71 0%, #27ae60 100%);
            color: #fff;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        tr:nth-child(even) {
            background-color: #fff;
        }
        tr:hover {
            background-color: #e9ecef;
            transition: background-color 0.3s ease;
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
        .no-orders {
            text-align: center;
            color: #7f8c8d;
            margin-top: 20px;
            font-size: 1.2em;
        }
        @media (max-width: 768px) {
            .order-container {
                width: 100%;
            }
            h1 {
                font-size: 2em;
            }
            .order-item {
                padding: 15px;
            }
            th, td {
                padding: 8px;
                font-size: 14px;
            }
            .filter-form {
                flex-direction: column;
                align-items: stretch;
            }
            .filter-form label {
                margin-right: 0;
                margin-bottom: 5px;
            }
            .action-buttons button {
                padding: 10px 20px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<h1>Danh Sách Đơn Hàng Của Khách Hàng (Mã KH: <%= request.getAttribute("idCustomer") %>)</h1>
<div class="order-container">
    <form action="CustomerOrderListServlet" method="get" class="filter-form">
        <input type="hidden" name="idCustomer" value="<%= request.getAttribute("idCustomer") %>">
        <label for="startDate">Ngày bắt đầu:</label>
        <input type="date" id="startDate" name="startDate" value="">
        <label for="endDate">Ngày kết thúc:</label>
        <input type="date" id="endDate" name="endDate" value="">
        <button type="submit">Xem</button>
    </form>
    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
    %>
    <div class="order-item">
        <h3>Đơn Hàng #<%= order.getIdOrder() %></h3>
        <p><strong>Ngày đặt:</strong> <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(order.getCreateDate()) %></p>
        <p><strong>Địa chỉ:</strong> <%= order.getAddress() %></p>
        <table>
            <tr>
                <th>Tên món</th>
                <th>Số lượng</th>
                <th>Giá (VNĐ)</th>
                <th>Thành tiền (VNĐ)</th>
            </tr>
            <%
                double orderTotal = 0;
                for (OrderDetail detail : order.getOrderDetails()) {
                    double lineTotal = detail.getPrice() * detail.getQuantity();
                    orderTotal += lineTotal;
            %>
            <tr>
                <td><%= detail.getItem().getName() %></td>
                <td><%= detail.getQuantity() %></td>
                <td><%= String.format("%,.0f", detail.getPrice()) %></td>
                <td><%= String.format("%,.0f", lineTotal) %></td>
            </tr>
            <% } %>
            <tr>
                <td colspan="3" style="text-align: right; font-weight: bold;">Tổng cộng:</td>
                <td><%= String.format("%,.0f", orderTotal) %></td>
            </tr>
        </table>
    </div>
    <% }
    } else { %>
    <p class="no-orders">Khách hàng này chưa có đơn hàng nào.</p>
    <% } %>
</div>
<div class="action-buttons">
    <a href="CustomerStatsServlet"><button class="back">Quay lại</button></a>
</div>
</body>
</html>