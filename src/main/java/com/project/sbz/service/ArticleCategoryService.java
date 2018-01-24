package com.project.sbz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.sbz.model.ArticleCategory;
import com.project.sbz.repository.ArticleCategoryRepository;

@Service
public class ArticleCategoryService {
	
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;
	
	@Transactional
	public List<ArticleCategory> findAll(){
		return this.articleCategoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}
	
	@Transactional
	public int deleteByCode(String code){
		return this.articleCategoryRepository.deleteByArticleCategoryCode(code);
	}
	
	@Transactional
	public ArticleCategory findByArticleCategoryCode(String name){
		return this.articleCategoryRepository.findByArticleCategoryCode(name);
	}
	
	@Transactional
	public ArticleCategory save(ArticleCategory article){
		return this.articleCategoryRepository.save(article);
	}
}
