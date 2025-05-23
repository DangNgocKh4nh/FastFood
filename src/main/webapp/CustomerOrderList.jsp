<%@ page import="com.fastfood.model.Order" %>
<%@ page import="com.fastfood.model.OrderDetail" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh Sách Đơn Hàng Của Khách Hàng</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h1 { text-align: center; color: #333; }
        .order-container { max-width: 1000px; margin: 20px auto; }
        .order-item { background-color: #f9f9f9; padding: 15px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .order-item h3 { margin: 0 0 10px 0; color: #555; }
        .order-item p { margin: 5px 0; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        tr:nth-child(even) { background-color: #fff; }
        .action-buttons { display: flex; justify-content: center; gap: 20px; margin-top: 20px; }
        .action-buttons button { padding: 10px 20px; border: none; border-radius: 6px; cursor: pointer; font-size: 16px; }
        .action-buttons .back { background-color: #888; color: white; }
        .action-buttons .back:hover { background-color: #666; }
        .no-orders { text-align: center; color: #888; margin-top: 20px; }
    </style>
</head>
<body>
<h1>Danh Sách Đơn Hàng Của Khách Hàng (Mã KH: <%= request.getAttribute("idCustomer") %>)</h1>
<div class="order-container">
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