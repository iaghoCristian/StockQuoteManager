package br.inatel.StockQuoteManager.controller;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.inatel.StockQuoteManager.model.dto.StockDto;
import br.inatel.StockQuoteManager.service.StockService;
import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StockService stockService;
	
	@Test
	void shouldRegister() throws Exception {
		StockDto stock = new StockDto();
		stock.setId("petr4");
		
		Mockito.when(stockService.getById("petr4")).thenReturn(stock);
		
		JSONObject quotes = new JSONObject();
		quotes.put("2021-07-03", "111");
		quotes.put("2021-07-04", "222");
		JSONObject data = new JSONObject();
		data.put("id", "petr4");
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201))
		.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));;
	}
	
	@Test
	public void shouldntRegister() throws Exception {
		Mockito.when(stockService.getById("invalid")).thenReturn(null);
		
		JSONObject quotes = new JSONObject();
		quotes.put("2021-07-03", "111");
		quotes.put("2021-07-04", "222");
		JSONObject data = new JSONObject();
		data.put("id", "invalid");
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void shouldListById() throws Exception{
		StockDto stock = new StockDto();
		stock.setId("petr4");
		
		Mockito.when(stockService.getById("petr4")).thenReturn(stock);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/quote/petr4")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}
	
	@Test
	public void shouldntListById() throws Exception {
		
		Mockito.when(stockService.getById("invalid")).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/quote/invalid")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
}
