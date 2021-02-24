package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import Models.LPurchase;

public class LPurchaseDAO extends AbstractDAO {
	public LPurchaseDAO () {}
	/**
	 * Añade un cobro
	 * @param Cobro objeto purchase a añadir
	 */
	public boolean cobro(LPurchase lpurchase) {
		String 	strSql="insert into lpurchases "
				+ " (idpurchase, idlin, idproduct, quantity) "
				+ "values ('" + lpurchase.getId() +"', '"
				+ "0', '"
				+ lpurchase.getIdproduct() +"', '"
				+ lpurchase.getQuantity()
				+"');";
		// Se ejecuta el SQL
		return ejecutaSQL(strSql); 
	}
	
	public float cajaDiaria (int idEmployee) {
		float result=0;
		Calendar c1 = Calendar.getInstance();
		Date fechaObject = new Date(c1.getTimeInMillis());
		float dif=0;
		String strSqlProducts = "select pr.id, pr.customer_price, pr.provider_price "
				+ " from lpurchases as lp, purchases as pu, employee as em, products as pr "
				+ " where em.id=pu.id_employee "
				+ " and pu.id=lp.idpurchase "
				+ " and pu.id_employee='" +idEmployee
				+ "' and pu.purchase_date='" + fechaObject + "'";
		
		// ejecuta la consulta productos vendidos en el dia por un empleado
		ResultSet rstProducts=consultaSQL(strSqlProducts);

		try {
			//recorre los productos vendidos del empleado
			while (rstProducts.next()) {
				// extrae la cantidad de cada producto
				dif=(rstProducts.getFloat(2)-rstProducts.getFloat(3));
				String strSql= "select sum(lp.quantity)"
						+ "from lpurchases as lp, purchases as pu, employee as em, products as pr"
						+ " where em.id='" + idEmployee
						+"' and pu.id=lp.idpurchase "
						+ " and pu.purchase_date='"+ fechaObject
						+ "' and pr.id='" +rstProducts.getInt(1)+"'";
				ResultSet rst=consultaSQL(strSql);
				if (rst.next()) result=result+(rst.getInt(1)*dif);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}


// end DAO
}

