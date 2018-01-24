package com.project.sbz.dto;

import java.util.List;

public class BillItemDTO {

	private int itemNumber;
	
	private double price;
	
	private ArticleDTO articleDTO;
	
	private int count;
	
	private double totalOriginalPrice;
	
	private double totalDiscountPrice;
	
	private double discountPercentage;
	
	private List<BillItemDiscountDTO> itemDiscountsDTO;
	
	public BillItemDTO(){
		
	}

	public BillItemDTO(int itemNumber, double price, ArticleDTO articleDTO, int count, double totalOriginalPrice,
			double totalDiscountPrice, double discountPercentage, List<BillItemDiscountDTO> itemDiscountsDTO) {
		super();
		this.itemNumber = itemNumber;
		this.price = price;
		this.articleDTO = articleDTO;
		this.count = count;
		this.totalOriginalPrice = totalOriginalPrice;
		this.totalDiscountPrice = totalDiscountPrice;
		this.discountPercentage = discountPercentage;
		this.itemDiscountsDTO = itemDiscountsDTO;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArticleDTO getArticleDTO() {
		return articleDTO;
	}

	public void setArticleDTO(ArticleDTO articleDTO) {
		this.articleDTO = articleDTO;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotalOriginalPrice() {
		return totalOriginalPrice;
	}

	public void setTotalOriginalPrice(double totalOriginalPrice) {
		this.totalOriginalPrice = totalOriginalPrice;
	}

	public double getTotalDiscountPrice() {
		return totalDiscountPrice;
	}

	public void setTotalDiscountPrice(double totalDiscountPrice) {
		this.totalDiscountPrice = totalDiscountPrice;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public List<BillItemDiscountDTO> getItemDiscountsDTO() {
		return itemDiscountsDTO;
	}

	public void setItemDiscountsDTO(List<BillItemDiscountDTO> itemDiscountsDTO) {
		this.itemDiscountsDTO = itemDiscountsDTO;
	}
}
