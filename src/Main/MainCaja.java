package Main;

import GUI.LoginView;

public class MainCaja {

	
	public static void main(String[] args) {
		// muestra el Login
		LoginView miLoginView=new LoginView();
		miLoginView.getFrame().setAlwaysOnTop(true);
		miLoginView.getFrame().setVisible(true);
		
		System.out.println("fin ControladorTaller");
	}
}
