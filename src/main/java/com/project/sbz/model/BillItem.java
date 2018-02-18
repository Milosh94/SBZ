package com.project.sbz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bill_item")
public class BillItem implements Serializable{

	private static final long serialVersionUID = -8949007901255943460L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "item_number")
	private int itemNumber;
	
	@JoinColumn(nullable = false, name = "bill_id")
	@ManyToOne
	private Bill bill;
	
	@JoinColumn(nullable = false, name = "article_id")
	@ManyToOne
	private Article article;
	
	private double price;
	
	private int count;
	
	@Column(name = "total_original_price")
	private double totalOriginalPrice;
	
	@Column(name = "total_discount_price")
	private double totalDiscountPrice;
	
	@Column(name = "discount_percentage")
	private double discountPercentage;
	
	@OneToMany(mappedBy = "item", cascade =  CascadeType.PERSIST)
	private Set<BillItemDiscount> itemDiscounts;
	
	public BillItem() {
		
	}

	public BillItem(int id, int itemNumber, Bill bill, Article article, double price, int count,
			double totalOriginalPrice, double totalDiscountPrice, double discountPercentage,
			Set<BillItemDiscount> itemDiscounts) {
		super();
		this.id = id;
		this.itemNumber = itemNumber;
		this.bill = bill;
		this.article = article;
		this.price = price;
		this.count = count;
		this.totalOriginalPrice = totalOriginalPrice;
		this.totalDiscountPrice = totalDiscountPrice;
		this.discountPercentage = discountPercentage;
		this.itemDiscounts = itemDiscounts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public Set<BillItemDiscount> getItemDiscounts() {
		return itemDiscounts;
	}

	public void setItemDiscounts(Set<BillItemDiscount> itemDiscounts) {
		this.itemDiscounts = itemDiscounts;
	}
	
	public void addItemDiscount(BillItemDiscount itemDiscount){
		this.itemDiscounts.add(itemDiscount);
	}
}
