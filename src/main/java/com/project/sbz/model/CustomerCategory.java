package com.project.sbz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "customer_category")
public class CustomerCategory implements Serializable{

	private static final long serialVersionUID = 93213141151275935L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "category_code", unique = true)
	private String categoryCode;
	
	private String name;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customerCategory")
	private Set<ConsumptionThreshold> consumptionThresholds;
	
	@JsonBackReference
	@OneToMany(mappedBy = "customerCategory", fetch = FetchType.LAZY)
	private Set<CustomerProfile> customerProfiles;
	
	public CustomerCategory() {
		
	}

	public CustomerCategory(int id, String categoryCode, String name, Set<ConsumptionThreshold> consumptionThresholds) {
		super();
		this.id = id;
		this.categoryCode = categoryCode;
		this.name = name;
		this.consumptionThresholds = consumptionThresholds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ConsumptionThreshold> getConsumptionThresholds() {
		return consumptionThresholds;
	}

	public void setConsumptionThresholds(Set<ConsumptionThreshold> consumptionThresholds) {
		this.consumptionThresholds = consumptionThresholds;
	}

	public Set<CustomerProfile> getCustomerProfiles() {
		return customerProfiles;
	}

	public void setCustomerProfiles(Set<CustomerProfile> customerProfiles) {
		this.customerProfiles = customerProfiles;
	}
	
	
}
