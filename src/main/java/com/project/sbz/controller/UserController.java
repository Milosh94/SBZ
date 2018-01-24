package com.project.sbz.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ConsumptionThresholdDTO;
import com.project.sbz.dto.CustomerCategoryDTO;
import com.project.sbz.dto.CustomerProfileDTO;
import com.project.sbz.dto.LoginDTO;
import com.project.sbz.dto.RegisterDTO;
import com.project.sbz.dto.RoleDTO;
import com.project.sbz.dto.UserDTO;
import com.project.sbz.model.CustomerProfile;
import com.project.sbz.model.Role;
import com.project.sbz.model.User;
import com.project.sbz.security.TokenUtils;
import com.project.sbz.service.CustomerService;
import com.project.sbz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
		//Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
		//Cookie cookie1 = WebUtils.getCookie(response, "XSRF-TOKEN");
		String responseValue = "";
		try{
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDTO.getUsername());
			responseValue = "{\"token\":\"" + this.tokenUtils.generateToken(userDetails) + "\"}";
			return new ResponseEntity<String>(responseValue, HttpStatus.OK);
		}catch(Exception e){
			responseValue = "{\"error\":\"Invalid login\"}";
			return new ResponseEntity<String>(responseValue, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user")
	public ResponseEntity<UserDTO> getLoggedUser(Principal userPrincipal){
		if(userPrincipal == null){
			return new ResponseEntity<UserDTO>(HttpStatus.FORBIDDEN);
		}
		String username = userPrincipal.getName();
		User user = this.userService.findByUsername(username);
		UserDTO userDTO = new UserDTO(username, user.getFirstName(), user.getLastName(), user.getRole().getName(), user.getRegisterDate());
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@GetMapping("/role")
	public ResponseEntity<List<RoleDTO>> getRoles(){
		List<Role> roles = new ArrayList<>();
		roles = this.userService.findAllRoles();
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for(Role r: roles){
			rolesDTO.add(new RoleDTO(r.getName()));
		}
		return new ResponseEntity<List<RoleDTO>>(rolesDTO, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
		String responseValue;
		if(this.userService.findByUsername(registerDTO.getUsername()) != null){
			responseValue = "{\"error\":\"Username not unique\"}";
			return new ResponseEntity<String>(responseValue, HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setUsername(registerDTO.getUsername());
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setRegisterDate(new Date());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		Role role = this.userService.findRoleByName(registerDTO.getRole());
		if(role == null){
			responseValue = "{\"error\":\"Role not valid\"}";
			return new ResponseEntity<String>(responseValue, HttpStatus.BAD_REQUEST);
		}
		user.setRole(role);
		if(this.userService.save(user) == null){
			responseValue = "{\"error\":\"User not valid\"}";
			return new ResponseEntity<String>(responseValue, HttpStatus.BAD_REQUEST);
		}
		if(user.getRole().getName().equals("ROLE_CUSTOMER")){
			CustomerProfile customerProfile = new CustomerProfile();
			customerProfile.setUser(user);
			customerProfile.setCustomerCategory(this.customerService.findCustomerCategoryByName("Regular"));
			customerProfile.setDeliveryAddress(registerDTO.getDeliveryAddress());
			this.customerService.saveCustomerProfile(customerProfile);
		}
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(registerDTO.getUsername());
		responseValue = "{\"token\":\"" + this.tokenUtils.generateToken(userDetails) + "\"}";
		return new ResponseEntity<String>(responseValue, HttpStatus.OK);
	}
	
	@GetMapping("/username-unique")
	public ResponseEntity<Boolean> usernameUnique(@RequestParam("username") String username){
		if(this.userService.findByUsername(username) != null){
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@GetMapping("/user-profile")
	public ResponseEntity<CustomerProfileDTO> getUserProfile(Principal userPrincipal){
		if(userPrincipal == null){
			return new ResponseEntity<CustomerProfileDTO>(HttpStatus.FORBIDDEN);
		}
		String username = userPrincipal.getName();
		User user = this.userService.findByUsername(username);
		List<ConsumptionThresholdDTO> consumptionThresholds = user.getCustomerProfile().getCustomerCategory().getConsumptionThresholds().stream().map(threshold -> new ConsumptionThresholdDTO(threshold.getPriceFrom(), threshold.getPriceTo(), threshold.getRewardPointsPercentage())).collect(Collectors.toList());
		CustomerCategoryDTO customerCategoryDTO = new CustomerCategoryDTO(user.getCustomerProfile().getCustomerCategory().getCategoryCode(),
				user.getCustomerProfile().getCustomerCategory().getName(),
				consumptionThresholds);
		CustomerProfileDTO profile = new CustomerProfileDTO(user.getCustomerProfile().getDeliveryAddress(),
				user.getCustomerProfile().getRewardPoints(),
				customerCategoryDTO);
		return new ResponseEntity<CustomerProfileDTO>(profile, HttpStatus.OK);
	}
}
