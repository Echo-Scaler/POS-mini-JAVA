package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/posdb",
                    "root",
                    ""); // <-- UPDATE THIS to your MySQL password
        } catch (Exception e) {
            System.err.println("CRITICAL: Could not connect to database (posdb).");
            System.err.println("1. Ensure MySQL is running on localhost:3306");
            System.err.println("2. Check if database 'posdb' exists (run setup.sql)");
            System.err.println("3. Verify credentials in DBConnection.java");
            System.err.println("4. Ensure mysql-connector-java.jar is in the classpath");
            e.printStackTrace();
            return null;
        }
    }
}