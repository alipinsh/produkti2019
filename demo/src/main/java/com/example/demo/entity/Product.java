package com.example.demo.entity;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	
	private String name;
	private String description;
	private float price;
	private String prodcode;
	private int manufacturer;
	
	public Product() {
		
	}
	
	public Product(String n, String d, float p, String pr, int m) {
		name = n;
		description = d;
		price = p;
		prodcode = pr;
		manufacturer = m;
	}
	
	public Product(int i, String n, String d, float p, String pr, int m) {
		id = i;
		name = n;
		description = d;
		price = p;
		prodcode = pr;
		manufacturer = m;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int i) {
		id = i;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String d) {
		description = d;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float p) {
		price = p;
	}
	
	public String getProdcode() {
		return prodcode;
	}
	
	public void setProdcode(String p) {
		prodcode = p;
	}
	
	public int getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(int m) {
		manufacturer = m;
	}
	
	@Override
	public String toString() {
		return id + " " + name + " " + description + " " + price + " " + prodcode + " " + manufacturer;
	}
	
}
