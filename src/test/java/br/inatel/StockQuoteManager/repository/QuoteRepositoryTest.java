package br.inatel.StockQuoteManager.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.inatel.StockQuoteManager.model.Quote;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class QuoteRepositoryTest {

	@Autowired
	private QuoteRepository quoteRepository;
	
	
	@Test
	void shouldFindByStockId() {
		List<Quote> quotes = quoteRepository.findByStockId("petr4");
		
		Assert.assertEquals(2, quotes.size());
	}

}
