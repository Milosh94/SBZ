package com.project.sbz.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.project.sbz.dto.ArticleCategoryDTO;
import com.project.sbz.dto.DiscountDTO;
import com.project.sbz.model.DiscountEvent;
import com.project.sbz.service.ArticleCategoryService;
import com.project.sbz.service.DiscountEventService;

@RestController
@RequestMapping("/api")
public class DiscountEventController {

	@Autowired
	private DiscountEventService discountEventService;
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	@GetMapping("/discount-event")
	public ResponseEntity<List<DiscountDTO>> getDiscountEvents(){
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		Date todayWithZeroTime;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			return new ResponseEntity<List<DiscountDTO>>(HttpStatus.CONFLICT);
		}
		List<DiscountDTO> discountEvents = this.discountEventService.findAll()
				.stream()
				.map(d -> new DiscountDTO(
						d.getEventCode(),
						d.getName(),
						d.getDateFrom(),
						d.getDateTo(),
						d.getDiscountPercentage(),
						d.getArticleCategories()
						.stream()
						.map(c -> new ArticleCategoryDTO(
								c.getArticleCategoryCode(), 
								c.getName(), 
								c.getAllowedDiscountPercentage()))
						.collect(Collectors.toList())))
				.filter(d -> !d.getDateTo().before(todayWithZeroTime))
				.collect(Collectors.toList());
		return new ResponseEntity<List<DiscountDTO>>(discountEvents, HttpStatus.OK);
	}
	
	@PostMapping("/discount-event")
	public ResponseEntity<DiscountDTO> postDiscountEvent(@RequestBody DiscountDTO discount){
		System.out.println(discount.getDateFrom().compareTo(discount.getDateTo()));
		System.out.println(discount.getDateTo().compareTo(new Date()));
		if(this.discountEventService.findByEventCode(discount.getEventCode()) != null){
			return new ResponseEntity<DiscountDTO>(HttpStatus.CONFLICT);
		}
		DiscountEvent event = new DiscountEvent();
		event.setName(discount.getName());
		event.setEventCode(discount.getEventCode());
		event.setDiscountPercentage(discount.getDiscountPercentage());
		event.setDateFrom(discount.getDateFrom());
		event.setDateTo(discount.getDateTo());
		event.setArticleCategories(discount.getArticleCategories()
		.stream()
		.map(category -> this.articleCategoryService.findByArticleCategoryCode(category.getArticleCategoryCode()))
		.filter(articleCategory -> articleCategory != null).collect(Collectors.toSet()));
		this.discountEventService.save(event);
		return new ResponseEntity<DiscountDTO>(HttpStatus.OK);
	}
	
	@DeleteMapping("/discount-event")
	public ResponseEntity<DiscountDTO> deleteDiscountEvent(@RequestParam("eventCode") String eventCode){
		int deleted = this.discountEventService.deleteByEventCode(eventCode);
		if(deleted == 1){
			return new ResponseEntity<DiscountDTO>(HttpStatus.OK);
		}
		return new ResponseEntity<DiscountDTO>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/discount-event")
	public ResponseEntity<DiscountDTO> putDiscountEvent(@RequestBody DiscountDTO discountDTO){
		DiscountEvent discount = this.discountEventService.findByEventCode(discountDTO.getEventCode());
		if(discount == null){
			return new ResponseEntity<DiscountDTO>(HttpStatus.BAD_REQUEST);
		}
		discount.setName(discountDTO.getName());
		discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
		discount.setDateFrom(discountDTO.getDateFrom());
		discount.setDateTo(discountDTO.getDateTo());
		discount.setArticleCategories(discountDTO.getArticleCategories()
		.stream()
		.map(category -> this.articleCategoryService.findByArticleCategoryCode(category.getArticleCategoryCode()))
		.filter(articleCategory -> articleCategory != null).collect(Collectors.toSet()));
		this.discountEventService.save(discount);
		return new ResponseEntity<DiscountDTO>(HttpStatus.OK);
	}
}
