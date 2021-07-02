package br.inatel.StockQuoteManager.config.validation;

public class ErrorDto {
	private String field;
	private String error;
	
	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}

	public ErrorDto(String field, String error) {
		super();
		this.field = field;
		this.error = error;
	}
}

