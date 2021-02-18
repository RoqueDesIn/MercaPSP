package Controllers;

import DAO.UsuarioDAO;
import GUI.LoginView;


public class ControladorPrTaller {
	
	//estados
	private ControBBDD controbbdd;
	private LoginView miLoginView;
	private UsuarioDAO miUsuarioDAO;
	
	//comportamientos
	/**
	 * constructor
	 */
	public ControladorPrTaller() {

		// instancia el controlador de BBDD
		controbbdd= new ControBBDD();
		// inicia la BBDD, la crea si no existe
		controbbdd.iniciar();
		// instancia la loginView
		miLoginView = new LoginView();
		//Instancia el usuarioDAO
		miUsuarioDAO= new UsuarioDAO();
		
		// muestra el Login
		miLoginView.getFrame().setAlwaysOnTop(true);
		miLoginView.getFrame().setVisible(true);
		
		System.out.println("fin ControladorTaller");
	}
	
	/**
	 * Comprueba el login
	 */
	public boolean comprobarlogin(int employee) {
		return miUsuarioDAO.compobarlogin(employee);
	}
	
	

	/**
	 * Inicia la BBDD
	 */
	public void inibbdd() {
		controbbdd.iniciar();
	}
}
