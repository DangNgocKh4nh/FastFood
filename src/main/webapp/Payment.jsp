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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán</title>
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
        h2, h3 {
            text-align: center;
            color: #2c3e50;
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
        .address-container {
            margin-bottom: 20px;
        }
        .address-container label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
            color: #2c3e50;
        }
        .address-container textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 16px;
            resize: vertical;
            transition: border-color 0.3s;
        }
        .address-container textarea:focus {
            border-color: #28a745;
            outline: none;
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
        .quantity-input {
            width: 60px;
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            text-align: center;
            transition: border-color 0.3s;
        }
        .quantity-input:focus {
            border-color: #28a745;
            outline: none;
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
            gap: 20px;
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
        .confirm-button {
            background-color: #28a745;
            color: white;
        }
        .confirm-button:hover {
            background-color: #218838;
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
        .error-message {
            text-align: center;
            color: #dc3545;
            font-weight: bold;
            margin: 10px 0;
            background-color: #ffe6e6;
            padding: 10px;
            border-radius: 6px;
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
            table {
                font-size: 14px;
            }
            th, td {
                padding: 8px;
            }
            .quantity-input {
                width: 50px;
                font-size: 12px;
            }
            .action-buttons {
                flex-direction: column;
                gap: 10px;
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
<div class="logout-container">
    <form action="login.jsp" method="get">
        <button type="submit" class="logout-button">Đăng xuất</button>
    </form>
</div>

<div class="container">
    <h2>Thanh toán</h2>

    <div class="customer-info">
        <h3>Thông tin người nhận</h3>
        <p><strong>Tên:</strong> <%= name %></p>
        <p><strong>Số điện thoại:</strong> <%= phone %></p>
    </div>

    <form action="ConfirmOrderServlet" method="post" class="order-details">
        <div class="address-container">
            <label for="address">Địa chỉ giao hàng:</label>
            <textarea id="address" name="address" rows="3" required></textarea>
        </div>

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

        <div class="total-amount">
            <strong>Tổng thanh toán:</strong> <span id="totalAmount"><%= String.format("%.0f", total) %> VNĐ</span>
        </div>

        <input type="hidden" name="detailCount" value="<%= orderDetails.size() %>">

        <%
            String error = request.getParameter("error");
            if ("missingAddress".equals(error)) {
        %>
        <div class="error-message">Vui lòng nhập địa chỉ giao hàng.</div>
        <%
        } else if ("saveFail".equals(error)) {
        %>
        <div class="error-message">Có lỗi xảy ra khi lưu đơn hàng. Vui lòng thử lại sau.</div>
        <%
            }
        %>

        <div class="action-buttons">
            <button type="submit" class="confirm-button">Xác nhận</button>
            <a href="Order.jsp"><button type="button" class="back-button">Quay lại</button></a>
        </div>
    </form>
</div>
</body>
</html>