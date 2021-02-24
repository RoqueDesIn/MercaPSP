package Models;

import java.sql.Date;

public class Employee {
	private int id;
	private Date last_session;
	private Date contract_date;
	
	public Employee(int id, Date last_session, Date contract_date) {
		super();
		this.id = id;
		this.last_session = last_session;
		this.contract_date = contract_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLast_session() {
		return last_session;
	}

	public void setLast_session(Date last_session) {
		this.last_session = last_session;
	}

	public Date getContract_date() {
		return contract_date;
	}

	public void setContract_date(Date contract_date) {
		this.contract_date = contract_date;
	}
	
	
	
}
