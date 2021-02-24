package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class EmployeeDAO extends  AbstractDAO{

	
	/**
	 * devuelve true si el login es correcto, false si es incorrecto
	 * @param user
	 * @param contra
	 * @return
	 */

	public static JSONObject loginUser(JSONObject object) {
		ResultSet rs= null;
		JSONObject result = new JSONObject();
		try {
            cn=conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT * FROM employee;");

            while (rs.next()) {
		        if (rs.getInt(1)==Integer.valueOf(String.valueOf(object.get("id"))) && result.isEmpty()) {
		        	result.put("id", String.valueOf(rs.getInt(1)));
		        	result.put("last_session", String.valueOf(rs.getDate(2)));
		        	result.put("contract_date", String.valueOf(rs.getDate(3)));
		        }
            }
         
        } catch (SQLException e) {
            e.printStackTrace();    
        }
		return result;
	}
}