package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByManufacturer(int manufacturer);
	List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String s1, String s2);
	List<Product> findByNameContainingIgnoreCase(String s);
	List<Product> findByDescriptionContainingIgnoreCase(String s);

}
