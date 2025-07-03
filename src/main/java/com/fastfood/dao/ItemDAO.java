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
        String sql = "SELECT * FROM item WHERE IdItem = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("IdItem"));
                item.setName(rs.getString("Name"));
                item.setPrice(rs.getDouble("Price"));
                item.setImage(rs.getString("Image"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addItem(Item item) {
        String sql = "INSERT INTO item (Name, Price, Image) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getImage());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setIdItem(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(Item item) {
        String sql = "UPDATE item SET Name = ?, Price = ?, Image = ? WHERE IdItem = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getImage());
            ps.setInt(4, item.getIdItem());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM item WHERE IdItem = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("IdItem"));
                item.setName(rs.getString("Name"));
                item.setPrice(rs.getDouble("Price"));
                item.setImage(rs.getString("Image"));
                items.add(item);
                System.out.println("Loaded item: " + item.getIdItem() + ", " + item.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage());
        }
        return items;
    }
}