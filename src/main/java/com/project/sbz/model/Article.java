package com.project.sbz.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateSerializer;

@Entity
public class Article implements Serializable{

	private static final long serialVersionUID = 1303825046108128105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "article_code", unique = true)
	private String articleCode;
	
	private String name;
	
	@JsonManagedReference
	@JoinColumn(name = "article_category_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private ArticleCategory articleCategory;
	
	private double price;
	
	@Column(name = "article_count")
	private int articleCount;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "order_more_articles")
	private boolean orderMoreArticles;
	
	private boolean status;
	
	@Column(name = "minimal_article_count")
	private int minimalArticleCount;
	
	public Article() {
		
	}

	public Article(int id, String articleCode, String name, ArticleCategory articleCategory, double price,
			int articleCount, Date created, boolean orderMoreArticles, boolean status, int minimalArticleCount) {
		super();
		this.id = id;
		this.articleCode = articleCode;
		this.name = name;
		this.articleCategory = articleCategory;
		this.price = price;
		this.articleCount = articleCount;
		this.created = created;
		this.orderMoreArticles = orderMoreArticles;
		this.status = status;
		this.minimalArticleCount = minimalArticleCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isOrderMoreArticles() {
		return orderMoreArticles;
	}

	public void setOrderMoreArticles(boolean orderMoreArticles) {
		this.orderMoreArticles = orderMoreArticles;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getMinimalArticleCount() {
		return minimalArticleCount;
	}

	public void setMinimalArticleCount(int minimalArticleCount) {
		this.minimalArticleCount = minimalArticleCount;
	}
	
	
}
