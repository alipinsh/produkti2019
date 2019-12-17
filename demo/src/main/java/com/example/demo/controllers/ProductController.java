package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Product;
import com.example.demo.repositories.ManufacturerRepository;
import com.example.demo.Ch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Scope("session")
public class ProductController {
	
	@Autowired
	User user;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;
	
	@GetMapping("/product/{id}")
	public String getProduct(@PathVariable String id, Model model) {
		if (User.id == 0) {
			return "not_found";
		}
		
		Optional<Product> optionalProduct = productRepository.findById(Integer.parseInt(id));
		if (optionalProduct.isEmpty()) {
			return "product_not_found";
		}
		Product product = optionalProduct.get();
		model.addAttribute("product", product);
		model.addAttribute("manufacturer", manufacturerRepository.findById(product.getManufacturer()).get());
		model.addAttribute("isclient", User.isClient);
		if (User.id == product.getManufacturer()) {
			model.addAttribute("ismine", true);
		} else {
			model.addAttribute("ismine", false);
		}
		
		return "product";
	}

	@GetMapping("/products")
	public String showProducts(@RequestParam(value="s", defaultValue = "") String s, Model model) {
		if (User.id == 0) {
			return "not_found";
		}
		
		List<Product> products;
		
		if (s != "") {
			products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(s, s);
		} else {
			products = productRepository.findAll();
		}
		
		model.addAttribute("products", products);
		model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("isclient", User.isClient);
        
		return "product_list";
	}
	
	
	@PostMapping("/product")
	@ResponseBody
	public Product addProduct(@RequestBody Map<String, String> body) {
		if (User.isClient == false && User.id == Integer.parseInt(body.get("manufacturer"))) {
			String name = body.get("name").trim().replaceAll("\\s{2,}", "");
			String description = body.get("description").trim().replaceAll("\\s{2,}", "");
			if (!Ch.checkString(body.get("price"))) {
				return null;
			}
			float price = Float.parseFloat(body.get("price"));
			String prodcode = body.get("prodcode").trim().replaceAll("\\s{2,}", "");
			int manufacturer = Integer.parseInt(body.get("manufacturer"));
			
			if (Ch.checkString(name) && Ch.checkString(description) && Ch.checkString(prodcode) && Ch.checkFloat(price)) {
				return productRepository.save(new Product (name, description, price, prodcode, manufacturer));
			}
		}
        return null;
	}
	
	@PutMapping("/product/{id}")
	@ResponseBody
	public Product editProduct(@PathVariable String id, @RequestBody Map<String, String> body) {
        Product product = productRepository.getOne(Integer.parseInt(id));
        if (User.isClient == false && User.id == product.getManufacturer()) {
        	if (body.containsKey("name")) {
        		if (!Ch.checkString(body.get("name"))) {
        			return null;
        		}
            	product.setName(body.get("name").trim().replaceAll("\\s{2,}", ""));
            }
            if (body.containsKey("description")) {
            	if (!Ch.checkString(body.get("description"))) {
        			return null;
        		}
            	product.setDescription(body.get("description").trim().replaceAll("\\s{2,}", ""));
            }
            if (body.containsKey("price")) {
            	if (!Ch.checkString(body.get("price"))) {
    				return null;
    			}
            	if (!Ch.checkFloat(Float.parseFloat(body.get("price")))) {
        			return null;
        		}
            	product.setPrice(Float.parseFloat(body.get("price")));
            }
            if (body.containsKey("prodcode")) {
            	if (!Ch.checkString(body.get("prodcode"))) {
        			return null;
        		}
            	product.setProdcode(body.get("prodcode").trim().replaceAll("\\s{2,}", ""));
            }
            return productRepository.save(product);
        }
        return null;
	}
	
	@DeleteMapping("/product/{id}")
	@ResponseBody
	public void deleteProduct(@PathVariable String id) {
		int manid = productRepository.getOne(Integer.parseInt(id)).getManufacturer();
		if (User.isClient == false && User.id == manid) {
			productRepository.deleteById(Integer.parseInt(id));
		}
		
	}
	
}
