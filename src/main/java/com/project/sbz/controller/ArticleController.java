package com.project.sbz.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.dto.DiscountDTO;
import com.project.sbz.model.Article;
import com.project.sbz.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/articles")
	public ResponseEntity<List<ArticleDTO>> getAllArticles(){
		Date now = new Date();
		List<ArticleDTO> articles = this.articleService.findAllArticles().stream()
				.map(article -> new ArticleDTO(
						article.getArticleCode(),
						article.getName(),
						article.getArticleCategory().getArticleCategoryCode(),
						article.getArticleCategory().getName(),
						article.getPrice(),
						article.getArticleCount(),
						article.getMinimalArticleCount(),
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
				.collect(Collectors.toList());
		return new ResponseEntity<List<ArticleDTO>>(articles, HttpStatus.OK);
	}
	
	@GetMapping("/order-articles")
	public ResponseEntity<List<ArticleDTO>> getOrderArticles(){
		Date now = new Date();
		List<ArticleDTO> articles = this.articleService.getOrderArticles().stream()
				.map(article -> new ArticleDTO(
						article.getArticleCode(),
						article.getName(),
						article.getArticleCategory().getArticleCategoryCode(),
						article.getArticleCategory().getName(),
						article.getPrice(),
						article.getArticleCount(), 
						article.getMinimalArticleCount(), 
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
				.collect(Collectors.toList());;
		return new ResponseEntity<List<ArticleDTO>>(articles, HttpStatus.OK);
	}
	
	@PutMapping("/order-articles")
	public ResponseEntity<ArticleDTO> updateQuantity(@RequestParam("code") String code, @RequestParam("quantity") int quantity){
		Article article = this.articleService.findByArticleCode(code);
		if(article == null || quantity <= 0){
			return new ResponseEntity<ArticleDTO>(HttpStatus.BAD_REQUEST);
		}
		article.setArticleCount(article.getArticleCount() + quantity);
		if(article.getArticleCount() >= article.getMinimalArticleCount()){
			article.setOrderMoreArticles(false);
		}
		this.articleService.save(article);
		return new ResponseEntity<ArticleDTO>(HttpStatus.OK);
	}
}
