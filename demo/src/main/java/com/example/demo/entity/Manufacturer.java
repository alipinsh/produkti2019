package com.example.demo.entity;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "manufacturer")
public class Manufacturer {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	
	private String name;
	private String address;
	private String description;
	private String email;
	private String password;
	
	public Manufacturer() {
		
	}
	
	public Manufacturer(String e, String p, String n, String d, String a) {
		email = e;
		password = p;
		name = n;
		description = d;
		address = a;
	}
	
	public Manufacturer(int i, String e, String p, String n, String a) {
		id = i;
		email = e;
		password = p;
		name = n;
		address = a;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setAddress(String a) {
		address = a;
	}
	
	public void setDescription(String d) {
		description = d;
	}
	
	public void setEmail(String e) {
		email = e;
	}
	
	public void setPassword(String p) {
		password = p;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int i) {
		id = i;
	}
}
