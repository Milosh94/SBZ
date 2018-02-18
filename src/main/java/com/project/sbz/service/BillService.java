package com.project.sbz.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sbz.dto.ShoppingCartDTO;
import com.project.sbz.dto.ShoppingCartItemDTO;
import com.project.sbz.model.Bill;
import com.project.sbz.model.BillDiscount;
import com.project.sbz.model.BillItem;
import com.project.sbz.model.BillItemDiscount;
import com.project.sbz.model.CustomerProfile;
import com.project.sbz.repository.ArticleRepository;
import com.project.sbz.repository.BillDiscountRepository;
import com.project.sbz.repository.BillItemRepository;
import com.project.sbz.repository.BillRepository;
import com.project.sbz.repository.CustomerProfileRepository;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private BillItemRepository billItemRepository;
	
	@Autowired
	private BillDiscountRepository billDiscountRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CustomerProfileRepository customerProfileRepository;
	
	@Autowired
	private KieContainer kieContainer;
	
	@Transactional
	public List<Bill> findAll(){
		return this.billRepository.findAll();
	}
	
	@Transactional
	public Bill findByBillCode(String code){
		return this.billRepository.findByBillCode(code);
	}
	
	@Transactional
	public Bill save(Bill bill){
		return this.billRepository.save(bill);
	}
	
	public BillItem basicDiscount(BillItem i){
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(i);
		kieSession.getAgenda().getAgendaGroup("item-basic-discounts").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        return i;
	}
	
	@Transactional
	public Bill checkout(ShoppingCartDTO cart, CustomerProfile customerProfile){
		KieSession kieSession = this.kieContainer.newKieSession("BillItemDiscount");
		Bill bill = new Bill();
		bill.setBuyer(customerProfile);
		bill.setCreated(new Date());
		bill.setStatus(0);
		bill = this.billRepository.save(bill);
		bill.setBillCode(bill.getId() + "");
		Set<BillItem> billItems = new HashSet<BillItem>();
		int itemIndex = 0;
		for(ShoppingCartItemDTO item: cart.getItems()){
			itemIndex += 1;
			BillItem i = new BillItem();
			i.setArticle(this.articleRepository.findByArticleCode(item.getArticleCode()));
			i.setBill(bill);
			i.setCount(item.getQuantity());
			i.setItemNumber(itemIndex);
			i.setItemDiscounts(new HashSet<BillItemDiscount>());
			i.setPrice(i.getArticle().getPrice());
			i.setTotalOriginalPrice(i.getPrice() * i.getCount());
			//i = this.basicDiscount(i);
			kieSession.getAgenda().getAgendaGroup("item-basic-discounts").setFocus();
			kieSession.insert(i);
			kieSession.fireAllRules();
			kieSession.getAgenda().getAgendaGroup("item-additional-discounts").setFocus();
			kieSession.insert(customerProfile);
			kieSession.fireAllRules();
			kieSession.getAgenda().getAgendaGroup("item-final").setFocus();
			kieSession.insert(customerProfile);
			kieSession.fireAllRules();
			this.billItemRepository.save(i);
			billItems.add(i);
		}
		kieSession.dispose();
		bill.setBillItems(billItems);
		bill.setBillDiscounts(new HashSet<BillDiscount>());
		kieSession = this.kieContainer.newKieSession("BillDiscount");
		kieSession.insert(bill);
		kieSession.getAgenda().getAgendaGroup("bill-original-price").setFocus();
		kieSession.fireAllRules();
		kieSession.getAgenda().getAgendaGroup("bill-discounts").setFocus();
		kieSession.fireAllRules();
		for(BillDiscount bd : bill.getBillDiscounts()){
			this.billDiscountRepository.save(bd);
		}
		kieSession.getAgenda().getAgendaGroup("bill-final").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		kieSession = this.kieContainer.newKieSession("RewardPoints");
		kieSession.insert(bill);
		kieSession.getAgenda().getAgendaGroup("reward-points").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		if(cart.getRewardPoints() < bill.getFinalCost()){
			bill.setRewardPointsSpent(cart.getRewardPoints());
		}
		else{
			bill.setRewardPointsSpent((int) Math.ceil(bill.getFinalCost()));
		}
		customerProfile.setRewardPoints((int)customerProfile.getRewardPoints() - cart.getRewardPoints());
		this.customerProfileRepository.save(customerProfile);
		bill = this.billRepository.save(bill);
		return bill;
	}
}
