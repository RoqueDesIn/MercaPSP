package Models;

import java.sql.Date;

public class Purchase {
	private int id;
	private int id_employee;
	private Date purchase_date;
	
	Purchase () {}

	public Purchase(int id, int id_employee, Date purchase_date) {
		super();
		this.id = id;
		this.id_employee = id_employee;
		this.purchase_date = purchase_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_employee() {
		return id_employee;
	}

	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}

	public Date getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}
	
	

}
