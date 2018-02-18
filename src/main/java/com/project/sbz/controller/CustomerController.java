package com.project.sbz.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.dto.BillDTO;
import com.project.sbz.dto.BillDiscountDTO;
import com.project.sbz.dto.BillItemDTO;
import com.project.sbz.dto.BillItemDiscountDTO;
import com.project.sbz.dto.ShoppingCartDTO;
import com.project.sbz.dto.ShoppingCartItemDTO;
import com.project.sbz.model.Article;
import com.project.sbz.model.Bill;
import com.project.sbz.model.User;
import com.project.sbz.repository.UserRepository;
import com.project.sbz.service.ArticleService;
import com.project.sbz.service.BillService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/shopping-history")
	public ResponseEntity<List<BillDTO>> getShoppingHistory(Principal userPrincipal){
		String username = userPrincipal.getName();
		User user = this.userRepository.findByUsername(username);
		System.out.println(user.getFirstName());
		List<BillDTO> bills = user.getCustomerProfile().getSuccessfulBuyings().stream()
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
								.map(itemDiscount -> new BillItemDiscountDTO(itemDiscount.getBillItemDiscountCode(),
										itemDiscount.getDiscountPercentage(),
										itemDiscount.isDiscountType()))
								.collect(Collectors.toList())))
						.collect(Collectors.toList()), 
						null))
				.collect(Collectors.toList());
		return new ResponseEntity<List<BillDTO>>(bills, HttpStatus.OK);
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<BillDTO> checkout(@RequestBody ShoppingCartDTO shoppingCartDTO, Principal userPrincipal){
		if(shoppingCartDTO.getItems() == null || shoppingCartDTO.getItems().size() == 0){
			return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
		}
		for(ShoppingCartItemDTO item : shoppingCartDTO.getItems()){
			Article a = this.articleService.findByArticleCode(item.getArticleCode());
			if(a == null || a.isStatus() == false){
				return new ResponseEntity<BillDTO>(HttpStatus.BAD_REQUEST);
			}
		}
		String username = userPrincipal.getName();
		User user = this.userRepository.findByUsername(username);
		Bill bill = billService.checkout(shoppingCartDTO, user.getCustomerProfile());
		BillDTO billDTO = new BillDTO(
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
						.map(itemDiscount -> new BillItemDiscountDTO(itemDiscount.getBillItemDiscountCode(),
								itemDiscount.getDiscountPercentage(),
								itemDiscount.isDiscountType()))
						.collect(Collectors.toList())))
				.collect(Collectors.toList()), 
				null);
		return new ResponseEntity<BillDTO>(billDTO, HttpStatus.OK);
	}
}
