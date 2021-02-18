package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends  AbstractDAO{

	private int miusuario;
	
	public UsuarioDAO() {
		super();
		miusuario=0;
	}
	
	
	/**
	 * devuelve true si el login es correcto, false si es incorrecto
	 * @param user
	 * @param contra
	 * @return
	 */
	public boolean compobarlogin(int employee) {
		ResultSet rs= null;
		boolean mibool=false;
		try {
            cn=super.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT * FROM usuarios;");
            //miprofe=siguiente();
            while (rs.next()) {
		        if (rs.getString(1).equals(employee) && mibool==false) {
		        	mibool=true;
		        	//aqui luego se crearia un usuario
		        	miusuario= employee;
		        }
		        
		      
            }
         
        } catch (SQLException e) {
            e.printStackTrace();    
        }
		return mibool;
	}
}