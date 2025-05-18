package com.customer.order_management.model;

public class Order {
    private int id;
    private String customer;
    private String product;
    private int quantity;

    public Order() {}

    public Order(int id, String customer, String product, int quantity) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return id + "\t" + customer + "\t" + product + "\t" + quantity;
    }

    public static Order fromString(String line) {
        String[] parts = line.split("\t");
        return new Order(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
    }
}
