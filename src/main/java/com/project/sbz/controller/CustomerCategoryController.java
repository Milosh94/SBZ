package com.project.sbz.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ConsumptionThresholdDTO;
import com.project.sbz.dto.CustomerCategoryDTO;
import com.project.sbz.model.ConsumptionThreshold;
import com.project.sbz.model.CustomerCategory;
import com.project.sbz.service.ManagerService;
import com.project.sbz.service.UserService;

@RestController
@RequestMapping("/api")
public class CustomerCategoryController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ManagerService managerService;
	
	@GetMapping("/customer-category")
	public ResponseEntity<List<CustomerCategoryDTO>> getCustomerCategories(){
		List<CustomerCategoryDTO> customerCategories = this.managerService.findAll()
				.stream()
				.map(cat -> new CustomerCategoryDTO(
						cat.getCategoryCode(),
						cat.getName(),
						cat.getConsumptionThresholds().stream().map(treshold -> new ConsumptionThresholdDTO(treshold.getPriceFrom(), treshold.getPriceTo(), treshold.getRewardPointsPercentage())).collect(Collectors.toList())))
				.collect(Collectors.toList());
		return new ResponseEntity<List<CustomerCategoryDTO>>(customerCategories, HttpStatus.OK);
	}
	
	@PostMapping("/customer-category")
	public ResponseEntity<String> updateCategoryThresholds(@RequestBody ConsumptionThresholdDTO[] thresholds, @RequestParam("code") String code){
		System.out.println(code);
		System.out.println(thresholds.length);
		CustomerCategory category = this.managerService.findByCategoryCode(code);
		System.out.println(category.getName());
		Set<ConsumptionThreshold> updatedThresholds = Arrays.stream(thresholds)
		.map(t -> {
			ConsumptionThreshold ct = new ConsumptionThreshold();
			ct.setPriceFrom(t.getPriceFrom());
			ct.setPriceTo(t.getPriceTo());
			ct.setRewardPointsPercentage(t.getRewardPointsPercentage());
			ct.setCustomerCategory(category);
			return ct;})
		.collect(Collectors.toSet());
		category.getConsumptionThresholds().clear();
		category.getConsumptionThresholds().addAll(updatedThresholds);
		CustomerCategory savedCategory = this.managerService.save(category);
		System.out.println(savedCategory.getConsumptionThresholds().size());
		String responseValue = "{\"status\":\"Updated\"}";
		return new ResponseEntity<String>(responseValue, HttpStatus.OK);
	}
}
