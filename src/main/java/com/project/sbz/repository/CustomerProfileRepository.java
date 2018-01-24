package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long>{

}
