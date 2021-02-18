package Main;

import Controllers.ControladorPrTaller;
import DAO.AbstractDAO;
import GUI.LoginView;

public class MainProTaller {

	private static ControladorPrTaller contro;
	private static LoginView login;
	
	public static void main(String[] args) {
		// muestra el Login
		LoginView miLoginView=new LoginView();
		miLoginView.getFrame().setAlwaysOnTop(true);
		miLoginView.getFrame().setVisible(true);
		System.out.println("fin ControladorTaller");
		//contro.inibbdd();
		//login= new LoginView();

	}
}
