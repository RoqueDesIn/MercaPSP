package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Purchase;

public class PurchaseDAO extends AbstractDAO{
	public PurchaseDAO () {}
	/**
	 * Añade un cobro
	 * @param Cobro objeto purchase a añadir
	 * @return id automático de venta realizada
	 */
	public int cobro(Purchase purchase) {
		int idVenta =0;
		
		// Añade venta
		String 	strSql="insert into purchases "
				+ " (id, id_employee, purchase_date) "
				+ "values ('" + purchase.getId() +"', '"
				+ purchase.getId_employee() +"', '"
				+ purchase.getPurchase_date() 
				+"');";
		ejecutaSQL(strSql);
		
		//extrae el ultimo id de venta
		strSql="select id from purchases order by id";
		ResultSet rst=consultaSQL(strSql);
		try {
			rst.last();
			idVenta=rst.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idVenta;
	}
}
