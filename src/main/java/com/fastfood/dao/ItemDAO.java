package com.fastfood.dao;

import com.fastfood.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO {

    public List<Item> getItemsByName(String keyword) {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE Name LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setIdItem(rs.getInt("IdItem"));
                    item.setName(rs.getString("Name"));
                    item.setPrice(rs.getDouble("Price"));
                    item.setImage(rs.getString("Image"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM item WHERE IdItem = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new Item();
                    item.setIdItem(rs.getInt("IdItem"));
                    item.setName(rs.getString("Name"));
                    item.setPrice(rs.getDouble("Price"));
                    item.setImage(rs.getString("Image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }
}