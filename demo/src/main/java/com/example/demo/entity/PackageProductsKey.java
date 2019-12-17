package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class PackageProductsKey implements Serializable {
	
    @Column(name = "product")
    private int product;
 
    @Column(name = "package")
    private int pakage;
    
    PackageProductsKey() {
    	
    }
    
    PackageProductsKey(int p, int pr) {
    	pakage = p;
    	product = pr;
    }
    
    @Override
    public int hashCode() {
    	
    	return 0;
    }
    
    @Override
    public boolean equals(Object o) {
    	
    	return true;
    }
}
