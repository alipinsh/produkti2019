package com.example.demo.entity;

import javax.persistence.*;


@Entity
@Table(name = "packer")
public class Packer {
	
	@Id
	private int manufacturer;
	
	private String code;
	
	public Packer() {
		
	}
	
	public Packer(int m, String c) {
		manufacturer = m;
		code = c;
	}
	
	public int getManufacturer() {
		return manufacturer;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setManufacturer(int m) {
		manufacturer = m;
	}
	
	public void setCode(String c) {
		code = c;
	}
	
}
