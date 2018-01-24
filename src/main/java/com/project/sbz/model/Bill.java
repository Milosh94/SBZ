package com.project.sbz.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateSerializer;

@Entity
public class Bill implements Serializable{
	
	private static final long serialVersionUID = -65399995078795935L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "bill_code", unique = true)
	private String billCode;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id", nullable = false)
	private CustomerProfile buyer;
	
	private int status;
	
	@Column(name = "original_cost")
	private double originalCost;
	
	@Column(name = "discount_percentage")
	private double discountPercentage;

	@Column(name = "final_cost")
	private double finalCost;
	
	@Column(name = "reward_points_spent")
	private int rewardPointsSpent;
	
	@Column(name = "reward_point_gained")
	private int rewardPointsGained;
	
	@OneToMany(mappedBy = "bill")
	private Set<BillDiscount> billDiscounts;
	
	@OneToMany(mappedBy = "bill")
	private Set<BillItem> billItems;
	
	public Bill() {
		
	}

	public Bill(int id, String billCode, Date created, CustomerProfile buyer, int status, double originalCost,
			double discountPercentage, double finalCost, int rewardPointsSpent, int rewardPointsGained,
			Set<BillDiscount> billDiscounts, Set<BillItem> billItems) {
		super();
		this.id = id;
		this.billCode = billCode;
		this.created = created;
		this.buyer = buyer;
		this.status = status;
		this.originalCost = originalCost;
		this.discountPercentage = discountPercentage;
		this.finalCost = finalCost;
		this.rewardPointsSpent = rewardPointsSpent;
		this.rewardPointsGained = rewardPointsGained;
		this.billDiscounts = billDiscounts;
		this.billItems = billItems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public CustomerProfile getBuyer() {
		return buyer;
	}

	public void setBuyer(CustomerProfile buyer) {
		this.buyer = buyer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(double originalCost) {
		this.originalCost = originalCost;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(double finalCost) {
		this.finalCost = finalCost;
	}

	public int getRewardPointsSpent() {
		return rewardPointsSpent;
	}

	public void setRewardPointsSpent(int rewardPointsSpent) {
		this.rewardPointsSpent = rewardPointsSpent;
	}

	public int getRewardPointsGained() {
		return rewardPointsGained;
	}

	public void setRewardPointsGained(int rewardPointsGained) {
		this.rewardPointsGained = rewardPointsGained;
	}

	public Set<BillDiscount> getBillDiscounts() {
		return billDiscounts;
	}

	public void setBillDiscounts(Set<BillDiscount> billDiscounts) {
		this.billDiscounts = billDiscounts;
	}

	public Set<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(Set<BillItem> billItems) {
		this.billItems = billItems;
	}
	
	
}
