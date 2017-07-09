package com.project.sbz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "consumption_threshold")
public class ConsumptionThreshold implements Serializable{

	private static final long serialVersionUID = -8065965492255317357L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "price_from")
	private double priceFrom;
	
	@Column(name = "price_to")
	private double priceTo;
	
	@JsonManagedReference
	@JoinColumn(name = "customer_category", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private CustomerCategory customerCategory;
	
	@Column(name = "reward_points_percentage")
	private double rewardPointsPercentage;
	
	public ConsumptionThreshold() {
		
	}

	public ConsumptionThreshold(int id, double priceFrom, double priceTo, CustomerCategory customerCategory,
			double rewardPointsPercentage) {
		super();
		this.id = id;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.customerCategory = customerCategory;
		this.rewardPointsPercentage = rewardPointsPercentage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}

	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
	}

	public double getRewardPointsPercentage() {
		return rewardPointsPercentage;
	}

	public void setRewardPointsPercentage(double rewardPointsPercentage) {
		this.rewardPointsPercentage = rewardPointsPercentage;
	}
	
	

}
