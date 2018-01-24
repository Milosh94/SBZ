package com.project.sbz.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateSerializer;

public class UserDTO {

	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String role;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	public UserDTO(String username, String firstName, String lastName, String role, Date registerDate) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.registerDate = registerDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	
}
