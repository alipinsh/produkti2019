package com.example.project;

import java.util.ArrayList;

public class Order {
    private int id;
    private boolean status;
    private ArrayList<Product> products;

    public Order() {

    }

    public Order(int id, boolean status, ArrayList<Product> products) {
        this.id = id;
        this.status = status;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void removeProduct(int productId) {
        for (int i = 0; i < products.size(); ++i) {
            if (products.get(i).getId() == productId) {
                products.remove(i);
                break;
            }
        }
    }

    public Product getProduct(int productId) {
        for (int i = 0; i < products.size(); ++i) {
            if (products.get(i).getId() == productId) {
                return products.get(i);
            }
        }

        return new Product();
    }

}
