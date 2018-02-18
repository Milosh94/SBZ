package com.project.sbz.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "bill_discount")
public class BillDiscount implements Serializable{

	private static final long serialVersionUID = -459522659269803449L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "bill_discount_code", unique = true)
	private String billDiscountCode;
	
	@JoinColumn(nullable = false, name = "bill_id")
	@ManyToOne
	private Bill bill;
	
	private double discountPercentage;
	
	private boolean discountType;
	
	public BillDiscount(){
		
	}

	public BillDiscount(int id, String billDiscountCode, Bill bill, double discountPercentage, boolean discountType) {
		super();
		this.id = id;
		this.billDiscountCode = billDiscountCode;
		this.bill = bill;
		this.discountPercentage = discountPercentage;
		this.discountType = discountType;
	}

	public BillDiscount(Bill bill, double discountPercentage, boolean discountType) {
		super();
		this.bill = bill;
		this.discountPercentage = discountPercentage;
		this.discountType = discountType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillDiscountCode() {
		return billDiscountCode;
	}

	public void setBillDiscountCode(String billDiscountCode) {
		this.billDiscountCode = billDiscountCode;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public boolean isDiscountType() {
		return discountType;
	}

	public void setDiscountType(boolean discountType) {
		this.discountType = discountType;
	}
	
	@PrePersist
	private void prePersist(){
		if(this.billDiscountCode == null){
			this.billDiscountCode = System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
		}
	}
}
