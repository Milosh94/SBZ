package com.project.sbz.util;

import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.project.sbz.model.Article;

public final class SearchCriteriaSpecification {

	public static Specification<Article> searchCriteria(String articleId, String articleName, Double priceFrom, Double priceTo, List<String> categories){
		return (root, query, cb) -> {
			Specifications<Article> s;
			s = Specifications.where(joinCategory(categories));
			s = s.and(SpecificationFactory.equals("status", true));
			if(articleId != null && !articleId.isEmpty()){
				s = s.and(SpecificationFactory.containsLike("articleCode", articleId));
			}
			if(articleName != null && !articleName.isEmpty()){
				s = s.and(SpecificationFactory.containsLike("name", articleName));
			}
			if(priceFrom != null){
				s = s.and(SpecificationFactory.isGreater("price", priceFrom));
			}
			if(priceTo != null){
				s = s.and(SpecificationFactory.isLower("price", priceTo));
			}
			return s.toPredicate(root, query, cb);
		};
	}
	
	public static Specification<Article> joinCategory(List<String> categories){
		return (root, query, cb) -> {
			if(categories == null || categories.size() == 0){
				return null;
			}
			Path<Object> path = root.join("articleCategory").get("articleCategoryCode");
			Predicate p = cb.equal(path, categories.get(0));
			for(String c : categories.subList(1, categories.size())){
				p = cb.or(p, cb.equal(path, c));
			}
			return p;
		};
	}
}
