package Models;

public class LPurchase {
	private int id;
	private int idlin;
	private int idproduct;
	private float quantity;
	
	
	public LPurchase(int id, int idlin, int idproduct, float quantity) {
		super();
		this.id = id;
		this.idlin = idlin;
		this.idproduct = idproduct;
		this.quantity = quantity;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdlin() {
		return idlin;
	}
	public void setIdlin(int idlin) {
		this.idlin = idlin;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	
}
