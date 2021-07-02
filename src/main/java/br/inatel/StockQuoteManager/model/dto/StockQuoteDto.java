package br.inatel.StockQuoteManager.model.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.inatel.StockQuoteManager.model.Quote;

public class StockQuoteDto {
	
	private String id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<String, String>();
	
	
	
	public StockQuoteDto(String stockid,List<Quote> quotes) {
		id = UUID.randomUUID().toString();
		this.stockId = stockid;
		this.quotesMap(quotes);
	}
	
	private void quotesMap(List<Quote> quotes) {
		quotes.forEach(quote -> {
			String date = quote.getDate().toString();
			String price = quote.getPrice().toBigInteger().toString();
			this.getQuotes().put(date, price);
		});
	}

	public String getId() {
		return id;
	}
	public Map<String, String> getQuotes() {
		return quotes;
	}

	public String getStockId() {
		return stockId;
	}
	
	
	
}
