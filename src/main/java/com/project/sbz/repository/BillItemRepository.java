package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.BillItem;

public interface BillItemRepository extends JpaRepository<BillItem, Long>{

}
