package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "package_products")
@IdClass(PackageProductsKey.class)
public class PackageProducts {
	
	@Id
	@Column(name = "package")
    private int pakage;
	
	@Id
	@Column(name = "product")
	private int product;
    
    private int quantity;
    private boolean scanned;
    
    public PackageProducts() {
    	
    }
    
    public PackageProducts(int p, int pr, int q, boolean s) {
    	pakage = p;
    	product = pr;
    	quantity = q;
    	scanned = s;
    }
    
    public int getPackage() {
    	return pakage;
    }
    
    public void setPackage(int p) {
    	pakage = p;
    }
    
    public int getProduct() {
    	return product;
    }
    
    public void setProduct(int pr) {
    	product = pr;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    
    public boolean getScanned() {
    	return scanned;
    }
    
    public void setQuantity(int q) {
    	quantity = q;
    }
    
    public void setScanned(boolean s) {
    	scanned = s;
    }
}
