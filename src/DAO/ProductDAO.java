package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Product;

public class ProductDAO extends AbstractDAO{
	
	public ProductDAO(){};

	/**
	 * Carga en un arrayList con los registros de productos
	 * @return ArrayList con los objetos de productos
	 */
	public ArrayList<Product> cargaListaDAO() {
		ArrayList<Product> miArray = new ArrayList<Product>();
		String 	strSql="select id,product,customer_price,provider_price,stock_amount"
				+" from products order by id";
		
		// ejecuta la consulta
		ResultSet rst=consultaSQL(strSql);
		// recorre el rst y guarda en el array
		try {
			Product producto;
			while (rst.next()) {
				producto = new Product(rst.getInt(1),rst.getString(2), rst.getFloat(3), rst.getFloat(4), rst.getInt(5));
				miArray.add(producto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return miArray;
	}
	
	/**
	 * Comprueba el Stock 
	 * @param product
	 * @return eñ stock actual del producto
	 */
	public int checkStock (Product product) {
		int result = 0;
		String 	strSql="select stock_amount"
				+" from products where id = " + product.getId();
		// ejecuta la consulta
		ResultSet rst=consultaSQL(strSql);
		try {
			rst.first();
			result = rst.getInt(1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return result;
	}
	

	/**
	 * devuelve objeto Product dado un id 
	 * @param id
	 * @return objeto Product representado por ese id
	 */
	public Product getProductById (int id) {
		Product result = new Product();
		String 	strSql="select *"
				+" from products where id = " + id;
		// ejecuta la consulta
		ResultSet rst=consultaSQL(strSql);
		try {
			rst.first();
			result.setId(rst.getInt(1));
			result.setProduct(rst.getString(2));
			result.setCustomer_price(rst.getFloat(3));
			result.setProvider_price(rst.getFloat(4));
			result.setStock_amount(rst.getInt(5));

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return result;

	}
	
	/**
	 * resta el stock de un artículo
	 * @param id
	 * @param valueOf
	 */
	public void restaStock(Product product, Integer quantity) {
		// resta el stock venta
		String 	strSql="update products as p"
				+ " set stock_amount= '"
				+ (checkStock(product) - quantity)
				+"' where p.id=" + product.getId();
		ejecutaSQL(strSql);
		
	}
	
	// fin DAO
}
