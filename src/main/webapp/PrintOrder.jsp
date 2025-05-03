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

<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng đã đặt thành công</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #28a745; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f8f9fa; }
        button { padding: 8px 16px; background-color: #007bff; color: #fff; border: none; border-radius: 4px; }
        button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<div style="position: absolute; top: 10px; right: 20px;">
    <form action="login.jsp" method="get" style="display:inline;">
        <button type="submit" style="
            background-color: #f44336;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
        ">
            Đăng xuất
        </button>
    </form>
</div>

<h2>🎉 Đặt hàng thành công!</h2>

<h3>Thông tin người nhận</h3>
<p><strong>Tên:</strong> <%= order.getCustomer().getName() %></p>
<p><strong>Số điện thoại:</strong> <%= order.getCustomer().getPhoneNumber() %></p>
<p><strong>Địa chỉ:</strong> <%= address %></p>

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

<p><strong>Tổng cộng:</strong> <%= String.format("%.0f", calculatedTotal) %> VNĐ</p>

<a href="Order.jsp"><button>Quay về menu</button></a>
</body>
</html>
