package com.project.sbz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sbz.model.DiscountEvent;
import com.project.sbz.repository.DiscountEventRepository;

@Service
public class DiscountEventService {

	@Autowired
	private DiscountEventRepository discountEventRepository;
	
	@Transactional
	public List<DiscountEvent> findAll(){
		return this.discountEventRepository.findAll();
	}
	
	@Transactional
	public int deleteByEventCode(String code){
		return this.discountEventRepository.deleteByEventCode(code);
	}
	
	@Transactional
	public DiscountEvent findByEventCode(String code){
		return this.discountEventRepository.findByEventCode(code);
	}
	
	@Transactional
	public DiscountEvent save(DiscountEvent discountEvent){
		return this.discountEventRepository.save(discountEvent);
	}
}
