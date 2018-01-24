package com.project.sbz.dto;

public class ArticleCategoryDTO {

	public String articleCategoryCode;
	
	public String name;
	
	public double allowedDiscountPercentage;
	
	public ArticleCategoryDTO(){
		
	}

	public ArticleCategoryDTO(String articleCategoryCode, String name, double allowedDiscountPercentage) {
		super();
		this.articleCategoryCode = articleCategoryCode;
		this.name = name;
		this.allowedDiscountPercentage = allowedDiscountPercentage;
	}

	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}

	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAllowedDiscountPercentage() {
		return allowedDiscountPercentage;
	}

	public void setAllowedDiscountPercentage(double allowedDiscountPercentage) {
		this.allowedDiscountPercentage = allowedDiscountPercentage;
	}
}
