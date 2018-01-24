package com.project.sbz.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sbz.dto.ArticleDTO;
import com.project.sbz.dto.BillDTO;
import com.project.sbz.dto.BillDiscountDTO;
import com.project.sbz.dto.BillItemDTO;
import com.project.sbz.dto.BillItemDiscountDTO;
import com.project.sbz.model.User;
import com.project.sbz.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/shopping-history")
	public ResponseEntity<List<BillDTO>> getShoppingHistory(Principal userPrincipal){
		String username = userPrincipal.getName();
		User user = this.userRepository.findByUsername(username);
		
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
								.collect(Collectors.toList()))).collect(Collectors.toList()))).collect(Collectors.toList());
		return new ResponseEntity<List<BillDTO>>(bills, HttpStatus.OK);
	}
}
