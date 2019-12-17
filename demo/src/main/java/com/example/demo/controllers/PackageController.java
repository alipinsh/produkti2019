package com.example.demo.controllers;

import com.example.demo.repositories.ProductRepository;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Package;
import com.example.demo.entity.PackageProducts;
import com.example.demo.repositories.ManufacturerRepository;
import com.example.demo.repositories.PackageProductsRepository;
import com.example.demo.repositories.PackageRepository;

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
public class PackageController {
	
	@Autowired
	User user;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	PackageProductsRepository packageProductsRepository;
	
	@PostMapping("/package")
	@ResponseBody
	public String addProductToPackage(@RequestBody String product) {
		
		if (User.isClient) {
			int productid = Integer.parseInt(product);
			int manufacturerid = productRepository.findById(productid).get().getManufacturer();
			List<Package> packages = packageRepository.findByClientAndSent(User.id, false);
			
			for (int i = 0; i < packages.size(); ++i) {
				int packageid = packages.get(i).getId();
				List<PackageProducts> packageProducts = packageProductsRepository.findByPakage(packageid);
				int anotherproductid = 0;
				
				if (packageProducts.size() > 0) {
					int exampleproductid = packageProducts.get(0).getProduct();
					int anothermanufacturerid = productRepository.findById(exampleproductid).get().getManufacturer();
					if (manufacturerid == anothermanufacturerid) {
						for (int j = 0; j < packageProducts.size(); ++j) {
							anotherproductid = packageProducts.get(j).getProduct();
							if (productid == anotherproductid) {
								return "duplicate";
							}
						}
						packageProductsRepository.save(new PackageProducts(packageid, productid, 1, false));
						return "added";
					}
				}
				
			}
			
			int newpackageid = packageRepository.save(new Package(User.id, false, false)).getId();
			packageProductsRepository.save(new PackageProducts(newpackageid, productid, 1, false));
			
			return "created";
		}
		
		return "error";
		
	}
	
	@PutMapping("/package")
	@ResponseBody
	public boolean sendPackage(@RequestBody Map<String, Integer> body) {
		int clientid = packageRepository.getOne(body.get("pakage")).getClient();
		
		if (User.isClient && User.id == clientid) {
			if (body.size() == 3) {
				PackageProducts p = packageProductsRepository.findByPakageAndProduct(
					body.get("pakage"), 
					body.get("product")
				);
				p.setQuantity(body.get("quantity"));
				packageProductsRepository.save(p);
				return true;
			} else if (body.size() == 1) {
				Package pakage = packageRepository.getOne(body.get("pakage"));
				pakage.setSent(true);
				packageRepository.save(pakage);
				return true;
			}
		}
		
		return false;
	}

	@DeleteMapping("/package")
	@ResponseBody
	public boolean deletePackage(@RequestBody Map<String, Integer> body) {
		int clientid = packageRepository.getOne(body.get("pakage")).getClient();
		
		if (User.isClient && User.id == clientid) {
			if (body.size() == 2) {
				packageProductsRepository.delete(
					packageProductsRepository.findByPakageAndProduct(
						body.get("pakage"), 
						body.get("product")
					)
				);
			} else if (body.size() == 1){
				List<PackageProducts> packageProducts = packageProductsRepository.findByPakage(body.get("pakage"));
				packageProductsRepository.deleteAll(packageProducts);
				packageRepository.deleteById(body.get("pakage"));
				return true;
			}
		}
		
		return false;
		
	}
}
