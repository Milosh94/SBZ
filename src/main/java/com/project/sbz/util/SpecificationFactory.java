package com.project.sbz.util;

import java.util.Date;

import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import com.project.sbz.model.Article;

public final class SpecificationFactory {
	public static Specification<Article> containsLike(String attribute, String value){
		return (root, query, cb) -> cb.like(cb.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
	}
	
	public static Specification<Article> isBetween(String attribute, double min, double max){
		return (root, query, cb) -> cb.between(root.get(attribute), min, max);
	}
	
	public static Specification<Article> isGreater(String attribute, double value){
		return (root, query, cb) -> cb.ge(root.get(attribute), value);
	}
	
	public static Specification<Article> isLower(String attribute, double value){
		return (root, query, cb) -> cb.le(root.get(attribute), value);
	}
	
	public static Specification<Article> isLowerDate(String attribute, Date date){
		return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), date);
	}
	
	public static Specification<Article> isGreaterDate(String attribute, Date date){
		return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), date);
	}
	
	public static <T> Specification<Article> equals(String attribute, T value){
		return (root, query, cb) -> {
			Path<T> actualValue = root.get(attribute);
			if(value == null){
				return null;
			}
			return cb.equal(actualValue, value);
		};
	}
}
