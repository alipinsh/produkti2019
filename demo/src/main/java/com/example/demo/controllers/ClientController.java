package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ManufacturerRepository;
import com.example.demo.repositories.PackageProductsRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.Ch;
import com.example.demo.entity.Client;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Package;
import com.example.demo.entity.PackageProducts;
import com.example.demo.entity.Packer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Scope("session")
public class ClientController {
	
	@Autowired
	User user;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	PackageProductsRepository packageProductsRepository;
	
	@GetMapping("/client")
    public String showClient(Model model) {
		if (User.id == 0) {
			return "not_found";
		}
		
		model.addAttribute("isclient", User.isClient);
		if (User.isClient) {
			Client client = clientRepository.getOne(User.id);
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
		return "not_found";
		
    }
	
	@PostMapping("/client")
	@ResponseBody
	public boolean addClient(@RequestBody Map<String, String> body) {
		
		if (clientRepository.findByEmail(body.get("email")) == null) {
			String email = body.get("email").trim().replaceAll("\\s{2,}", "");
			String password = body.get("password").trim().replaceAll("\\s{2,}", "");
			String name = body.get("name").trim().replaceAll("\\s{2,}", "");
			String surname = body.get("surname").trim().replaceAll("\\s{2,}", "");
			String address = body.get("address").trim().replaceAll("\\s{2,}", "");
			
			if (Ch.checkEmail(email) && Ch.checkPassword(password) && Ch.checkString(name) && Ch.checkString(surname) && Ch.checkString(address)) {
				clientRepository.save(new Client(name, surname, address, email, password));
				return true;
			}
		}
		
		return false;
    	
	}
	
	@PutMapping("/client")
	@ResponseBody
	public Client editClient(@RequestBody Map<String, String> body) {
		if (User.isClient && User.id == Integer.parseInt(body.get("id"))) {
			Client client = clientRepository.getOne(Integer.parseInt(body.get("id")));
			if (body.containsKey("name")) {
	        	String name = body.get("name").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(name)) {
        			return null;
        		}
	        	client.setName(name);
	        }
	        if (body.containsKey("username")) {
	        	String username = body.get("username").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(username)) {
        			return null;
        		}
	        	client.setSurname(username);
	        }
	        if (body.containsKey("address")) {
	        	String address = body.get("address").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(address)) {
        			return null;
        		}
	        	client.setAddress(address);
	        }
	        if (body.containsKey("email")) {
	        	String email = body.get("email").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkEmail(email)) {
        			return null;
        		}
	        	client.setEmail(email);
	        }
	        if (body.containsKey("password")) {
	        	String password = body.get("password").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkPassword(password)) {
        			return null;
        		}
	        	client.setPassword(password);
	        }
	        
	        return clientRepository.save(client);
		}
        return null;
	}
	
	@DeleteMapping("/client")
	@ResponseBody
	public void deleteClient(@RequestBody String id) {
		if (User.isClient && User.id == Integer.parseInt(id)) {
			clientRepository.deleteById(Integer.parseInt(id));
		}
	}
	
}
