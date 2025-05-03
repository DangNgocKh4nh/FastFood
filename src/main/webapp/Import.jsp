<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Import Ingredients</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="styles.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 15px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .total-box {
            text-align: right;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>Import Ingredients</h2>

<form action="ImportServlet" method="get">
    <input type="text" name="searchName" placeholder="Nhập tên nguyên liệu" required>
    <button type="submit" name="action" value="search">Tìm kiếm</button>
</form>

<h3>Kết quả tìm kiếm</h3>
<table>
    <tr>
        <th>Tên nguyên liệu</th>
        <th>Giá nhập</th>
        <th>Chọn</th>
    </tr>
    <%-- Lặp qua danh sách kết quả tìm kiếm từ request attribute --%>
    <c:forEach var="ingredient" items="${searchResults}">
        <tr>
            <td>${ingredient.name}</td>
            <td>${ingredient.price}</td>
            <td>
                <form action="ImportServlet" method="post">
                    <input type="hidden" name="idIngredient" value="${ingredient.idIngredient}">
                    <button type="submit" name="action" value="add">Chọn</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Danh sách nguyên liệu đã chọn</h3>
<table>
    <tr>
        <th>Tên nguyên liệu</th>
        <th>Giá nhập</th>
        <th>Số lượng</th>
        <th>Hủy</th>
    </tr>
    <%-- Lặp qua danh sách đã chọn từ session --%>
    <c:forEach var="item" items="${selectedIngredients}">
        <tr>
            <td>${item.ingredient.name}</td>
            <td>${item.ingredient.price}</td>
            <td>${item.quantity}</td>
            <td>
                <form action="ImportServlet" method="post">
                    <input type="hidden" name="idIngredient" value="${item.ingredient.idIngredient}">
                    <button type="submit" name="action" value="remove">Hủy</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="total-box">
    <strong>Tổng tiền:</strong> ${totalPrice}
</div>

<form action="ImportServlet" method="post">
    <button type="submit" name="action" value="confirm">Xác nhận</button>
    <button type="button" onclick="window.location.href='Order.jsp'">Quay lại</button>
</form>

</body>
</html>
