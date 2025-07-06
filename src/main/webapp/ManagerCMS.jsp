<%@ page import="com.fastfood.model.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fastfood.model.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager CMS</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            position: fixed;
            height: 100%;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
            /*justify-content: space-between;*/
        }
        .sidebar h3 {
            padding: 15px 20px;
            margin: 0;
            font-size: 1.2em;
            background-color: #34495e;
        }
        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar ul li {
            padding: 15px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .sidebar ul li:hover {
            background-color: #34495e;
        }
        .sidebar ul li.active {
            background-color: #3498db;
        }
        .sidebar .menu-buttons {
            padding-top: 20px;
            border-top: 1px solid #34495e;
        }
        .sidebar .menu-buttons button {
            display: block;
            width: 100%;
            padding: 10px 20px;
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 10px;
            transition: background-color 0.3s;
        }
        .sidebar .menu-buttons button:hover {
            background-color: #c0392b;
        }
        .sidebar .menu-buttons .back-button {
            background-color: #007bff;
        }
        .sidebar .menu-buttons .back-button:hover {
            background-color: #0056b3;
        }
        .content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
            overflow-y: auto;
        }
        .form-container, .order-form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            display: none;
        }
        .form-container.active, .order-form-container.active {
            display: block;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #34495e;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group input[type="file"] {
            padding: 5px 0;
        }
        .form-group button {
            padding: 10px 20px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-group button:hover {
            background-color: #27ae60;
        }
        .table-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        th {
            background-color: #3498db;
            color: white;
        }
        td button {
            padding: 5px 10px;
            margin-right: 5px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        td .edit-btn {
            background-color: #f1c40f;
            color: white;
        }
        td .edit-btn:hover {
            background-color: #e0b11c;
        }
        td .delete-btn {
            background-color: #e74c3c;
            color: white;
        }
        td .delete-btn:hover {
            background-color: #c0392b;
        }
        td .update-btn {
            background-color: #9b59b6;
            color: white;
        }
        td .update-btn:hover {
            background-color: #8e44ad;
        }
        img {
            max-width: 50px;
            max-height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }
        @media (max-width: 768px) {
            .sidebar {
                width: 200px;
            }
            .content {
                margin-left: 200px;
                width: calc(100% - 200px);
            }
            .form-container, .order-form-container, .table-container {
                padding: 10px;
            }
            th, td {
                padding: 8px;
                font-size: 14px;
            }
            img {
                max-width: 30px;
                max-height: 30px;
            }
        }
    </style>
    <script>
        function editItem(id, name, price, image) {
            document.getElementById('itemId').value = id;
            document.getElementById('itemName').value = name;
            document.getElementById('itemPrice').value = price;
            document.getElementById('itemImage').value = ''; // Reset file input
            document.getElementById('action').value = 'update';
            document.getElementById('submitBtn').textContent = 'Cập nhật';
        }

        function resetForm() {
            document.getElementById('itemId').value = '';
            document.getElementById('itemName').value = '';
            document.getElementById('itemPrice').value = '';
            document.getElementById('itemImage').value = ''; // Reset file input
            document.getElementById('action').value = 'add';
            document.getElementById('submitBtn').textContent = 'Thêm mới';
        }

        function confirmDelete(id) {
            if (confirm('Bạn có chắc muốn xóa item này?')) {
                window.location.href = 'ItemServlet?action=delete&id=' + id;
            }
        }

        function updateOrder(orderId) {
            var paymentMethod = document.getElementById('paymentMethod_' + orderId).value;
            var state = document.getElementById('state_' + orderId).value;

            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'ItemServlet?action=updateOrder&orderId=' + orderId + '&paymentMethod=' + paymentMethod + '&state=' + state, true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    alert('Cập nhật đơn hàng thành công!');
                } else {
                    alert('Cập nhật đơn hàng thất bại!');
                }
            };
            xhr.send();
        }

        document.addEventListener('DOMContentLoaded', function() {
            const menuItems = document.querySelectorAll('.sidebar ul li');
            const contents = document.querySelectorAll('.content > div');

            menuItems.forEach(item => {
                item.addEventListener('click', function() {
                    menuItems.forEach(i => i.classList.remove('active'));
                    this.classList.add('active');
                    contents.forEach(content => content.classList.remove('active'));
                    const targetId = this.getAttribute('data-target');
                    document.getElementById(targetId).classList.add('active');
                });
            });

            if (menuItems.length > 0) {
                menuItems[0].click();
            }
        });
    </script>
