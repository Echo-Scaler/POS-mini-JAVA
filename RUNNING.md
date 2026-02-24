# How to Run the POS System

Follow these steps to set up and run your modern POS application.

## 1. Database Setup

You need a MySQL database running.

- Open your MySQL terminal or client.
- Run the provided [setup.sql](file:///c:/Users/outward_tech01/OneDrive/Desktop/javaApp/javaTeT/setup.sql) script:
  ```bash
  mysql -u root -p < setup.sql
  ```
- **Note**: Ensure the credentials in [DBConnection.java](file:///c:/Users/outward_tech01/OneDrive/Desktop/javaApp/javaTeT/dao/DBConnection.java) match your MySQL username and password.

## 2. Dependencies

You will need the **MySQL Connector/J** JAR file.

- Download it from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/).
- Place the `.jar` file in the root of the project folder (`javaTeT/`).

## 3. Compilation

Compile all the Java files from the project root:

```bash
javac -d . *.java dao/*.java model/*.java service/*.java ui/*.java
```

## 4. Execution

Run the application using the following command (replace `mysql-connector-java.jar` with your actual filename):

```bash
java -cp ".;mysql-connector-java.jar" Main
```

## UI Features

- **Dynamic Search**: Search products in real-time by name.
- **Modern Grid**: View products in a clean, card-based layout.
- **Shopping Cart**: Add items to the cart and see subtotals, tax, and totals update instantly.
- **Elite Aesthetics**: Uses a custom Slate and Indigo color palette for a premium feel.
