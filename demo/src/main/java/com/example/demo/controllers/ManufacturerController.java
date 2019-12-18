package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import com.example.demo.Ch;
import com.example.demo.entity.Client;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Product;
import com.example.demo.repositories.ManufacturerRepository;
import com.example.demo.repositories.PackerRepository;
import com.example.demo.entity.Packer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Controller
@Scope("session")
public class ManufacturerController {
	
	@Autowired
	User user;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	PackerRepository packerRepository;
	
	@GetMapping("/manufacturer")
	public String showCurrentManufacturer(Model model) {
		model.addAttribute("isclient", User.isClient);
		if (User.id == 0) {
			return "not_found";
		}
		
		if (User.isClient) {
			model.addAttribute("manufacturers", manufacturerRepository.findAll());
			return "manufacturer_list";
		} else {
			model.addAttribute("ismine", true);
			
			Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(User.id);
			if (optionalManufacturer.isEmpty()) {
				return "manufacturer_not_found";
			}
			
			Manufacturer manufacturer = optionalManufacturer.get();
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
	
	@GetMapping("/manufacturers")
	public String showManufacturers(@RequestParam(value="s", defaultValue = "") String s, Model model) {
		if (User.id == 0) {
			return "not_found";
		}
		
		List<Manufacturer> manufacturers;
		if (s != "") {
			manufacturers = manufacturerRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(s, s);
		} else {
			manufacturers = manufacturerRepository.findAll();
		}
		
		model.addAttribute("manufacturers", manufacturers);
		model.addAttribute("isclient", User.isClient);
		
		return "manufacturer_list";
	}
	
	@GetMapping("/manufacturer/{id}")
	public String showManufacturer(@PathVariable String id, Model model) {
		model.addAttribute("isclient", User.isClient);
		if (User.id == 0) {
			return "not_found";
		}
		
		if (User.isClient || Integer.parseInt(id) != User.id) {
			model.addAttribute("ismine", false);
			model.addAttribute("code", 0);
		} else {
			model.addAttribute("ismine", true);
			model.addAttribute("code", packerRepository.findById(User.id).get().getCode());
		}
		
		Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(Integer.parseInt(id));
		if (optionalManufacturer.isEmpty()) {
			return "manufacturer_not_found";
		}
		Manufacturer manufacturer = optionalManufacturer.get();
		model.addAttribute("id", manufacturer.getId());
		model.addAttribute("name", manufacturer.getName());
		model.addAttribute("description", manufacturer.getDescription());
		model.addAttribute("address", manufacturer.getAddress());
		model.addAttribute("email", manufacturer.getEmail());
		model.addAttribute("products", productRepository.findByManufacturer(manufacturer.getId()));
		
		return "manufacturer";
	}
	
	@PostMapping("/manufacturer")
	@ResponseBody
	public boolean addManufacturer(@RequestBody Map<String, String> body) {
		
		if (manufacturerRepository.findByEmail(body.get("email")) == null &&
				packerRepository.findByCode(body.get("code")) == null) {
			String email = body.get("email").trim().replaceAll("\\s{2,}", "");
			String password = body.get("password").trim().replaceAll("\\s{2,}", "");
			String password2 = body.get("password2").trim().replaceAll("\\s{2,}", "");
			String name = body.get("name").trim().replaceAll("\\s{2,}", "");
			String description = body.get("description").trim().replaceAll("\\s{2,}", "");
			String address = body.get("address").trim().replaceAll("\\s{2,}", "");
			String code = "";
			
			if (!(password.equals(password2))) {
				return false;
			}
			
			Random rand = new Random();
			do {
				for (int i = 0; i < 3; ++i) {
					code += Integer.toString(rand.nextInt(10)); 
				}
			} while (!(packerRepository.findByCode(code) == null));
			
			if (Ch.checkEmail(email) && Ch.checkPassword(password) && Ch.checkString(name) && Ch.checkString(description) && Ch.checkString(address) && Ch.checkString(code)) {
				Manufacturer manufacturer = manufacturerRepository.save(new Manufacturer(email, password, name, description, address));
				packerRepository.save(new Packer(manufacturer.getId(), code));
				return true;
			}
		}
		
		return false;
	}
	
	@PutMapping("/manufacturer")
	@ResponseBody
	public Manufacturer editManufacturer(@RequestBody Map<String, String> body) {
		if (User.isClient == false && User.id == Integer.parseInt(body.get("id"))) {
			Manufacturer manufacturer = manufacturerRepository.getOne(Integer.parseInt(body.get("id")));
			
	        if (body.containsKey("name")) {
	        	String name = body.get("name").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(name)) {
        			return null;
        		}
	        	manufacturer.setName(name);
	        }
	        if (body.containsKey("description")) {
	        	String description = body.get("description").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(description)) {
        			return null;
        		}
	        	manufacturer.setDescription(description);
	        }
	        if (body.containsKey("address")) {
	        	String address = body.get("address").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(address)) {
        			return null;
        		}
	        	manufacturer.setAddress(address);
	        }
	        if (body.containsKey("email")) {
	        	String email = body.get("email").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkEmail(email)) {
        			return null;
        		}
	        	manufacturer.setEmail(email);
	        }
	        if (body.containsKey("password")) {
	        	String password = body.get("password").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkPassword(password)) {
        			return null;
        		}
	        	manufacturer.setPassword(password);
	        }
	        if (body.containsKey("code")) {
	        	String code = body.get("code").trim().replaceAll("\\s{2,}", "");
	        	if (!Ch.checkString(code)) {
        			return null;
        		}
	        	Packer packer = packerRepository.findById(User.id).get();
	        	packer.setCode(code);
	        	packerRepository.save(packer);
	        }
	        
	        return manufacturerRepository.save(manufacturer);
		}
		
		return null;
	}
	
	@DeleteMapping("/manufacturer")
	@ResponseBody
	public void deleteManufacturer(@RequestBody String id) {
		if (User.isClient == false && User.id == Integer.parseInt(id)) {
			manufacturerRepository.deleteById(Integer.parseInt(id)); 
		}
	}
	
	@PostMapping("/code")
	@ResponseBody
	public String regenerate() {
		String code = "";
		Random rand = new Random();
		do {
			for (int i = 0; i < 3; ++i) {
				code += Integer.toString(rand.nextInt(10)); 
			}
		} while (!(packerRepository.findByCode(code) == null));
		
		return code;
	}
}
