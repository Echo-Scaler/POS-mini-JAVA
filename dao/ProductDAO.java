package dao;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, barcode, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getBarcode());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductByBarcode(String barcode) {
        String sql = "SELECT * FROM products WHERE barcode = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barcode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}