package br.inatel.StockQuoteManager.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitProjectService {
	
	@Autowired
	private StockService stock; 
	@PostConstruct
	private void init() {
		stock.notification();
		
	}
}
