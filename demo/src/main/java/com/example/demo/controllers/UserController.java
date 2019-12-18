package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Ch;
import com.example.demo.entity.Client;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Package;
import com.example.demo.entity.PackageProducts;
import com.example.demo.entity.Product;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ManufacturerRepository;
import com.example.demo.repositories.PackageProductsRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.PackerRepository;
import com.example.demo.repositories.ProductRepository;

@Controller
@Scope("session")
public class UserController {
	
	@Autowired
	User user;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	PackerRepository packerRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	PackageProductsRepository packageProductsRepository;
	
	@GetMapping("/")
	public String homepage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		
		return "login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody Map<String, String> body, Model model) {
		String email = body.get("email");
		String password = body.get("password");
		String type = body.get("type");
		
		if (type.equals("c")) {
			Client client = clientRepository.findByEmail(email);
			if (client != null) {
				if (client.getPassword().equals(password)) {
					User.isClient = true;
					User.id = client.getId();
					
					model.addAttribute("isclient", User.isClient);
					model.addAttribute("name", client.getName());
					model.addAttribute("surname", client.getSurname());
					model.addAttribute("email", client.getEmail());
					model.addAttribute("address", client.getAddress());
					model.addAttribute("id", client.getId());
					
					List<Package> packages = packageRepository.findByClient(client.getId());
					model.addAttribute("packages", packages);
					
					Map<Integer, List<PackageProducts>> packageProducts = new HashMap<Integer, List<PackageProducts>>();
					for (int i = 0; i < packages.size(); ++i) {
						packageProducts.put(
							packages.get(i).getId(),
							packageProductsRepository.findByPakage(packages.get(i).getId())
						);
					}
					model.addAttribute("packageProducts", packageProducts);
					
					Map<Integer, Product> products = new HashMap<Integer, Product>();
					List<Integer> productIds = new ArrayList<Integer>();
					for (Map.Entry<Integer, List<PackageProducts>> entry : packageProducts.entrySet()) {
						for (int i = 0; i < entry.getValue().size(); ++i) {
							int productId = entry.getValue().get(i).getProduct();
							if (!(productIds.contains(productId))) {
								productIds.add(productId);
								products.put(productId, productRepository.findById(productId).get());
							}
						}
					}
					
					model.addAttribute("products", products);
					
					return "client";
				}
			}
			
		} else if (type.equals("m")) {
			Manufacturer manufacturer = manufacturerRepository.findByEmail(email);
			if (manufacturer != null) {
				if (manufacturer.getPassword().equals(password)) {
					User.isClient = false;
					User.id = manufacturer.getId();
					
					model.addAttribute("isclient", User.isClient);
					model.addAttribute("ismine", true);
					model.addAttribute("id", manufacturer.getId());
					model.addAttribute("name", manufacturer.getName());
					model.addAttribute("description", manufacturer.getDescription());
					model.addAttribute("address", manufacturer.getAddress());
					model.addAttribute("email", manufacturer.getEmail());
					model.addAttribute("products", productRepository.findByManufacturer(manufacturer.getId()));
					model.addAttribute("code", packerRepository.findById(User.id).get().getCode());
					
					return "manufacturer";
				}
			}
		}
		
		model.addAttribute("loginError", true);
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "register";
	}
	
	@GetMapping("/logout")
	public String logout() {
		User.isClient = true;
		User.id = 0;
		return "index";
	}
	
	@PostMapping("/checkemail")
	@ResponseBody
	public boolean checkEmail(@RequestBody Map<String, String> body) {
		String email = body.get("email").trim().replaceAll("\\s{2,}", "");
		
		if (!(Ch.checkEmail(email))) {
			return false; 
		}
		
		if (body.get("role") == "c") {
			Client c = clientRepository.findByEmail(email);
			if (c == null) {
				return false;
			}
		} else if (body.get("role") == "m") {
			Manufacturer m = manufacturerRepository.findByEmail(email);
			if (m == null) {
				return false;
			}
		}
		
		return true;
	}
	
	@GetMapping("/edit")
	public String editpage(Model model) {
		model.addAttribute("isclient", User.isClient);
		if (!User.isClient) {
			model.addAttribute("code", packerRepository.findById(User.id).get().getCode());
		}
		
		return "edit"; 
	}
	
}
