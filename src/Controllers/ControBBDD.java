package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;

import DAO.AbstractDAO;

public class ControBBDD extends AbstractDAO{
	
	protected ResultSet rs;
	
	//connection es cn
	
	//constructor conrolador bbdd
	public ControBBDD() {
		super();
	}
	
	//comportamiento que incia la bbdd
	public void iniciar() {
		super.iniciar();
	}

}
