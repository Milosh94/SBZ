package com.project.sbz.controller;

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
import com.project.sbz.dto.BillDTO;
import com.project.sbz.dto.BillDiscountDTO;
import com.project.sbz.dto.BillItemDTO;
import com.project.sbz.dto.BillItemDiscountDTO;
import com.project.sbz.dto.CustomerCategoryDTO;
import com.project.sbz.dto.CustomerProfileDTO;
import com.project.sbz.dto.UserDTO;
import com.project.sbz.model.Bill;
import com.project.sbz.model.BillItem;
import com.project.sbz.service.BillService;

@RestController
@RequestMapping("/api")
public class BillController {

	@Autowired
	private BillService billService;
	
	@GetMapping("/bill")
	public ResponseEntity<List<BillDTO>> getBills(){
		List<BillDTO> bills = this.billService.findAll()
				.stream()
				.map(bill -> new BillDTO(
						bill.getBillCode(),
						bill.getCreated(),
						bill.getStatus(),
						bill.getOriginalCost(),
						bill.getDiscountPercentage(),
						bill.getFinalCost(),
						bill.getRewardPointsSpent(),
						bill.getRewardPointsGained(),
						bill.getBillDiscounts().stream()
						.map(discount -> new BillDiscountDTO(
								discount.getBillDiscountCode(),
								discount.getDiscountPercentage(),
								discount.isDiscountType()))
						.collect(Collectors.toList()),
						bill.getBillItems().stream()
						.map(item -> new BillItemDTO(
								item.getItemNumber(),
								item.getPrice(),
								new ArticleDTO(item.getArticle().getArticleCode(),
										item.getArticle().getName(),
										item.getArticle().getArticleCategory() == null ? "None" : item.getArticle().getArticleCategory().getArticleCategoryCode(),
										item.getArticle().getArticleCategory() == null ? "None" : item.getArticle().getArticleCategory().getName(),
										item.getArticle().getPrice(),
										item.getArticle().getArticleCount(),
										null),
								item.getCount(),
								item.getTotalOriginalPrice(),
								item.getTotalDiscountPrice(),
								item.getDiscountPercentage(),
								item.getItemDiscounts()
								.stream()
								.map(itemDiscount -> new BillItemDiscountDTO(
										itemDiscount.getBillItemDiscountCode(),
										itemDiscount.getDiscountPercentage(),
										itemDiscount.isDiscountType()))
								.collect(Collectors.toList())))
						.collect(Collectors.toList()),
						new CustomerProfileDTO(bill.getBuyer().getDeliveryAddress(), 
								bill.getBuyer().getRewardPoints(), 
								new CustomerCategoryDTO(
										bill.getBuyer().getCustomerCategory().getCategoryCode(), 
										bill.getBuyer().getCustomerCategory().getName(), 
										null), 
								new UserDTO(
										bill.getBuyer().getUser().getUsername(), 
										bill.getBuyer().getUser().getFirstName(), 
										bill.getBuyer().getUser().getLastName(), 
										bill.getBuyer().getUser().getRole().getName(), 
										bill.getBuyer().getUser().getRegisterDate()))))
				.collect(Collectors.toList());
		return new ResponseEntity<List<BillDTO>>(bills, HttpStatus.OK);
	}
	
	@PutMapping("/approve-bill")
	public ResponseEntity<BillDTO> approveBill(@RequestParam("code") String code){
		Bill bill = this.billService.findByBillCode(code);
		if(bill == null){
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		if(bill.getStatus() != 0){
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		for(BillItem item : bill.getBillItems()){
			if(item.getCount() <= item.getArticle().getArticleCount()){
				item.getArticle().setArticleCount(item.getArticle().getArticleCount() - item.getCount());
			}
			else{
				return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
			};
		}
		bill.setStatus(1);
		bill.getBuyer().setRewardPoints((int) bill.getBuyer().getRewardPoints() + bill.getRewardPointsGained());
		this.billService.save(bill);
		return new ResponseEntity<BillDTO>(HttpStatus.OK);
	}
	
	@PutMapping("/refuse-bill")
	public ResponseEntity<BillDTO> refuseBill(@RequestParam("code") String code){
		Bill bill = this.billService.findByBillCode(code);
		if(bill == null){
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		if(bill.getStatus() != 0){
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		bill.setStatus(-1);
		bill.getBuyer().setRewardPoints((int) bill.getBuyer().getRewardPoints() + bill.getRewardPointsSpent());
		this.billService.save(bill);
		return new ResponseEntity<BillDTO>(HttpStatus.OK);
	}
}
