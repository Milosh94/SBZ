package com.project.sbz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sbz.model.CustomerCategory;
import com.project.sbz.repository.CustomerCategoryRepository;

@Service
public class ManagerService {

	@Autowired
	CustomerCategoryRepository customerCategoryRepository;
	
	@Transactional
	public List<CustomerCategory> findAll(){
		return this.customerCategoryRepository.findAll();
	}
	
	@Transactional
	public CustomerCategory findByCategoryCode(String code){
		return this.customerCategoryRepository.findByCategoryCode(code);
	}
	
	@Transactional
	public CustomerCategory save(CustomerCategory cc){
		return this.customerCategoryRepository.save(cc);
	}
}
