package com.project.sbz.dto;

public class BillItemDiscountDTO {

	private String billItemDiscountCode;
	
	private double discountPercentage;
	
	private boolean discountType;
	
	public BillItemDiscountDTO(){
		
	}

	public BillItemDiscountDTO(String billItemDiscountCode, double discountPercentage, boolean discountType) {
		super();
		this.billItemDiscountCode = billItemDiscountCode;
		this.discountPercentage = discountPercentage;
		this.discountType = discountType;
	}

	public String getBillItemDiscountCode() {
		return billItemDiscountCode;
	}

	public void setBillItemDiscountCode(String billItemDiscountCode) {
		this.billItemDiscountCode = billItemDiscountCode;
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
