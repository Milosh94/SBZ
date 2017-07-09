package com.project.sbz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateSerializer;

@Entity
@Table(name = "discount_event")
public class DiscountEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8144127192887654957L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "event_code", unique = true)
	private String eventCode;
	
	private String name;
	
	@Column(name = "date_from")
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.DATE)
	private Date dateFrom;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "date_to")
	private Date dateTo;
	
	@Column(name = "discount_percentage")
	private double discountPercentage;
	
	@JsonManagedReference
	@JoinTable(name = "discount_event_article_category", joinColumns = @JoinColumn(name = "discount_event_id"), inverseJoinColumns = @JoinColumn(name = "article_category_id"))
	@ManyToMany
	private Set<ArticleCategory> articleCategories;
	
	public DiscountEvent() {
		
	}

	public DiscountEvent(int id, String eventCode, String name, Date dateFrom, Date dateTo, double discountPercentage,
			Set<ArticleCategory> articleCategories) {
		super();
		this.id = id;
		this.eventCode = eventCode;
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.discountPercentage = discountPercentage;
		this.articleCategories = articleCategories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Set<ArticleCategory> getArticleCategories() {
		return articleCategories;
	}

	public void setArticleCategories(Set<ArticleCategory> articleCategories) {
		this.articleCategories = articleCategories;
	}
	
	
}
