package Main;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Common.Constantes;
import Common.FlujoServer;
import Common.SendMail;
import Common.XmlReader;
import DAO.AbstractDAO;
import DAO.EmployeeDAO;
import DAO.LPurchaseDAO;
import DAO.ProductDAO;
import DAO.PurchaseDAO;
import Models.LPurchase;
import Models.Product;
import Models.Purchase;

public class MainServer {

	private static JSONObject mensaje=null;
	private static String[] credencialycorreo;
	private static FlujoServer server=null;
	private static JSONObject result = null;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	
		// definición de variables
		AbstractDAO abstractdao = new AbstractDAO() {};

		// extraer datos del correo y la clave del fichero XML
		XmlReader xmlreader = new XmlReader();
		credencialycorreo = xmlreader.leeXml().split(";");
		
		boolean salir=false;
		while (!salir) {
			// Crea socket servidor
			server= new FlujoServer(Constantes.PUERTO);
			// espera a recibir mensaje
			mensaje=server.serverReceive();
			/*
			 *  debe de comprobar el mensaje y segun el mismo
			 *  enviar la respuesta
			 */
			
			// login
			if  (mensaje.get("orden").equals("Login")) {
				// llamar a EmployeeDAO
				result=EmployeeDAO.loginUser(mensaje);
				server.serverSend(result);
			}
			
			// lista de productos
			if  (mensaje.get("orden").equals("Lista")) {
				// llamar a EmployeeDAO
				ProductDAO productdao = new ProductDAO();
				// mapear lista a json
				result=mapeaLista(productdao.cargaListaDAO());
				server.serverSend(result);
				result=new JSONObject();
			}
			
			// venta
			if  (mensaje.get("orden").equals("Cobro")) {
				gestionaCobro(mensaje);
			}
			
			// caja del dia
			if  (mensaje.get("orden").equals("Caja")) {
				LPurchaseDAO lpurchasedao = new LPurchaseDAO();
				result.put("Resultado",lpurchasedao.cajaDiaria(Integer.valueOf(mensaje.get("id_employee").toString())));
				//enviar mensaje
				server.serverSend(result);
				result=new JSONObject();
			}
			// cierra socket server
			server.serverClose();
		}
	
	}
	
	/**
	 * Gestiona una venta
	 * @param mensaje2 mensaje recibido
	 */
	private static void gestionaCobro(JSONObject mensaje2) {
		// obtener fecha del sistema
		Calendar c1 = Calendar.getInstance();
		// obtener fecha del sistema
		Date fechaObject =  new Date(c1.getTimeInMillis());
		String fecha =fechaObject.toString();
		System.out.println (fecha);
		
		//crea el producto a consultar y el DAO
		ProductDAO productdao= new ProductDAO();
		Product product = productdao.getProductById(Integer.valueOf(mensaje.get("idproduct").toString()));
		
		// comprueba rotura de stock y si la hay envía correo
		if (productdao.checkStock(product)<=Integer.valueOf(mensaje.get("quantity").toString())) {
			// enviar mail anunciando la rotura de stock
			// configuración del mail a enviar
			String destinatario =  credencialycorreo[0]; //A quien le quieres escribir.
			//destinatario = "laquesehaliadopsp@gmail.com";
		    String asunto = "ROTURA DE STOCK";
		    // configura cuerpo del correo
		    String cuerpo = "El producto "+ mensaje.get("idproduct") 
		    				+ " con el precio de proveedor " 
		    				+ product.getProvider_price()
		    				+ "€ ha agotado sus existencias  el " + fecha;
		    String clave=credencialycorreo[1];
		    //clave="M1Cl4v3#!psp";
		    SendMail sendmail = new SendMail();
		    sendmail.enviarConGMail(destinatario, asunto, cuerpo,clave);
		}
		
		// resta el stock del producto
		productdao.restaStock(product,Integer.valueOf(mensaje.get("quantity").toString()));
		
		// crea la venta
		PurchaseDAO purchasedao = new PurchaseDAO();
		Purchase purchase = new Purchase(0,
				Integer.valueOf(mensaje.get("id_employee").toString()),
				fechaObject );
		int idLastVenta = purchasedao.cobro(purchase);
		
		// crea la linea de venta
		LPurchaseDAO lpurchasedao = new LPurchaseDAO();
		LPurchase lpurchase = new LPurchase(
				idLastVenta,
				0,
				Integer.valueOf(mensaje.get("idproduct").toString()),
				Float.valueOf(mensaje.get("quantity").toString()) 
				);
		
		result.put("Resultado", String.valueOf(lpurchasedao.cobro(lpurchase)));
		
		//enviar mensaje
		server.serverSend(result);
		result=new JSONObject();		
	}


	/**
	 * Recibe array de objetos producto y mapea el Json
	 * @param miArray lista de productos
	 * @return JSONObject de dos niveles con array de productos
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject mapeaLista(ArrayList<Product> miArray) {
		JSONArray jsArray = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		
		for (int i=0;i<miArray.size();i++) {
			jsonobject.put("id", miArray.get(i).getId());
			jsonobject.put("product", miArray.get(i).getProduct());
			jsonobject.put("customer_price", miArray.get(i).getCustomer_price());
			jsonobject.put("provider_price", miArray.get(i).getProvider_price());
			jsonobject.put("stock_amount", miArray.get(i).getStock_amount());
			jsArray.add(jsonobject);
			jsonobject = new JSONObject();
		}
		
		JSONObject result = new JSONObject();
		result.put("todo",jsArray);
		return result;
	}
	
	// acaba Main
}