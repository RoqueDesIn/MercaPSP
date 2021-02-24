package Controllers;

import DAO.EmployeeDAO;
import GUI.LoginView;


public class ControlSuper {
	
	//estados
	private ControBBDD controbbdd;
	private LoginView miLoginView;
	private EmployeeDAO miUsuarioDAO;
	
	//comportamientos
	/**
	 * constructor
	 */
	public ControlSuper() {

		// instancia el controlador de BBDD
		controbbdd= new ControBBDD();
		// inicia la BBDD, la crea si no existe
		controbbdd.iniciar();
		// instancia la loginView
		miLoginView = new LoginView();
		//Instancia el usuarioDAO
		miUsuarioDAO= new EmployeeDAO();
		
		// muestra el Login
		miLoginView.getFrame().setAlwaysOnTop(true);
		miLoginView.getFrame().setVisible(true);
		
		System.out.println("fin ControladorTaller");
	}
	
	/**
	 * Inicia la BBDD
	 */
	public void inibbdd() {
		controbbdd.iniciar();
	}
}
