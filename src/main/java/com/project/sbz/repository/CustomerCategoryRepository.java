package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.CustomerCategory;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long>{

	public CustomerCategory findByName(String name);
	
	public CustomerCategory findByCategoryCode(String code);
}
