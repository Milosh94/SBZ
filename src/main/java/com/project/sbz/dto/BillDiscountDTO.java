package com.project.sbz.dto;

public class BillDiscountDTO {

	private String billDiscountCode;
	
	private double discountPercentage;
	
	private boolean discountType;
	
	public BillDiscountDTO(){
		
	}

	public BillDiscountDTO(String billDiscountCode, double discountPercentage, boolean discountType) {
		super();
		this.billDiscountCode = billDiscountCode;
		this.discountPercentage = discountPercentage;
		this.discountType = discountType;
	}

	public String getBillDiscountCode() {
		return billDiscountCode;
	}

	public void setBillDiscountCode(String billDiscountCode) {
		this.billDiscountCode = billDiscountCode;
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
