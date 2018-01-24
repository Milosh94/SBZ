package com.project.sbz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sbz.model.Role;
import com.project.sbz.model.User;
import com.project.sbz.repository.RoleRepository;
import com.project.sbz.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
	
	@Transactional
	public User findByUsername(String username){
		return this.userRepository.findByUsername(username);
	}
	
	@Transactional
	public List<Role> findAllRoles(){
		return this.roleRepository.findAll();
	}
	
	@Transactional
	public Role findRoleByName(String name){
		return this.roleRepository.findByName(name);
	}
	
	@Transactional
	public User save(User u){
		return this.userRepository.save(u);
	}
}
