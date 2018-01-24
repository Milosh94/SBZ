package com.project.sbz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.service.ArticleService;

@RestController
@RequestMapping("/api")
public class SearchController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/search")
	public ResponseEntity<Page<ArticleDTO>> search(@RequestParam String articleId, @RequestParam String articleName, 
			@RequestParam(required=false) Double priceFrom, @RequestParam(required=false) Double priceTo, @RequestParam(required=false) List<String> categories, Pageable pageable){
		Page<ArticleDTO> lis = this.articleService.findAll(articleId, articleName, priceFrom, priceTo, categories, pageable);
		return new ResponseEntity<Page<ArticleDTO>>(lis, HttpStatus.OK);
	}
}
