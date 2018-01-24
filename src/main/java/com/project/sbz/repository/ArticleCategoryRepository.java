package com.project.sbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.sbz.model.ArticleCategory;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long>{
	
	public int deleteByArticleCategoryCode(String code);
	
	public ArticleCategory findByArticleCategoryCode(String name);
}
