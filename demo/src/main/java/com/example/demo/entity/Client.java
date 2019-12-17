package com.example.demo.entity;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	
	private String name;
	private String surname;
	private String address;
	private String email;
	private String password;
	
	public Client() {
		
	}
	
	public Client(String n, String s, String a, String e, String p) {
		name = n;
		surname = s;
		address = a;
		email = e;
		password = p;
	}
	
	public Client(int i, String n, String s, String a, String e, String p) {
		id = i;
		name = n;
		surname = s;
		address = a;
		email = e;
		password = p;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setSurname(String s) {
		surname = s;
	}
	
	public void setAddress(String a) {
		address = a;
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