</head>
<body>
<div class="sidebar">
    <h3>Menu</h3>
    <ul>
        <li data-target="itemSection">Quản lý món ăn</li>
        <li data-target="orderSection">Quản lý đơn hàng</li>
    </ul>
    <div class="menu-buttons">
        <form action="ManagerDashboard.jsp">
            <button type="submit" class="back-button">Quay lại</button>
        </form>
        <form action="login.jsp" method="get">
            <button type="submit" class="logout-button">Đăng Xuất</button>
        </form>
    </div>
</div>
<div class="content">
    <div id="itemSection" class="form-container active">
        <h2>Quản lý món ăn</h2>
        <form action="ItemServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" id="itemId" name="id">
            <input type="hidden" id="action" name="action" value="add">
            <div class="form-group">
                <label for="itemName">Tên món ăn:</label>
                <input type="text" id="itemName" name="name" required>
            </div>
            <div class="form-group">
                <label for="itemPrice">Giá:</label>
                <input type="number" id="itemPrice" name="price" step="1000" required>
            </div>
            <div class="form-group">
                <label for="itemImage">Ảnh:</label>
                <input type="file" id="itemImage" name="imageFile" accept="image/*">
            </div>
            <div class="form-group">
                <button type="submit" id="submitBtn">Thêm mới</button>
                <button type="button" onclick="resetForm()">Làm mới</button>
            </div>
        </form>
        <div class="table-container">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Tên món</th>
                    <th>Giá</th>
                    <th>Hình ảnh</th>
                    <th>Hành động</th>
                </tr>
                <%
                    List<Item> items = (List<Item>) request.getAttribute("items");
                    if (items != null && !items.isEmpty()) {
                        for (Item item : items) {
                %>
                <tr>
                    <td><%= item.getIdItem() %></td>
                    <td><%= item.getName() %></td>
                    <td><%= String.format("%.0f", item.getPrice()) %> VNĐ</td>
                    <td>
                        <img src="<%= request.getContextPath() + (item.getImage() != null ? item.getImage() : "/images/default.jpg") %>"
                             alt="<%= item.getName() %>"
                             onerror="this.src='<%= request.getContextPath() + "/images/default.jpg" %>';">
                    </td>
                    <td>
                        <button class="edit-btn"
                                onclick="editItem('<%= item.getIdItem() %>', '<%= item.getName() %>', '<%= item.getPrice() %>', '<%= item.getImage() %>')">
                            Sửa
                        </button>
                        <button class="delete-btn" onclick="confirmDelete('<%= item.getIdItem() %>')">Xóa</button>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5">Không có item nào. Vui lòng thêm item mới hoặc kiểm tra kết nối cơ sở dữ liệu.</td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>

    <div id="orderSection" class="order-form-container">
        <h2>Quản lý đơn hàng</h2>
        <div class="table-container">
            <table>
                <tr>
                    <th>ID Đơn Hàng</th>
                    <th>Ngày đặt hàng</th>
                    <th>Địa chỉ</th>
                    <th>Phương Thức Thanh Toán</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
                <%
                    List<Order> orders = (List<Order>) request.getAttribute("orders");
                    if (orders != null && !orders.isEmpty()) {
                        for (Order order : orders) {
                %>
                <tr>
                    <td><%= order.getIdOrder() %></td>
                    <td><%= order.getCreateDate() %></td>
                    <td><%= order.getAddress() %></td>
                    <td>
                        <select id="paymentMethod_<%= order.getIdOrder() %>" name="paymentMethod">
                            <option value="COD" <%= "COD".equals(order.getPaymentMethod()) ? "selected" : "" %>>COD</option>
                            <option value="Momo" <%= "Momo".equals(order.getPaymentMethod()) ? "selected" : "" %>>Momo</option>
                            <option value="ZaloPay" <%= "ZaloPay".equals(order.getPaymentMethod()) ? "selected" : "" %>>ZaloPay</option>
                            <option value="InternetBanking" <%= "InternetBanking".equals(order.getPaymentMethod()) ? "selected" : "" %>>Internet Banking</option>
                        </select>
                    </td>
                    <td>
                        <select id="state_<%= order.getIdOrder() %>" name="state">
                            <option value="Preparing" <%= "Preparing".equals(order.getState()) ? "selected" : "" %>>Đang chuẩn bị</option>
                            <option value="Delivering" <%= "Delivering".equals(order.getState()) ? "selected" : "" %>>Đang giao hàng</option>
                            <option value="Delivered" <%= "Delivered".equals(order.getState()) ? "selected" : "" %>>Đã giao hàng</option>
                        </select>
                    </td>
                    <td>
                        <button class="update-btn" onclick="updateOrder('<%= order.getIdOrder() %>')">Cập nhật</button>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="4">Không có đơn hàng nào. Vui lòng kiểm tra kết nối cơ sở dữ liệu.</td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>
</body>
</html>