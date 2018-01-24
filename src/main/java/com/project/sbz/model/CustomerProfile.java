package com.project.sbz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "customer_profile")
public class CustomerProfile implements Serializable{
	
	private static final long serialVersionUID = 1196825479760542930L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "delivery_address")
	private String deliveryAddress;
	
	@Column(name = "reward_points")
	private int rewardPoints;
	
	@JsonManagedReference
	@JoinColumn(nullable = false, name = "customer_category")
	@ManyToOne(fetch = FetchType.LAZY)
	private CustomerCategory customerCategory;
	
	@JsonBackReference
	@OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	private Set<Bill> successfulBuyings;
	
	@JsonManagedReference
	@JoinColumn(nullable = false, name = "customer_user")
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	public CustomerProfile() {
		
	}

	public CustomerProfile(int id, String deliveryAddress, int rewardPoints, CustomerCategory customerCategory,
			Set<Bill> successfulBuyings, User user) {
		super();
		this.id = id;
		this.deliveryAddress = deliveryAddress;
		this.rewardPoints = rewardPoints;
		this.customerCategory = customerCategory;
		this.successfulBuyings = successfulBuyings;
		this.user = user;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public double getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}

	public Set<Bill> getSuccessfulBuyings() {
		return successfulBuyings;
	}

	public void setSuccessfulBuyings(Set<Bill> successfulBuyings) {
		this.successfulBuyings = successfulBuyings;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
