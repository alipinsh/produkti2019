package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	Client findByEmail(String e);
}
