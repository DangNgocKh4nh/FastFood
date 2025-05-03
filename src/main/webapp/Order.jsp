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
    <title>Order Page</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f8f9fa; }
        .search-container { margin-bottom: 20px; }
        button { padding: 6px 12px; background-color: #28a745; color: #fff; border: none; border-radius: 4px; }
        button:hover { background-color: #218838; }
        .cancel-button { background-color: #dc3545; }
        .cancel-button:hover { background-color: #c82333; }
    </style><div style="position: absolute; top: 10px; right: 20px;">
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

</head>
<body>

<h2>Trang đặt món</h2>

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
<h3>Kết quả tìm kiếm:</h3>
<table>
    <tr>
        <th>Tên</th>
        <th>Giá</th>
        <th>Chọn</th>
    </tr>
    <% for (Item item : resultItems) { %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %> VNĐ</td>
        <td>
            <form action="OrderServlet" method="post">
                <input type="hidden" name="addItemId" value="<%= item.getIdItem() %>">
                <input type="hidden" name="keyword" value="<%= keyword %>">
                <button type="submit">Chọn</button>
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
<h3>Món đã chọn:</h3>
<table>
    <tr>
        <th>Tên</th>
        <th>Giá</th>
        <th>Hủy</th>
    </tr>
    <% for (Item item : selectedItems) { %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getPrice() %> VNĐ</td>
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
<br>
<form action="OrderServlet" method="post">
    <input type="hidden" name="action" value="checkout" />
    <button type="submit">Thanh toán</button>
</form>
<% } %>

</body>
</html>
