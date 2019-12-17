package com.example.demo.entity;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "package")
public class Package {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	
	private int client;
	
	private boolean status;
	private boolean sent;
	
	public Package() {
		
	}
	
	public Package(int c, boolean st, boolean se) {
		client = c;
		status = st;
		sent = se;
	}
	
	public int getId() {
		return id;
	}
	
	public int getClient() {
		return client;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public boolean getSent() {
		return sent;
	}
	
	public void setId(int i) {
		id = i;
	}
	
	public void setStatus(boolean s) {
		status = s;
	}
	
	public void setClient(int c) {
		client = c;
	}
	
	public void setSent(boolean s) {
		sent = s;
	}

}
