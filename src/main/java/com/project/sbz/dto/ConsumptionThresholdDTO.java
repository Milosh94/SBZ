package com.project.sbz.dto;

public class ConsumptionThresholdDTO {

	private double priceFrom;
	
	private double priceTo;
	
	private double rewardPointsPercentage;
	
	public ConsumptionThresholdDTO(){
		
	}

	public ConsumptionThresholdDTO(double priceFrom, double priceTo, double rewardPointsPercentage) {
		super();
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.rewardPointsPercentage = rewardPointsPercentage;
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

	public double getRewardPointsPercentage() {
		return rewardPointsPercentage;
	}

	public void setRewardPointsPercentage(double rewardPointsPercentage) {
		this.rewardPointsPercentage = rewardPointsPercentage;
	}
	
	
}
