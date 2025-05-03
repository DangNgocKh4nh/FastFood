package com.fastfood.dao;

import com.fastfood.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO {

    public UserDAO() {
        super(); // gọi constructor DAO cha để lấy connection
    }

    public User getUserByName(String username) {
        User user = null;
        String sql = "SELECT * FROM User WHERE Username = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setIdUser(rs.getInt("IdUser"));
                    user.setUsername(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                    user.setRole(rs.getString("Role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // nếu tìm thấy 1 dòng => login thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void register(User user) {
        if (this.con == null) {
            System.out.println("Connection is null");
            return;
        }

        String sql = "INSERT INTO User (Username, Password, Role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole()); // Role như "Customer"

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
