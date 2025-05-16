<%@ page import="com.fastfood.model.Ingredient" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>In Hóa Đơn</title>
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
        .invoice-container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        .invoice-header {
            margin-bottom: 20px;
        }
        .invoice-header p {
            margin: 5px 0;
            color: #555;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
        }
        .total-box {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
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
        .action-buttons .print {
            background-color: #2196F3;
            color: white;
        }
        .action-buttons .print:hover {
            background-color: #1976D2;
        }
        .action-buttons .back {
            background-color: #888;
            color: white;
        }
        .action-buttons .back:hover {
            background-color: #666;
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
            font-family: Arial, sans-serif;
            transition: background-color 0.3s;
        }
        .logout-button:hover {
            background-color: #c82333;
        }
        @media (max-width: 600px) {
            body {
                margin: 20px;
            }
            .invoice-container {
                padding: 15px;
            }
            h1 {
                font-size: 24px;
            }
            table {
                font-size: 14px;
            }
            th, td {
                padding: 8px;
            }
            .total-box {
                font-size: 16px;
            }
            .action-buttons {
                flex-direction: column;
                gap: 10px;
            }
            .action-buttons button {
                padding: 8px 16px;
                font-size: 14px;
            }
            .logout-container {
                position: static;
                text-align: center;
                margin-bottom: 30px;
            }
        }
        @media print {
            .action-buttons, .logout-container {
                display: none !important;
            }
            body {
                margin: 0;
                background-color: white;
            }
            h1 {
                font-size: 20px;
            }
            .invoice-container {
                box-shadow: none;
                padding: 10px;
            }
        }
    </style>
    <script>
        function printInvoice() {
            window.print();
        }
    </script>
</head>
<body>
<%
    if (session.getAttribute("manager") != null) {
%>
<div class="logout-container">
    <form action="login.jsp" method="get">
        <button type="submit" class="logout-button">Đăng xuất</button>
    </form>
</div>
<%
    }
%>

<h1>Hóa Đơn Nhập Hàng</h1>

<div class="invoice-container">
    <div class="invoice-header">
        <p><strong>Người nhập:</strong> <%= request.getAttribute("managerName") != null ? request.getAttribute("managerName") : "Không xác định" %></p>
        <p><strong>Nhà cung cấp:</strong> <%= request.getAttribute("supplierName") != null ? request.getAttribute("supplierName") : "Không xác định" %></p>
        <p><strong>Ngày nhập:</strong> <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %></p>
    </div>

    <table>
        <tr>
            <th>Tên nguyên liệu</th>
            <th>Giá nhập (VNĐ)</th>
            <th>Số lượng</th>
            <th>Thành tiền (VNĐ)</th>
        </tr>
        <%
            List<Ingredient> selectedIngredients = (List<Ingredient>) request.getAttribute("selectedIngredients");
            List<Integer> quantities = (List<Integer>) request.getAttribute("quantities");
            double total = 0;
            if (selectedIngredients != null && !selectedIngredients.isEmpty()) {
                for (int i = 0; i < selectedIngredients.size(); i++) {
                    Ingredient ingredient = selectedIngredients.get(i);
                    int quantity = quantities.get(i);
                    double lineTotal = ingredient.getPrice() * quantity;
                    total += lineTotal;
        %>
        <tr>
            <td><%= ingredient.getName() %></td>
            <td><%= String.format("%.0f", ingredient.getPrice()) %></td>
            <td><%= quantity %></td>
            <td><%= String.format("%.0f", lineTotal) %></td>
        </tr>
        <% } %>
        <% } else { %>
        <tr>
            <td colspan="4" style="text-align: center;">Không có nguyên liệu nào được chọn.</td>
        </tr>
        <% } %>
    </table>

    <div class="total-box">
        Tổng tiền: <%= String.format("%.0f", total) %> VNĐ
    </div>

    <div class="action-buttons">
        <button class="print" onclick="printInvoice()">In hóa đơn</button>
        <a href="SelectSupplier.jsp"><button class="back">Quay lại</button></a>
    </div>
</div>
</body>
</html>