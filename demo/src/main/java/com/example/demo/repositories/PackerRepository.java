package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Packer;

import java.util.List;

public interface PackerRepository extends JpaRepository<Packer, Integer> {
	Packer findByCode(String c);
}
