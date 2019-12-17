package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Package;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Integer> {
	
	
	List<Package> findByClient(int client);
	List<Package> findByClientAndSent(int client, boolean sent);
	
}
