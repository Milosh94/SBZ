package com.project.sbz.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.dto.DiscountDTO;
import com.project.sbz.model.Article;
import com.project.sbz.repository.ArticleRepository;
import com.project.sbz.util.SearchCriteriaSpecification;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Transactional
	public Page<ArticleDTO> findAll(String articleId, String articleName, Double priceFrom, Double priceTo, List<String> categories, Pageable pageable){
		Page<Article> page = this.articleRepository.findAll(SearchCriteriaSpecification.searchCriteria(articleId, articleName, priceFrom, priceTo, categories), pageable);
		long totalElements = page.getTotalElements();
		Date now = new Date();
		return new PageImpl<ArticleDTO>(page.getContent()
				.stream()
				.map(article -> new ArticleDTO(
						article.getArticleCode(),
						article.getName(),
						article.getArticleCategory().getArticleCategoryCode(),
						article.getArticleCategory().getName(),
						article.getPrice(),
						article.getArticleCount(), 
						article.getArticleCategory().getDiscountEvents()
						.stream()
						.filter(discount -> discount.getDateFrom().compareTo(now) == -1 && discount.getDateTo().compareTo(now) == 1)
						.map(discount -> new DiscountDTO(
								discount.getEventCode(),
								discount.getName(),
								discount.getDateFrom(),
								discount.getDateTo(),
								discount.getDiscountPercentage(),
								null))
						.findAny()
						.orElse(null)))
				.collect(Collectors.toList()), pageable, totalElements);
	}
}
