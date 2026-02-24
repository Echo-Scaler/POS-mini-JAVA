package util;

import dao.ProductDAO;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class DataSeeder {

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        System.out.println("Starting database initialization and seeding...");

        // Ensure table exists
        try (java.sql.Connection conn = dao.DBConnection.getConnection()) {
            if (conn != null) {
                String createTable = "CREATE TABLE IF NOT EXISTS products (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "barcode VARCHAR(100) UNIQUE NOT NULL, " +
                        "category VARCHAR(100), " +
                        "price DOUBLE NOT NULL, " +
                        "quantity INT NOT NULL" +
                        ")";
                try (java.sql.Statement stmt = conn.createStatement()) {
                    stmt.execute(createTable);

                    // NEW: Migration - Add category column if it doesn't exist (for existing
                    // tables)
                    try {
                        stmt.execute("ALTER TABLE products ADD COLUMN category VARCHAR(100) AFTER barcode");
                        System.out.println("Migrated: Added 'category' column to existing table.");
                    } catch (java.sql.SQLException e) {
                        // Error code 1060 means column already exists, which is fine
                        if (!e.getSQLState().equals("42S21") && e.getErrorCode() != 1060) {
                            System.err.println("Warning during migration: " + e.getMessage());
                        }
                    }

                    System.out.println("Table 'products' initialized.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing table: " + e.getMessage());
        }

        List<Product> sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product(0, "Organic Milk", "1001", "Dairy", 3.50, 50));
        sampleProducts.add(new Product(0, "Whole Wheat Bread", "1002", "Bakery", 2.20, 30));
        sampleProducts.add(new Product(0, "Avocado", "1003", "Produce", 1.50, 100));
        sampleProducts.add(new Product(0, "Greek Yogurt", "1004", "Dairy", 4.00, 40));
        sampleProducts.add(new Product(0, "Artisanal Pizza", "1005", "Frozen", 12.00, 15));
        sampleProducts.add(new Product(0, "Fresh Strawberries", "1006", "Produce", 5.00, 20));
        sampleProducts.add(new Product(0, "Espresso Beans", "1007", "Beverages", 18.50, 10));

        int count = 0;
        for (Product p : sampleProducts) {
            // Check if product already exists by barcode to avoid duplicates
            if (productDAO.getProductByBarcode(p.getBarcode()) == null) {
                productDAO.addProduct(p);
                System.out.println("Added: " + p.getName() + " (" + p.getCategory() + ")");
                count++;
            } else {
                System.out.println("Skipped (Already exists): " + p.getName());
            }
        }

        System.out.println("Seeding complete. Added " + count + " new products.");
    }
}
