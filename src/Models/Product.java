package Models;

import java.sql.Date;

public class Product {
	private int id;
	private String product;
	private float  customer_price;
	private float provider_price;
	private int stock_amount;
	
	
	public Product(int id, String product, float customer_price, float provider_price, int stock_amount) {
		super();
		this.id = id;
		this.product = product;
		this.customer_price = customer_price;
		this.provider_price = provider_price;
		this.stock_amount = stock_amount;
	}
	
	
	public Product() {

	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public float getCustomer_price() {
		return customer_price;
	}
	public void setCustomer_price(float customer_price) {
		this.customer_price = customer_price;
	}
	public float getProvider_price() {
		return provider_price;
	}
	public void setProvider_price(float provider_price) {
		this.provider_price = provider_price;
	}
	public int getStock_amount() {
		return stock_amount;
	}
	public void setStock_amount(int stock_amount) {
		this.stock_amount = stock_amount;
	}

	
}
