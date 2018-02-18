package com.project.sbz.dto;

public class ShoppingCartItemDTO {

	private String articleCode;
	
	private int quantity;
	
	public ShoppingCartItemDTO(){
		
	}

	public ShoppingCartItemDTO(String articleCode, int quantity) {
		super();
		this.articleCode = articleCode;
		this.quantity = quantity;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
