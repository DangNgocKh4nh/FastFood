<%@ page import="com.fastfood.model.Order" %>
<%@ page import="com.fastfood.model.OrderDetail" %>
<%@ page import="com.fastfood.model.Item" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Order order = (Order) request.getAttribute("order");
    String address = (String) request.getAttribute("address");

    double calculatedTotal = 0;
    for (OrderDetail detail : order.getOrderDetails()) {
        calculatedTotal += detail.getQuantity() * detail.getPrice();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn hàng đã đặt thành công</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 40px;
            background-color: #f4f6f8;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #28a745;
            font-size: 28px;
            margin-bottom: 20px;
        }
        h3 {
            color: #2c3e50;
            margin-top: 30px;
            margin-bottom: 15px;
        }
        .success-message {
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            background-color: #e6f4ea;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .customer-info, .order-details {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
        .customer-info p {
            margin: 10px 0;
            font-size: 16px;
        }
        .customer-info strong {
            color: #2c3e50;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }
        th {
            background-color: #2c3e50;
            color: white;
            font-weight: bold;
        }
        tr:last-child td {
            border-bottom: none;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .total-amount {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
            margin: 20px 0;
            color: #2c3e50;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        button {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .back-button {
            background-color: #007bff;
            color: white;
        }
        .back-button:hover {
            background-color: #0056b3;
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
            padding: 8px 16px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .logout-button:hover {
            background-color: #c82333;
        }
        .action-buttons .print {
            background-color: #2196F3;
            color: white;
        }
        .action-buttons .print:hover {
            background-color: #1976D2;
        }
        @media (max-width: 600px) {
            body {
                margin: 20px;
            }
            .container {
                padding: 0 10px;
            }
            .customer-info, .order-details {
                padding: 15px;
            }
            h2 {
                font-size: 24px;
            }
            .success-message {
                font-size: 18px;
                padding: 10px;
            }
            table {
                font-size: 14px;
            }
            th, td {
                padding: 8px;
            }
            .total-amount {
                font-size: 16px;
            }
            button {
                padding: 10px 20px;
                font-size: 14px;
            }
            .logout-container {
                position: static;
                text-align: center;
                margin-bottom: 20px;
            }
        }
    </style>
    <script>
        function printInvoice() {
            window.print();
        }
    </script>
</head>
<body>
<div class="logout-container">
    <form action="login.jsp" method="get">
        <button type="submit" class="logout-button">Đăng xuất</button>
    </form>
</div>

<div class="container">
    <div class="success-message">🎉 Đặt hàng thành công!</div>
    <h2>Thông tin đơn hàng</h2>

    <div class="customer-info">
        <h3>Thông tin người nhận</h3>
        <p><strong>Tên:</strong> <%= order.getCustomer().getName() %></p>
        <p><strong>Số điện thoại:</strong> <%= order.getCustomer().getPhoneNumber() %></p>
        <p><strong>Địa chỉ:</strong> <%= address %></p>
    </div>

    <div class="order-details">
        <h3>Chi tiết đơn hàng</h3>
        <table>
            <tr>
                <th>Tên món</th>
                <th>Số lượng</th>
                <th>Giá (VNĐ)</th>
                <th>Thành tiền (VNĐ)</th>
            </tr>
            <% for (OrderDetail detail : order.getOrderDetails()) {
                Item item = detail.getItem();
                double lineTotal = detail.getQuantity() * detail.getPrice();
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= detail.getQuantity() %></td>
                <td><%= String.format("%.0f", detail.getPrice()) %></td>
                <td><%= String.format("%.0f", lineTotal) %></td>
            </tr>
            <% } %>
        </table>

        <div class="total-amount">
            <strong>Tổng cộng:</strong> <%= String.format("%.0f", calculatedTotal) %> VNĐ
        </div>
    </div>

    <div class="action-buttons">
        <button class="print" onclick="printInvoice()">In hóa đơn</button>
        <a href="Order.jsp"><button class="back-button">Quay về menu</button></a>
    </div>
</div>
</body>
</html>