package com.project.sbz.dto;

import java.util.List;

public class ShoppingCartDTO {
	
	private int rewardPoints;
	
	private List<ShoppingCartItemDTO> items;
	
	public ShoppingCartDTO(){
		
	}

	public ShoppingCartDTO(int rewardPoints, List<ShoppingCartItemDTO> items) {
		super();
		this.rewardPoints = rewardPoints;
		this.items = items;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public List<ShoppingCartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartItemDTO> items) {
		this.items = items;
	}
}
