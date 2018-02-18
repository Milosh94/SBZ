package com.project.sbz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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
	
	@Autowired
	private KieContainer kieContainer;
	
	@Transactional
	public Article findByArticleCode(String code){
		return this.articleRepository.findByArticleCode(code);
	}
	
	@Transactional
	public List<Article> findAllArticles(){
		return this.articleRepository.findAll().stream().filter(a -> a.isStatus()).collect(Collectors.toList());
	}
	
	@Transactional
	public Article save(Article a){
		return this.articleRepository.save(a);
	}
	
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
	
	@Transactional
	public List<Article> getOrderArticles(){
		KieSession kieSession = this.kieContainer.newKieSession("OrderArticles");
		kieSession.getAgenda().getAgendaGroup("order-articles").setFocus();
		List<Article> articles = this.findAllArticles();
		List<Article> articlesToOrder = new ArrayList<Article>();
		kieSession.insert(articlesToOrder);
		for(Article a : articles){
			kieSession.insert(a);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		System.out.println(articlesToOrder.size());
		for(Article a : articlesToOrder){
			this.articleRepository.save(a);
		}
		return articlesToOrder;
	}
}
