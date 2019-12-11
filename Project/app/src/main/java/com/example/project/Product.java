package com.example.project;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private String prodcode;
    private boolean scanned;

    public Product() {

    }

    public Product(int id, String name, int quantity, String prodcode, boolean scanned) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.prodcode = prodcode;
        this.scanned = scanned;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProdcode() {
        return prodcode;
    }

    public boolean getScanned() {
        return scanned;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantitiy) {
        this.quantity = quantitiy;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

}
