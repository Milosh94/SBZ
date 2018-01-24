package com.project.sbz.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateSerializer;

public class BillDTO {

	private String billCode;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	private int status;
	
	private double originalCost;
	
	private double discountPercentage;
	
	private double finalCost;
	
	private int rewardPointsSpent;
	
	private int rewardPointsGained;
	
	private List<BillDiscountDTO> billDiscountsDTO;
	
	private List<BillItemDTO> billItemsDTO;
	
	public BillDTO(){
		
	}

	public BillDTO(String billCode, Date created, int status, double originalCost, double discountPercentage,
			double finalCost, int rewardPointsSpent, int rewardPointsGained, List<BillDiscountDTO> billDiscountsDTO,
			List<BillItemDTO> billItemsDTO) {
		super();
		this.billCode = billCode;
		this.created = created;
		this.status = status;
		this.originalCost = originalCost;
		this.discountPercentage = discountPercentage;
		this.finalCost = finalCost;
		this.rewardPointsSpent = rewardPointsSpent;
		this.rewardPointsGained = rewardPointsGained;
		this.billDiscountsDTO = billDiscountsDTO;
		this.billItemsDTO = billItemsDTO;
	}

	public List<BillDiscountDTO> getBillDiscountsDTO() {
		return billDiscountsDTO;
	}

	public void setBillDiscountsDTO(List<BillDiscountDTO> billDiscountsDTO) {
		this.billDiscountsDTO = billDiscountsDTO;
	}

	public List<BillItemDTO> getBillItemsDTO() {
		return billItemsDTO;
	}

	public void setBillItemsDTO(List<BillItemDTO> billItemsDTO) {
		this.billItemsDTO = billItemsDTO;
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
}
