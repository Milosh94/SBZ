package com.project.sbz.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sbz.model.CustomerCategory;
import com.project.sbz.model.CustomerProfile;
import com.project.sbz.repository.CustomerCategoryRepository;
import com.project.sbz.repository.CustomerProfileRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerProfileRepository customerProfileRepository;
	
	@Autowired
	private CustomerCategoryRepository customerCategoryRepository;
	
	@Transactional
	public CustomerProfile saveCustomerProfile(CustomerProfile cp){
		return this.customerProfileRepository.save(cp);
	}
	
	@Transactional
	public CustomerCategory findCustomerCategoryByName(String name){
		return this.customerCategoryRepository.findByName(name);
	}
	
}
