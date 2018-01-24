package com.project.sbz.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.sbz.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userService.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    	grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
	}

}
