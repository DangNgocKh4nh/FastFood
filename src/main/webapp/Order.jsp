<%@ page import="java.util.*, com.fastfood.model.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String keyword = request.getParameter("keyword");
    if (keyword == null) {
        keyword = (String) session.getAttribute("lastKeyword");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang đặt món</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 40px;
            background-color: #f4f6f8;
            color: #333;
        }
        h2, h3 {
            text-align: center;
            color: #2c3e50;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        .search-container {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .search-container form {
            display: flex;
            width: 100%;
            max-width: 500px;
        }
        .search-container input[type="text"] {
            flex: 1;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px 0 0 8px;
            font-size: 16px;
            outline: none;
            transition: border-color 0.3s;
        }
        .search-container input[type="text"]:focus {
            border-color: #28a745;
        }
        .search-container button {
            padding: 12px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 0 8px 8px 0;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .search-container button:hover {
            background-color: #218838;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            margin: 20px 0;
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
        button {
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        button.select-button {
            background-color: #28a745;
            color: white;
        }
        button.select-button:hover {
            background-color: #218838;
        }
        button.cancel-button {
            background-color: #dc3545;
            color: white;
        }
        button.cancel-button:hover {
            background-color: #c82333;
        }
        button.checkout-button {
            background-color: #007bff;
            color: white;
            padding: 12px 24px;
            font-size: 16px;
            margin: 20px auto;
            display: block;
        }
        button.checkout-button:hover {
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
        img {
            max-width: 100px;
            max-height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }
        @media (max-width: 600px) {
            body {
                margin: 20px;
            }
            .search-container form {
                flex-direction: column;
                max-width: 100%;
            }
            .search-container input[type="text"] {
                border-radius: 8px;
                margin-bottom: 10px;
            }
            .search-container button {
                border-radius: 8px;
            }
            table {
                font-size: 14px;
            }
            th, td {
                padding: 8px;
            }
            button {
                padding: 6px 12px;
                font-size: 12px;
            }
            img {
                max-width: 50px;
                max-height: 50px;
            }
            .logout-container {
                position: static;
                text-align: center;
                margin-bottom: 20px;
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

<div class="container">
    <h2>Trang Đặt Món</h2>

    <div class="search-container">
        <form action="OrderServlet" method="get">
            <input type="text" name="keyword" placeholder="Nhập tên món ăn" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit">Tìm kiếm</button>
        </form>
    </div>

    <%
        List<Item> resultItems = (List<Item>) request.getAttribute("searchResults");
        if (resultItems != null && !resultItems.isEmpty()) {
    %>
    <h3>Kết quả tìm kiếm</h3>
    <table>
        <tr>
            <th>Hình ảnh</th>
            <th>Tên</th>
            <th>Giá</th>
            <th>Chọn</th>
        </tr>
        <% for (Item item : resultItems) { %>
        <tr>
            <td>
                <%
                    String imagePath = item.getImage() != null ? item.getImage() : "/images/default.jpg";
                %>
                <img src="<%= request.getContextPath() + imagePath %>" alt="<%= item.getName() %>" onerror="this.src='<%= request.getContextPath() + "/images/default.jpg" %>';">
            </td>
            <td><%= item.getName() %></td>
            <td><%= String.format("%.0f", item.getPrice()) %> VNĐ</td>
            <td>
                <form action="OrderServlet" method="post">
                    <input type="hidden" name="addItemId" value="<%= item.getIdItem() %>">
                    <input type="hidden" name="keyword" value="<%= keyword %>">
                    <button type="submit" class="select-button">Chọn</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>

    <%
        List<Item> selectedItems = (List<Item>) session.getAttribute("selectedItems");
        if (selectedItems != null && !selectedItems.isEmpty()) {
    %>
    <h3>Món đã chọn</h3>
    <table>
        <tr>
            <th>Hình ảnh</th>
            <th>Tên</th>
            <th>Giá</th>
            <th>Hủy</th>
        </tr>
        <% for (Item item : selectedItems) { %>
        <tr>
            <td>
                <%
                    String imagePath = item.getImage() != null ? item.getImage() : "/images/default.jpg";
                %>
                <img src="<%= request.getContextPath() + imagePath %>" alt="<%= item.getName() %>" onerror="this.src='<%= request.getContextPath() + "/images/default.jpg" %>';">
            </td>
            <td><%= item.getName() %></td>
            <td><%= String.format("%.0f", item.getPrice()) %> VNĐ</td>
            <td>
                <form action="OrderServlet" method="post">
                    <input type="hidden" name="removeItemId" value="<%= item.getIdItem() %>">
                    <input type="hidden" name="keyword" value="<%= keyword %>">
                    <button type="submit" class="cancel-button">Hủy</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <form action="OrderServlet" method="post">
        <input type="hidden" name="action" value="checkout" />
        <button type="submit" class="checkout-button">Thanh toán</button>
    </form>
    <% } %>
</div>

</body>
</html>