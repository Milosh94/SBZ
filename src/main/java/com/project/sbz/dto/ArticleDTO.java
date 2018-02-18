package com.project.sbz.dto;

public class ArticleDTO {
	
	private String articleCode;
	
	private String name;
	
	private String articleCategoryCode;
	
	private String articleCategoryName;
	
	private double price;
	
	private int articleCount;
	
	private int minimalCount;
	
	private DiscountDTO discount;
	
	public ArticleDTO(){
		this.minimalCount = 0;
	}

	public ArticleDTO(String articleCode, String name, String articleCategoryCode, String articleCategoryName,
			double price, int articleCount, DiscountDTO discount) {
		super();
		this.articleCode = articleCode;
		this.name = name;
		this.articleCategoryCode = articleCategoryCode;
		this.articleCategoryName = articleCategoryName;
		this.price = price;
		this.articleCount = articleCount;
		this.minimalCount = 0;
		this.discount = discount;
	}
	
	

	public ArticleDTO(String articleCode, String name, String articleCategoryCode, String articleCategoryName,
			double price, int articleCount, int minimalCount, DiscountDTO discount) {
		super();
		this.articleCode = articleCode;
		this.name = name;
		this.articleCategoryCode = articleCategoryCode;
		this.articleCategoryName = articleCategoryName;
		this.price = price;
		this.articleCount = articleCount;
		this.minimalCount = minimalCount;
		this.discount = discount;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}

	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}

	public String getArticleCategoryName() {
		return articleCategoryName;
	}

	public void setArticleCategoryName(String articleCategoryName) {
		this.articleCategoryName = articleCategoryName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public DiscountDTO getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDTO discount) {
		this.discount = discount;
	}

	public int getMinimalCount() {
		return minimalCount;
	}

	public void setMinimalCount(int minimalCount) {
		this.minimalCount = minimalCount;
	}
}
