package GUI;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import Common.Constantes;
import Common.FlujoClient;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;

public class LoginView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JSONObject mensaje;
	private JTextField JTF_usuario;
	private ImageIcon miimagen;
	private JPanel panel;
	private JPanel Panel_central;
	private JPanel Panel_contenedor;
	private JPanel panel_login;
	private JPanel Panel_usuario;
	
	

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
		mensaje= new JSONObject();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		creaFrame();
		creaPanelTitulo();
		creaPanelCentral();
		creaPanelContenedor();
		creaPanelImagen();
		creaPanelLogin();
		creaPanelUsuario();
		creaPanelBotón();		
		frame.pack();
	}
	
	/**
	 * crea panel de botón
	 */
	private void creaPanelBotón() {
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
	}

	/**
	 * crea panel linea de usuario
	 */
	private void creaPanelUsuario() {
		Panel_usuario = new JPanel();
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
	}

	/**
	 * crea panel linea de login
	 */
	private void creaPanelLogin() {
		panel_login = new JPanel();
		Panel_contenedor.add(panel_login);
		panel_login.setLayout(new BorderLayout(0, 0));
		panel_login.setBackground(Color.decode("#2A9D8F"));		
	}

	/**
	 * crea el panel de imagen
	 */
	private void creaPanelImagen() {
		// carga imagen
        try {
			 BufferedImage img = ImageIO.read(new File(Constantes.ICONO));
			 ImageIcon icon = new ImageIcon(img);
			 JLabel LBImg = new JLabel(icon);
			 Panel_contenedor.add(LBImg);
         } catch (IOException e) {
            e.printStackTrace();
         }
        
		JPanel panel_imagen = new JPanel();
		Panel_contenedor.add(panel_imagen);
		panel_imagen.setBackground(Color.decode("#2A9D8F"));
		
		miimagen= new ImageIcon("src/png/logo_login.png");
		JLabel JLB_imagen = new JLabel(miimagen);
		panel_imagen.add(JLB_imagen);		
	}

	/**
	 * crea el panel contenedor
	 */
	private void creaPanelContenedor() {
		Panel_contenedor = new JPanel();
		Panel_central.add(Panel_contenedor);
		Panel_contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel_contenedor.setBackground(Color.decode("#2A9D8F"));		
	}

	/**
	 * Crea el panel central
	 */
	private void creaPanelCentral() {
		Panel_central = new JPanel();
		panel.add(Panel_central, BorderLayout.CENTER);
		Panel_central.setBackground(Color.decode("#2A9D8F"));		
	}

	/**
	 * Crea el panel titulo
	 */
	private void creaPanelTitulo() {
		JPanel Panel_login = new JPanel();
		panel.add(Panel_login, BorderLayout.NORTH);
		Panel_login.setBackground(Color.decode("#264653"));
		
		JLabel JLB_login = new JLabel("Login");
		JLB_login.setForeground(Color.ORANGE);
		JLB_login.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		Panel_login.add(JLB_login);		
	}

	/**
	 * Crea el frame
	 */
	private void creaFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		// implementar las teclas en el frame
		KeyListener listener = new MyKeyListener();
		frame.addKeyListener(listener);
		frame.setFocusable(true);
		
		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
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
		FlujoClient client=null;
		int puerto= 6125;
		
		if ((JTF_usuario.getText().length()>0)) {
			// crea el cliente
			client= new FlujoClient(puerto);
			// crea el objeto a enviar
			mensaje.put("orden","Login");
			mensaje.put("id",JTF_usuario.getText());
			// el cliente envia el login al server
			client.clientSend(mensaje);
			// recibe el mensaje
			JSONObject mensaje=client.clientReceive();
			if (mensaje!=null) {
				frame.dispose();;
				MenuVentasView miMenuV = new MenuVentasView(mensaje);
				miMenuV.getFrame().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(frame, "Empleado inexistente");
			}
			// cierra el cliente
			client.clientClose();
		} else {
			JOptionPane.showMessageDialog(frame, "Debe rellenar el número de empleado");
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
