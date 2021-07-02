package br.inatel.StockQuoteManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.inatel.StockQuoteManager.model.Quote;

public interface QuoteRepository  extends JpaRepository<Quote,Long>{
	
	List<Quote> findByStockId(String stockId);
	
}
