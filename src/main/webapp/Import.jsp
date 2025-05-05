<%@ page import="com.fastfood.model.Ingredient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nhập Nguyên Liệu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin:  Ascendancy;
            background-color: #f4f6f8;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .search-box {
            display: flex;
            justify-content: center;
            margin-bottom: 30px;
        }
        .search-box input[type="text"] {
            width: 300px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px 0 0 8px;
            outline: none;
        }
        .search-box button {
            padding: 10px 20px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 0 8px 8px 0;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .search-box button:hover {
            background-color: #45a049;
        }
        .results-container, .selected-container {
            max-width: 800px;
            margin: 0 auto 30px;
        }
        .result-item, .selected-item {
            background-color: white;
            padding: 15px 20px;
            margin-bottom: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .result-item strong, .selected-item strong {
            font-size: 18px;
            color: #333;
        }
        .result-item form button, .selected-item form button {
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .result-item form button {
            background-color: #2196F3;
            color: white;
        }
        .result-item form button:hover {
            background-color: #1976D2;
        }
        .selected-item input[type="number"] {
            width: 60px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .selected-item form button.cancel {
            background-color: #f44336;
            color: white;
        }
        .selected-item form button.cancel:hover {
            background-color: #d32f2f;
        }
        .no-result {
            text-align: center;
            color: #888;
            margin-top: 20px;
        }
        .total-box {
            text-align: right;
            max-width: 800px;
            margin: 20px auto;
            font-size: 18px;
            font-weight: bold;
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .action-buttons button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }
        .action-buttons .confirm {
            background-color: #4CAF50;
            color: white;
        }
        .action-buttons .confirm:hover {
            background-color: #45a049;
        }
        .action-buttons .back {
            background-color: #888;
            color: white;
        }
        .action-buttons .back:hover {
            background-color: #666;
        }
    </style>
</head>
<body>
<h1>Nhập Nguyên Liệu</h1>

<form action="ImportServlet" method="get" class="search-box">
    <input type="hidden" name="supplierId" value="<%= request.getParameter("supplierId") %>">
    <input type="text" name="keyword" placeholder="Nhập tên nguyên liệu..." value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>" required />
    <button type="submit">Tìm kiếm</button>
</form>

<div class="results-container">
    <%
        List<Ingredient> ingredientList = (List<Ingredient>) request.getAttribute("ingredientList");
        String keyword = (String) request.getAttribute("keyword");
        if (ingredientList != null && !ingredientList.isEmpty()) {
    %>
    <h3>Kết quả tìm kiếm cho: "<%= keyword %>"</h3>
    <% for (Ingredient ingredient : ingredientList) { %>
    <div class="result-item">
        <div>
            <strong><%= ingredient.getName() %></strong>
            <p>Giá nhập: <%= String.format("%.2f", ingredient.getPrice()) %> VNĐ</p>
        </div>
        <form action="ImportServlet" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="supplierId" value="<%= request.getParameter("supplierId") %>">
            <input type="hidden" name="ingredientId" value="<%= ingredient.getIdIngredient() %>">
            <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit">Chọn</button>
        </form>
    </div>
    <% } %>
    <% } else if (keyword != null && !keyword.trim().isEmpty()) { %>
    <p class="no-result">Không tìm thấy nguyên liệu nào phù hợp.</p>
    <% } %>
</div>

<div class="selected-container">
    <h3>Nguyên liệu đã chọn</h3>
    <%
        List<Ingredient> selectedIngredients = (List<Ingredient>) session.getAttribute("selectedIngredients");
        List<Integer> quantities = (List<Integer>) session.getAttribute("quantities");
        double total = 0.0;
        if (selectedIngredients != null && !selectedIngredients.isEmpty()) {
            for (int i = 0; i < selectedIngredients.size(); i++) {
                Ingredient ingredient = selectedIngredients.get(i);
                int quantity = quantities.get(i);
                double subtotal = ingredient.getPrice() * quantity;
                total += subtotal;
    %>
    <div class="selected-item">
        <div>
            <strong><%= ingredient.getName() %></strong>
            <p>Giá nhập: <%= String.format("%.2f", ingredient.getPrice()) %> VNĐ</p>
        </div>
        <div>
            <form action="ImportServlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="supplierId" value="<%= request.getParameter("supplierId") %>">
                <input type="hidden" name="index" value="<%= i %>">
                <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>">
                <input type="number" name="quantity" value="<%= quantity %>" min="1" onchange="this.form.submit()">
            </form>
            <form action="ImportServlet" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="supplierId" value="<%= request.getParameter("supplierId") %>">
                <input type="hidden" name="index" value="<%= i %>">
                <input type="hidden" name="keyword" value="<%= keyword != null ? keyword : "" %>">
                <button type="submit" class="cancel">Hủy</button>
            </form>
        </div>
    </div>
    <% } %>
    <% } else { %>
    <p class="no-result">Chưa có nguyên liệu nào được chọn.</p>
    <% } %>
</div>

<div class="total-box">
    Tổng tiền: <%= String.format("%.2f", total) %> VNĐ
</div>

<div class="action-buttons">
    <button class="confirm" onclick="alert('Xác nhận thành công!')">Xác nhận</button>
    <a href="SelectSupplier.jsp"><button class="back">Quay lại</button></a>
</div>

</body>
</html>