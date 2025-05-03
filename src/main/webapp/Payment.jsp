<%@ page import="java.util.List" %>
<%@ page import="com.fastfood.model.OrderDetail" %>
<%@ page import="com.fastfood.model.Item" %>
<%@ page import="com.fastfood.model.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<OrderDetail> orderDetails = (List<OrderDetail>) session.getAttribute("orderDetails");
    if (orderDetails == null) orderDetails = new java.util.ArrayList<>();

    Customer customer = (Customer) session.getAttribute("customer");
    String name = customer != null ? customer.getName() : "Chưa đăng nhập";
    String phone = customer != null ? customer.getPhoneNumber() : "N/A";

    double total = 0;
    for (OrderDetail detail : orderDetails) {
        total += detail.getPrice() * detail.getQuantity();
    }
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f8f9fa; }
        textarea { width: 100%; }
        button { padding: 8px 16px; margin-top: 10px; background-color: #28a745; color: #fff; border: none; border-radius: 4px; }
        button:hover { background-color: #218838; }
        .back-button { background-color: #007bff; }
        .back-button:hover { background-color: #0056b3; }
        p.error { color: red; }
    </style>
    <script>
        function updateTotal() {
            let total = 0;
            const rows = document.querySelectorAll(".order-row");
            rows.forEach(row => {
                const price = parseFloat(row.dataset.price);
                const qtyInput = row.querySelector(".quantity-input");
                const quantity = parseInt(qtyInput.value);
                const lineTotal = price * quantity;
                row.querySelector(".line-total").innerText = lineTotal.toFixed(0) + " VNĐ";
                total += lineTotal;
            });
            document.getElementById("totalAmount").innerText = total.toFixed(0) + " VNĐ";
        }

        document.addEventListener("DOMContentLoaded", () => {
            const qtyInputs = document.querySelectorAll(".quantity-input");
            qtyInputs.forEach(input => {
                input.addEventListener("input", updateTotal);
            });
        });
    </script>
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

<h2>Thanh toán</h2>

<h3>Thông tin người nhận</h3>
<p><strong>Tên:</strong> <%= name %></p>
<p><strong>Số điện thoại:</strong> <%= phone %></p>

<form action="ConfirmOrderServlet" method="post">
    <label>Địa chỉ giao hàng:</label><br>
    <textarea name="address" rows="3" required></textarea>

    <h3>Thông tin đơn hàng</h3>
    <table>
        <tr>
            <th>Tên món</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
        </tr>
        <% for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail detail = orderDetails.get(i);
            Item item = detail.getItem();
        %>
        <tr class="order-row" data-price="<%= item.getPrice() %>">
            <td><%= item.getName() %></td>
            <td><%= String.format("%.0f", item.getPrice()) %> VNĐ</td>
            <td>
                <input type="number" class="quantity-input" name="quantity_<%= i %>" value="<%= detail.getQuantity() %>" min="1" />
                <input type="hidden" name="itemId_<%= i %>" value="<%= item.getIdItem() %>" />
            </td>
            <td class="line-total"><%= String.format("%.0f", item.getPrice() * detail.getQuantity()) %> VNĐ</td>
        </tr>
        <% } %>
    </table>

    <p><strong>Tổng thanh toán:</strong> <span id="totalAmount"><%= String.format("%.0f", total) %> VNĐ</span></p>

    <input type="hidden" name="detailCount" value="<%= orderDetails.size() %>">

    <button type="submit">Xác nhận</button>
    <a href="Order.jsp"><button type="button" class="back-button">Quay lại</button></a>
    <%
        String error = request.getParameter("error");
        if ("missingAddress".equals(error)) {
    %>
    <p class="error">Vui lòng nhập địa chỉ giao hàng.</p>
    <%
    } else if ("saveFail".equals(error)) {
    %>
    <p class="error">Có lỗi xảy ra khi lưu đơn hàng. Vui lòng thử lại sau.</p>
    <%
        }
    %>
</form>
</body>
</html>
