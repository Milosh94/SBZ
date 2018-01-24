package com.project.sbz.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.sbz.serializer.JsonDateWithoutTimeDeserializer;
import com.project.sbz.serializer.JsonDateWithoutTimeSerializer;

public class DiscountDTO {
	
	private String eventCode;
	
	private String name;
	
	@JsonDeserialize(using = JsonDateWithoutTimeDeserializer.class)
	@JsonSerialize(using = JsonDateWithoutTimeSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	
	@JsonDeserialize(using = JsonDateWithoutTimeDeserializer.class)
	@JsonSerialize(using = JsonDateWithoutTimeSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;

	private double discountPercentage;
	
	private List<ArticleCategoryDTO> articleCategories;
	
	public DiscountDTO(){
		
	}

	public DiscountDTO(String eventCode, String name, Date dateFrom, Date dateTo, double discountPercentage, List<ArticleCategoryDTO> articleCategories) {
		super();
		this.eventCode = eventCode;
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.discountPercentage = discountPercentage;
		this.articleCategories = articleCategories;
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

	public List<ArticleCategoryDTO> getArticleCategories() {
		return articleCategories;
	}

	public void setArticleCategories(List<ArticleCategoryDTO> articleCategories) {
		this.articleCategories = articleCategories;
	}
}
