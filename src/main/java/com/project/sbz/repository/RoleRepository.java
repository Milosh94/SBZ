package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	public Role findByName(String name);
}
