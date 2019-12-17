package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PackageProducts;

import java.util.List;

public interface PackageProductsRepository extends JpaRepository<PackageProducts, Integer> {

	List<PackageProducts> findByPakage(int p);
	PackageProducts findByPakageAndProduct(int pa, int pr);
	
}
