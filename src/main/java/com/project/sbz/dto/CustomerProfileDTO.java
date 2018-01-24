package com.project.sbz.dto;

public class CustomerProfileDTO {

	private String deliveryAddress;
	
	private double rewardPoints;
	
	private CustomerCategoryDTO customerCategoryDTO;
	
	public CustomerProfileDTO(){
		
	}

	public CustomerProfileDTO(String deliveryAddress, double rewardPoints, CustomerCategoryDTO customerCategoryDTO) {
		super();
		this.deliveryAddress = deliveryAddress;
		this.rewardPoints = rewardPoints;
		this.customerCategoryDTO = customerCategoryDTO;
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

	public void setRewardPoints(double rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public CustomerCategoryDTO getCustomerCategoryDTO() {
		return customerCategoryDTO;
	}

	public void setCustomerCategoryDTO(CustomerCategoryDTO customerCategoryDTO) {
		this.customerCategoryDTO = customerCategoryDTO;
	}
	
	
}
