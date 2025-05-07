package com.fastfood.dao;

import com.fastfood.model.Manager;
import com.fastfood.model.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO extends DAO {
    public Manager getManagerByUserId(int idUser) {
        Manager manager = null;
        String sql = "SELECT * FROM manager WHERE IdUser = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    manager = new Manager();
                    manager.setIdManager(rs.getInt("IdManager"));
                    manager.setIdUser(idUser);
                    manager.setName(rs.getString("Name"));
                    manager.setPhoneNumber(rs.getString("PhoneNumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return manager;
    }
}
