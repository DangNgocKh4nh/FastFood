package com.fastfood.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection con;

    public DAO() {
        try {
            // Đảm bảo bạn đã thêm đúng driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/fastfooddb";
            String username = "root";
            String password = "27052003";  // Thay thế bằng mật khẩu của bạn

            this.con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu không thể kết nối
        }
    }
}
