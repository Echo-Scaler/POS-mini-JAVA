package dao;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, barcode, category, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null)
                return;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getBarcode());
                stmt.setString(3, product.getCategory());
                stmt.setDouble(4, product.getPrice());
                stmt.setInt(5, product.getQuantity());

                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductByBarcode(String barcode) {
        String sql = "SELECT * FROM products WHERE barcode = ?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null)
                return null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, barcode);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("barcode"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return searchProducts("", "All");
    }

    public List<Product> searchProducts(String name, String category) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (category != null && !category.equals("All")) {
            sql.append(" AND category = ?");
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return products;
            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int paramIdx = 1;
                if (name != null && !name.isEmpty()) {
                    stmt.setString(paramIdx++, "%" + name + "%");
                }
                if (category != null && !category.equals("All")) {
                    stmt.setString(paramIdx++, category);
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("barcode"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("All");
        String sql = "SELECT DISTINCT category FROM products ORDER BY category ASC";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return categories;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String cat = rs.getString("category");
                    if (cat != null && !cat.isEmpty()) {
                        categories.add(cat);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}