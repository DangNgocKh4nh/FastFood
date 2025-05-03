<%@ page import="com.fastfood.model.Supplier" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chọn Nhà Cung Cấp</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
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
        .results-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .result-item {
            background-color: white;
            padding: 15px 20px;
            margin-bottom: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .result-item strong {
            font-size: 18px;
            color: #333;
        }
        .result-item form button {
            padding: 8px 16px;
            border: none;
            background-color: #2196F3;
            color: white;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .result-item form button:hover {
            background-color: #1976D2;
        }
        .no-result {
            text-align: center;
            color: #888;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>Chọn Nhà Cung Cấp</h1>

<form action="SelectSupplierServlet" method="get" class="search-box">
    <input type="text" name="keyword" placeholder="Nhập tên nhà cung cấp..." value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>" required />
    <button type="submit">Tìm kiếm</button>
</form>

<div class="results-container">
    <%
        List<Supplier> supplierList = (List<Supplier>) request.getAttribute("supplierList");
        String keyword = (String) request.getAttribute("keyword");
        if (supplierList != null && !supplierList.isEmpty()) {
    %>
    <h3>Kết quả tìm kiếm cho: "<%= keyword %>"</h3>
    <% for (Supplier supplier : supplierList) { %>
    <div class="result-item">
        <strong><%= supplier.getName() %></strong>
        <form action="Import.jsp" method="get">
            <input type="hidden" name="supplierId" value="<%= supplier.getIdSupplier() %>">
            <button type="submit">Chọn</button>
        </form>
    </div>
    <% } %>
    <% } else if (keyword != null && !keyword.trim().isEmpty()) { %>
    <p class="no-result">Không tìm thấy nhà cung cấp nào phù hợp.</p>
    <% } %>
</div>
</body>
</html>
