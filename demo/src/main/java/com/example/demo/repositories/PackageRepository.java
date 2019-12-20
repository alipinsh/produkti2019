package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Package;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Integer> {
	
	List<Package> findByClient(int client);
	List<Package> findByClientAndSent(int client, boolean sent);
	
	@Query(value = "SELECT DISTINCT package.* FROM package JOIN package_products ON package_products.package = package.id " + 
			"JOIN product ON product.id = package_products.product WHERE product.manufacturer = ?1 AND package.sent = true;", nativeQuery = true)
	List<Package> findByManufacturer(int m);
	
}
