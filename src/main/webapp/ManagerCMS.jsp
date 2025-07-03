<%@ page import="com.fastfood.model.Item" %>
<%@ page import="java.util.List" %>
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
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }
        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
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
        .logout-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .logout-button {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .logout-button:hover {
            background-color: #c0392b;
        }
        .back-container {
            position: absolute;
            top: 20px;
            left: 20px;
        }
        .back-button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
        img {
            max-width: 50px;
            max-height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }
        @media (max-width: 600px) {
            .form-container, .table-container {
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
    </script>
</head>
<body>
<div class="logout-container">
    <form action="login.jsp" method="get">
        <button type="submit" class="logout-button">Đăng Xuất</button>
    </form>
</div>
<div class="back-container">
    <form action="ManagerDashboard.jsp">
        <button type="submit" class="back-button">Quay lại</button>
    </form>
</div>
<div class="container">
    <h2>Quản lý món ăn</h2>

    <div class="form-container">
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
                <input type="file" id="itemImage" name="imageFile" accept="image/*" required>
            </div>
            <div class="form-group">
                <button type="submit" id="submitBtn">Thêm mới</button>
                <button type="button" onclick="resetForm()">Làm mới</button>
            </div>
        </form>
    </div>

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

</body>
</html>