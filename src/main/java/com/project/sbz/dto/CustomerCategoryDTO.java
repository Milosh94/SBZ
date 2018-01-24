package com.project.sbz.dto;

import java.util.List;

public class CustomerCategoryDTO {

	private String categoryCode;
	
	private String name;
	
	private List<ConsumptionThresholdDTO> consumptionThresholds;
	
	public CustomerCategoryDTO(){
		
	}

	public CustomerCategoryDTO(String categoryCode, String name, List<ConsumptionThresholdDTO> consumptionThresholds) {
		super();
		this.categoryCode = categoryCode;
		this.name = name;
		this.consumptionThresholds = consumptionThresholds;
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

	public List<ConsumptionThresholdDTO> getConsumptionThresholds() {
		return consumptionThresholds;
	}

	public void setConsumptionThresholds(List<ConsumptionThresholdDTO> consumptionThresholds) {
		this.consumptionThresholds = consumptionThresholds;
	}
	
	
}
