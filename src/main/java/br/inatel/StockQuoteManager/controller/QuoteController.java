package br.inatel.StockQuoteManager.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.StockQuoteManager.config.validation.ErrorDto;
import br.inatel.StockQuoteManager.form.StockQuoteForm;
import br.inatel.StockQuoteManager.model.Quote;
import br.inatel.StockQuoteManager.model.dto.StockDto;
import br.inatel.StockQuoteManager.model.dto.StockQuoteDto;
import br.inatel.StockQuoteManager.repository.QuoteRepository;
import br.inatel.StockQuoteManager.service.StockService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@Slf4j
public class QuoteController {
	
	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	StockService stockService;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder){
		StockDto stock = stockService.getById(form.getId());
		
		if(stock == null) {
			log.warn("Stock not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("id", "Não foi encontrado o Stock com o id: " + form.getId()));
		}
		
		log.info("Saving quotes");
		quoteRepository.saveAll(form.convert());
		URI uri = uriBuilder.path("/quotes/{id}").buildAndExpand(form.getId()).toUri();
		List<Quote> quotes = quoteRepository.findByStockId(form.getId());
		
		return ResponseEntity.created(uri).body(new StockQuoteDto(quotes.get(0).getStockId(),quotes));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable String id){
		StockDto stock = stockService.getById(id);
		List<Quote> quotes = quoteRepository.findByStockId(id);
		
		if(stock == null) {
			log.warn("Stock with id: "+id+" not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Stock", "Não foi encontrado o Stock com o id: " + id));
		}
		if(quotes.isEmpty()) {
			log.warn("No quotes for stock with id "+id);
			return ResponseEntity.status(204).body(null);
		}
		return ResponseEntity.ok(new StockQuoteDto(quotes.get(0).getStockId() ,quotes));
	}
	
	@GetMapping
	public ResponseEntity<?> list(){
		List<StockDto> stocks = stockService.listAll();
		List<StockQuoteDto> stockWithQuotes = stocks.stream().map(stock ->{
			List<Quote> quotes = quoteRepository.findByStockId(stock.getId());
			return new StockQuoteDto(stock.getId(),quotes);
		}).collect(Collectors.toList());
		
		if (stockWithQuotes.isEmpty()) {
			log.warn("StockQuotes not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("id0", "Não foi possivel encontrar nenhum StockQuote"));
		}
		
		return ResponseEntity.status(200).body(stockWithQuotes);
	}
	
	
}
