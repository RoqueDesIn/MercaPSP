package GUI;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

import Common.Constantes;
import DAO.UsuarioDAO;
import GUI.FichaClienteView.MyKeyListener;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JPasswordField;
import java.awt.CardLayout;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;

public class LoginView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private UsuarioDAO miuserdao;
	private JTextField JTF_usuario;
	private ImageIcon miimagen;
	private JPasswordField Jpass;
	

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
		miuserdao= new UsuarioDAO();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		// implementar las teclas en el frame
		KeyListener listener = new MyKeyListener();
		frame.addKeyListener(listener);
		frame.setFocusable(true);
	
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		
		JPanel Panel_login = new JPanel();
		panel.add(Panel_login, BorderLayout.NORTH);
		Panel_login.setBackground(Color.decode("#264653"));
		
		JLabel JLB_login = new JLabel("Login");
		JLB_login.setForeground(Color.ORANGE);
		JLB_login.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		Panel_login.add(JLB_login);
		
		JPanel Panel_central = new JPanel();
		panel.add(Panel_central, BorderLayout.CENTER);
		Panel_central.setBackground(Color.decode("#2A9D8F"));
		
		JPanel Panel_contenedor = new JPanel();
		Panel_central.add(Panel_contenedor);
		Panel_contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel_contenedor.setBackground(Color.decode("#2A9D8F"));
		
		JPanel panel_imagen = new JPanel();
		Panel_contenedor.add(panel_imagen);
		panel_imagen.setBackground(Color.decode("#2A9D8F"));
		
		miimagen= new ImageIcon("src/png/logo_login.png");
		JLabel JLB_imagen = new JLabel(miimagen);
		panel_imagen.add(JLB_imagen);
		
		
		
		
		
		JPanel panel_login = new JPanel();
		Panel_contenedor.add(panel_login);
		panel_login.setLayout(new BorderLayout(0, 0));
		panel_login.setBackground(Color.decode("#2A9D8F"));
		
		JPanel Panel_usuario = new JPanel();
		panel_login.add(Panel_usuario, BorderLayout.NORTH);
		Panel_usuario.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel_usuario.setBackground(Color.decode("#2A9D8F"));
		
		JLabel JLB_usuario = new JLabel("Usuario");
		JLB_usuario.setFont(new Font("Tahoma", Font.BOLD, 10));
		Panel_usuario.add(JLB_usuario);
		
		JTF_usuario = new JTextField();
		
		JTF_usuario.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
	                   login();
	                }
	                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                	salir();
					}				
			}			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		Panel_usuario.add(JTF_usuario);
		JTF_usuario.setColumns(10);
		JTF_usuario.requestFocus();
		
		JPanel panel_passwd = new JPanel();
		panel_login.add(panel_passwd, BorderLayout.CENTER);
		panel_passwd.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_passwd.setBackground(Color.decode("#2A9D8F"));
		
		JLabel JLB_passwd = new JLabel("Contrase\u00F1a");
		panel_passwd.add(JLB_passwd);
		
		Jpass = new JPasswordField();
		Jpass.setColumns(10);
		panel_passwd.add(Jpass);
		
		Jpass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
	                   login();
	                }
	                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                	if(JOptionPane.showConfirmDialog(frame, "¿Seguro que quiere salir de la aplicación?", 
	                			"Salir", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
	                		System.exit(0);;
	                	}
					}
			}
		});
		
		JPanel panel_boton = new JPanel();
		panel_login.add(panel_boton, BorderLayout.SOUTH);
		panel_boton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_boton.setBackground(Color.decode("#2A9D8F"));
		
		JButton JBT_buscar = new JButton("Iniciar Sesi\u00F3n");
		JBT_buscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login();
			}
		});
		JBT_buscar.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
	                   login();
	                }
	                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                	salir();
					}				
			}			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		panel_boton.add(JBT_buscar);
		
		frame.pack();
	}

	/*
	 * Sale de la aplicación
	 */
	protected void salir() {
    	if(JOptionPane.showConfirmDialog(frame, "¿Seguro que quiere salir de la aplicación?", 
    			"Salir", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
    		System.exit(0);;
    	}		
	}

	/**
	 * Get JFrame
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/*
	 * Comprueba el login 
	 */
	public void login() {
		if ((JTF_usuario.getText().length()>0)||Jpass.getText().length()>0) {
			// encriptar contraseña
			String cadena="";
			cadena = Constantes.encriptar(Jpass.getPassword());
			
			// comprobar login
			if (miuserdao.compobarlogin(JTF_usuario.getText(),cadena)==true) {
				frame.setVisible(false);
				navegacion();
			} else {
				Jpass.setBackground(Color.decode("#FF0000"));
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Debe rellenar todos los datos");
		}

	}
	


	/**
	 * navegación al menú inicial
	 */
	private void navegacion() {
		switch ( miuserdao.getuser().getRango()) {
			case "vendedor":
				//llamar a la GUI de menú inicial ventas
				MenuVentasView miMenuV = new MenuVentasView(miuserdao.getuser());
				miMenuV.getFrame().setVisible(true);
				break;
			case "mecanico":
				//llamar a la GUI de menú inicial taller
				MenuMecanicoView miMenuM = new MenuMecanicoView(miuserdao.getuser());
				miMenuM.getFrame().setVisible(true);
				break;
			case "jefe":
				//llamar a la GUI de menú inicial Jefe
				MenuJefeMax miMenuJ = new MenuJefeMax(miuserdao.getuser());
				miMenuJ.getFrame().setVisible(true);
				break;
			case "jefeTaller":
				//llamar a la GUI de menú inicial jefe de taller
				MenuJTallerView miMenuJT = new MenuJTallerView(miuserdao.getuser());
				miMenuJT.getFrame().setVisible(true);
				
				break;
			default:
				System.out.println("¿Comorrrrrrrr?");
		}			
	}
	
	/*
	 * Implementa keyEvents
	 */
	public class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {
			int ascii = (int) arg0.getKeyChar();
			System.out.println(ascii);
			switch (arg0.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					salir();
					break;	
				case KeyEvent.VK_ENTER:
					login();
					frame.requestFocus();
			}
			
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
		}
	
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	
	//******************* fin
}
