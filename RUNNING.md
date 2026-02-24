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

## 2. Seed Sample Data (Optional)

If you want to quickly populate the database with sample products via Java:

```powershell
# FOR POWERSHELL: Use quotes around the classpath
java -cp ".;mysql-connector-java-8.1.0.jar" util.DataSeeder
```

## 3. Dependencies

You **MUST** have the MySQL Connector JAR in your project folder.

1. Download `mysql-connector-j-8.x.x.jar` from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/).
2. Copy the `.jar` file into this folder: `c:\Users\outward_tech01\OneDrive\Desktop\javaApp\javaTeT\`
3. **Make sure the filename in the commands below matches your JAR filename exactly.**

## 4. Compilation

Compile all Java files:

```powershell
javac -d . *.java dao/*.java model/*.java service/*.java ui/*.java util/*.java
```

## 5. Execution

Run the application (ensure you use the correct JAR name):

```powershell
# FOR POWERSHELL: Use quotes around the classpath
java -cp ".;mysql-connector-java-8.1.0.jar" Main
```

> [!TIP]
> If your JAR is named `mysql-connector-j-8.3.0.jar`, use `java -cp ".;mysql-connector-j-8.3.0.jar" Main`.
