package br.inatel.StockQuoteManager.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.StockQuoteManager.model.dto.StockDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {
	
	//@Value("${manager.url}")
	private String url;
	private RestTemplate restTemplate;
	
	@Autowired
	public StockService(@Value("${manager.url}") String url){
		this.url = url;
		restTemplate = new RestTemplate();
	}
	
	@Cacheable(value="stockList")
	public List<StockDto> listAll(){
		log.info("Getting a list of stock on API");
		StockDto[] stockList = restTemplate.getForObject(url+"/stock", StockDto[].class);
		return Arrays.asList(stockList);
	}
	
	@Cacheable(value="list")
	public StockDto getById(String id) {
		log.info("Looking for a stock on API");
		StockDto stockDto = restTemplate.getForObject(url+"/stock/" + id, StockDto.class);
		return stockDto;
	}
	
	public void notification() {
		log.info("notification");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject body = new JSONObject();
		
		body.put("host", "localhost");
		body.put("port", "8081");
		
		HttpEntity<String> request = new HttpEntity<String>(body.toString(),header);
		restTemplate.postForObject(url + "/notification", request, String.class);
		
	}
	
}
