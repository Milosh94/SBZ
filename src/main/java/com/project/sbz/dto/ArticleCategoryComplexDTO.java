package com.project.sbz.dto;

import java.util.List;

public class ArticleCategoryComplexDTO {

	private String articleCategoryCode;
	
	private ArticleCategoryDTO parentCategory;
	
	private String name;
	
	private double allowedDiscountPercentage;
	
	private List<ArticleDTO> categoryArticles;
	
	public ArticleCategoryComplexDTO(){
		
	}

	public ArticleCategoryComplexDTO(String articleCategoryCode, ArticleCategoryDTO parentCategory, String name,
			double allowedDiscountPercentage, List<ArticleDTO> categoryArticles) {
		super();
		this.articleCategoryCode = articleCategoryCode;
		this.parentCategory = parentCategory;
		this.name = name;
		this.allowedDiscountPercentage = allowedDiscountPercentage;
		this.categoryArticles = categoryArticles;
	}

	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}

	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}

	public ArticleCategoryDTO getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ArticleCategoryDTO parentCategory) {
		this.parentCategory = parentCategory;
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

	public List<ArticleDTO> getCategoryArticles() {
		return categoryArticles;
	}

	public void setCategoryArticles(List<ArticleDTO> categoryArticles) {
		this.categoryArticles = categoryArticles;
	}
}
