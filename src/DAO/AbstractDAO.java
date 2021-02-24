package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Common.Constantes;

/**

 * Esta clase define el objeto AbstractDAO de la aplicación.
 * En ella están todos los métodos para conectar y crear BD, 
 * consultar, modificar, borrar e insertar registros etc. 
 * @author Roque Flores Naranjo
 * 
 * @version 27/10/2020-1.0

 * @see <a href = "https://www.linkedin.com/in/roque-flores-naranjo/" /> Mi LinkEdin :) </a>

 */
public abstract class AbstractDAO {
	
	// ESTADOS**********************
    // conexión a la BD
    protected static Connection cn = null;
    // statement
	protected static Statement stm = null;

	// COMPORTAMIENTOS *******************
	public AbstractDAO() {
		iniciar();
	}
    /**
     * inicia el DAO
     * crea la conexión a BD y el statement
     */
	
    public void iniciar () {
          
		// crea la conexión
        conectar();
        // crea el statement
        try {
			stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        creaBD(); 
    }
    
	protected void creaBD() {
		// crea la BD si es necesario
        try {
        	ejecutaSQL("use information_schema");
        	// comprueba que existe la tabla usuarios
        	String miSQL=" SELECT * "
            		+ " FROM information_schema.tables"
            		+ " WHERE table_name = 'employee'"
            		+ " and table_schema= '"+ Constantes.BD + "';";
        	
            ResultSet rs= consultaSQL(miSQL);
			if (rs.next()) {
				ejecutaSQL("use "+ Constantes.BD);
				System.out.println("Base de datos "+ Constantes.BD+" abierta correctamente.");
			} else {
				ejecutaSQL("use "+ Constantes.BD);
				ejecutaSentencias(Constantes.SQLCREATE);
			    // carga los datos Dummies
				ejecutaSentencias(Constantes.SQLCARGA);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Define el objeto connection para conectar con una BD MySQL
	 * @return 
	 */
    protected static Connection conectar() {
        try {
           Class.forName(Constantes.CONTROLADOR);
            cn = DriverManager.getConnection(Constantes.URL, Constantes.USUARIO, Constantes.CLAVE);
            System.out.println("Conexión OK");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error en la conexión");
            
            e.printStackTrace();
        }
		return cn;		
	}

	
	/**
	 * ejecuta sentencias desde un fichero SQL
	 * @param fileSQL fichero con las sentencias
	 */
	public void ejecutaSentencias(String fileSQL) {
		//lee el fichero
		String lineas= leeFichero(fileSQL);
		String [] sentencias=lineas.split(";");
		// recorre el array y ejecuta las sentencias 
		for (int i=0;i<sentencias.length;i++) {
			ejecutaSQL(sentencias[i]);
		}
	}
	
	/**
	 * lee un fichero SQL y devuelve un String con los datos
	 * @return
	 */
	private String leeFichero(String fileSQL) {
		String lineas=" ";
		BufferedReader lector = null;

		try {
			lector = new BufferedReader(new FileReader(fileSQL));
			String linea=lector.readLine();
			while(linea!=null) {
				if (linea!=null) lineas=lineas+linea.trim();
				linea=lector.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				lector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lineas;
	}
	
	/**
	 * Ejecuta un statement 
	 * @param strSQL el statement SQL a ejecutar
	 * @return 
	 */
	protected boolean ejecutaSQL(String strSQL) {
		boolean resultado=true;
		
		try {
			// ejecuta Statement
            stm.execute(strSQL);
            System.out.println( "SQL Ejecutado: " + strSQL);
            
        } catch (SQLException e) {
			e.printStackTrace();
            JOptionPane.showMessageDialog(null, strSQL, "Error: "+e.getMessage(), JOptionPane.ERROR_MESSAGE);
			resultado=false;
		} finally {
			
		}
        
		return resultado;
	}
	
	/**
	 * ejecuta una consulta SQL dado un String
	 * @param strSql
	 * @return un Resulset con los resultados
	 */
	protected ResultSet consultaSQL(String strSql) {
		ResultSet rst=null;
		try {
			//stm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rst = stm.executeQuery(strSql);
				return rst;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}


	
	
	//*************** fin
}
