package com.project.sbz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_item_discount")
public class BillItemDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4386569916917655273L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "bill_item_discount_code")
	private String billItemDiscountCode;
	
	@JoinColumn(name = "item_id", nullable = false)
	@ManyToOne
	private BillItem item;
	
	@Column(name = "discount_percentage")
	private double discountPercentage;
	
	@Column(name = "discount_type")
	private boolean discountType;
	
	public BillItemDiscount(){
		
	}

	public BillItemDiscount(int id, String billItemDiscountCode, BillItem item, double discountPercentage,
			boolean discountType) {
		super();
		this.id = id;
		this.billItemDiscountCode = billItemDiscountCode;
		this.item = item;
		this.discountPercentage = discountPercentage;
		this.discountType = discountType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillItemDiscountCode() {
		return billItemDiscountCode;
	}

	public void setBillItemDiscountCode(String billItemDiscountCode) {
		this.billItemDiscountCode = billItemDiscountCode;
	}

	public BillItem getItem() {
		return item;
	}

	public void setItem(BillItem item) {
		this.item = item;
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
	
	
}
