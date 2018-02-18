package com.project.sbz.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ArticleCategoryComplexDTO;
import com.project.sbz.dto.ArticleCategoryDTO;
import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.model.ArticleCategory;
import com.project.sbz.service.ArticleCategoryService;

@RestController
@RequestMapping("/api")
public class ArticleCategoryController {
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@GetMapping("/category")
	public ResponseEntity<List<ArticleCategoryDTO>> getCategories(){
		List<ArticleCategoryDTO> categories = this.articleCategoryService.findAll()
				.stream()
				.map(item -> new ArticleCategoryDTO(
						item.getArticleCategoryCode(),
						item.getName(),
						item.getAllowedDiscountPercentage()))
				.collect(Collectors.toList());
		return new ResponseEntity<List<ArticleCategoryDTO>>(categories, HttpStatus.OK);
	}
	
	@GetMapping("/category-no-goods")
	public ResponseEntity<List<ArticleCategoryDTO>> getCategoriesWithoutConsumerGoods(){
		List<ArticleCategoryDTO> categories = this.articleCategoryService.findAll()
				.stream()
				.filter(category -> category.getChildCategories().size() == 0)
				.map(item -> new ArticleCategoryDTO(
						item.getArticleCategoryCode(),
						item.getName(),
						item.getAllowedDiscountPercentage()))
				.collect(Collectors.toList());
		return new ResponseEntity<List<ArticleCategoryDTO>>(categories, HttpStatus.OK);
	}
	
	@GetMapping("/article-category")
	public ResponseEntity<List<ArticleCategoryComplexDTO>> getComplexCategories(){
		List<ArticleCategoryComplexDTO> categories = this.articleCategoryService.findAll()
				.stream()
				.map(cat -> new ArticleCategoryComplexDTO(
						cat.getArticleCategoryCode(),
						cat.getParentCategory() != null ? new ArticleCategoryDTO(cat.getParentCategory().getArticleCategoryCode(),
								cat.getParentCategory().getName(),
								cat.getParentCategory().getAllowedDiscountPercentage()) : null,
						cat.getName(),
						cat.getAllowedDiscountPercentage(),
						cat.getCategoryArticles()
						.stream()
						.filter(article -> article.isStatus())
						.map(art -> new ArticleDTO(art.getArticleCode(),
								art.getName(),
								null,
								null,
								art.getPrice(),
								art.getArticleCount(),
								null))
						.collect(Collectors.toList())))
				.collect(Collectors.toList());
		return new ResponseEntity<List<ArticleCategoryComplexDTO>>(categories, HttpStatus.OK);
	}
	
	@DeleteMapping("/article-category")
	public ResponseEntity<ArticleCategoryDTO> deleteArticleCategory(@RequestParam("code") String code){
		int deleted = this.articleCategoryService.deleteByCode(code);
		if(deleted == 1){
			return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.OK);
		}
		else{
			return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/article-category")
	public ResponseEntity<ArticleCategoryDTO> postArticleCategory(@RequestBody ArticleCategoryDTO postCategory, @RequestParam("isGoods") boolean isGoods){
		if(this.articleCategoryService.findByArticleCategoryCode(postCategory.getArticleCategoryCode()) != null){
			return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.CONFLICT);
		}
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setArticleCode(postCategory.getArticleCategoryCode());
		articleCategory.setName(postCategory.getName());
		articleCategory.setAllowedDiscountPercentage(postCategory.getAllowedDiscountPercentage());
		if(isGoods){
			articleCategory.setParentCategory(this.articleCategoryService.findByArticleCategoryCode("Consumer Goods"));
		}
		else{
			articleCategory.setParentCategory(null);
		}
		this.articleCategoryService.save(articleCategory);
		return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.OK);
	}
	
	@PutMapping("/article-category")
	public ResponseEntity<ArticleCategoryDTO> putArticleCategory(@RequestBody ArticleCategoryDTO postCategory, @RequestParam("isGoods") boolean isGoods){
		ArticleCategory category = this.articleCategoryService.findByArticleCategoryCode(postCategory.getArticleCategoryCode());
		if(category == null){
			return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.BAD_REQUEST);
		}
		category.setName(postCategory.getName());
		category.setAllowedDiscountPercentage(postCategory.getAllowedDiscountPercentage());
		if(isGoods){
			category.setParentCategory(this.articleCategoryService.findByArticleCategoryCode("Consumer Goods"));
		}
		else{
			category.setParentCategory(null);
		}
		this.articleCategoryService.save(category);
		return new ResponseEntity<ArticleCategoryDTO>(HttpStatus.OK);
	}
}
