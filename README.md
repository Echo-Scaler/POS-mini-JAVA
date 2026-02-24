# 🛒 Simple POS System

Hello! This is a Point of Sale (POS) system I built using Java and MySQL. I tried to make it look really cool and professional while keeping the code easy to understand.

## 🌟 What this app does:

- **Product List**: It shows products in a nice grid. I used `GridLayout` for this!
- **Search Bar**: You can type a name and it finds the product instantly.
- **Category Filter**: I added a dropdown so you can pick things like "Dairy" or "Produce".
- **Shopping Cart**: You can add items, and it calculates the price + tax automatically.
- **Database**: All the products are stored in a MySQL database.

## 🛠️ How I built it:

- **Java Swing**: For the windows and buttons. I used a "Slate" and "Indigo" color theme.
- **MySQL**: To save all the product info.
- **JDBC**: This is how the Java code talks to the database.

## 📂 Project Structure (Where everything is):

Here is how I organized my files. I tried to keep them in separate folders to stay organized!

```text
javaTeT/
├── dao/              (Database code)
│   ├── DBConnection  (Connects to MySQL)
│   └── ProductDAO    (Handles product queries)
├── model/            (Data classes)
│   ├── Product       (Holds product info)
│   └── CartItem      (Items in the cart)
├── service/          (Calculations)
│   └── POSService    (Cart logic & tax math)
├── ui/               (The screens)
│   └── POSForm       (Main app window)
├── util/             (Utility tools)
│   └── DataSeeder    (Adds sample data)
├── Main.java         (Starts the app)
├── run.bat           (One-click to start!)
└── setup.sql         (Database setup script)
```

## 🚀 How to run it on your computer:

### 1. The Database

First, you need MySQL. You can use the `setup.sql` file to create the table. If you are using PowerShell, try this:
`Get-Content setup.sql | mysql -u root -p`

### 2. The JAR file

Make sure you have the `mysql-connector-j-9.6.0.jar` file in this folder, or the code won't be able to connect to the database!

### 3. Just Click Run!

I made a `run.bat` file to make it easy for everyone:

1. Double-click `run.bat`.
2. Press **[2]** to add my sample products (Seeding).
3. Press **[1]** to open the app!

