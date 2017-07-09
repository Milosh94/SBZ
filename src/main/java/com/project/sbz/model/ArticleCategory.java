package com.project.sbz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "article_category")
public class ArticleCategory implements Serializable{

	private static final long serialVersionUID = 527212276719447332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "article_code", unique = true)
	private String articleCategoryCode;
	
	@JsonManagedReference
	@JoinColumn(name = "parent_category_id", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private ArticleCategory parentCategory;
	
	private String name;
	
	@Column(name = "allowed_discount_percentage")
	private double allowedDiscountPercentage;
	
	@JsonBackReference
	@OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
	private Set<ArticleCategory> childCategories;
	
	@JsonBackReference
	@OneToMany(mappedBy = "articleCategory", fetch = FetchType.LAZY)
	private Set<Article> categoryArticles;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "articleCategories")
	private Set<DiscountEvent> discountEvents;
	
	public ArticleCategory() {
		
	}

	public ArticleCategory(int id, String articleCode, ArticleCategory parentCategory, String name,
			double allowedDiscountPercentage, Set<ArticleCategory> childCategories) {
		super();
		this.id = id;
		this.articleCategoryCode = articleCode;
		this.parentCategory = parentCategory;
		this.name = name;
		this.allowedDiscountPercentage = allowedDiscountPercentage;
		this.childCategories = childCategories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArticleCode() {
		return articleCategoryCode;
	}

	public void setArticleCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}

	public ArticleCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ArticleCategory parentCategory) {
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

	public Set<ArticleCategory> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(Set<ArticleCategory> childCategories) {
		this.childCategories = childCategories;
	}

	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}

	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}

	public Set<Article> getCategoryArticles() {
		return categoryArticles;
	}

	public void setCategoryArticles(Set<Article> categoryArticles) {
		this.categoryArticles = categoryArticles;
	}

	public Set<DiscountEvent> getDiscountEvents() {
		return discountEvents;
	}

	public void setDiscountEvents(Set<DiscountEvent> discountEvents) {
		this.discountEvents = discountEvents;
	}
	
	
}
