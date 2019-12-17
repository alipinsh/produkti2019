package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Product;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
	List<Manufacturer> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String s1, String s2);
	List<Manufacturer> findByNameContainingIgnoreCase(String s);
	List<Manufacturer> findByDescriptionContainingIgnoreCase(String s);
	Manufacturer findByEmail(String e);
}
