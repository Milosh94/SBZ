package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.DiscountEvent;

public interface DiscountEventRepository extends JpaRepository<DiscountEvent, Long>{
	
	public int deleteByEventCode(String code);
	
	public DiscountEvent findByEventCode(String code);
}
