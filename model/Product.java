package com.pos.model;

public class Product {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private int quantity;

    public Product(int id, String name, String barcode, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
}