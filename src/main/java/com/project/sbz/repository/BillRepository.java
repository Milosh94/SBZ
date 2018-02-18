package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
	public Bill findByBillCode(String code);
}
