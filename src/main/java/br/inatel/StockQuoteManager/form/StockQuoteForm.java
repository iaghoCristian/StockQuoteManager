package br.inatel.StockQuoteManager.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.StockQuoteManager.model.Quote;


public class StockQuoteForm {

	@NotEmpty @NotNull
	private String id;
	@NotEmpty @NotNull
	private Map<String, String> quotes = new HashMap<String,String>();
	
	public String getId() {
		return id;
	}
	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	public List<Quote> convert() {
		List<Quote> quotes = new ArrayList<>();
		
		for(Entry<String,String> quote : this.quotes.entrySet()) {
			LocalDate date = LocalDate.parse(quote.getKey());
			BigDecimal price = new BigDecimal(quote.getValue());
			
			quotes.add(new Quote(this.id, date, price));
		}
		return quotes;
	}
	
	
}
