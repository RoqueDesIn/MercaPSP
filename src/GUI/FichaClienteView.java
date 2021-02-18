package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Common.Constantes;
import Common.Constantes.DigitoDni;
import DAO.ClientesDAO;
import GUI.MenuVentasView.MyKeyListener;
import Models.Clientes;
import Models.Usuarios;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class FichaClienteView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Usuarios usuario;
	private ClientesDAO miCliDAO;
	private JTextField TFDni;
	private JTextField TFNombre;
	private JTextField TFApellidos;
	private JTextField TFProv;
	private JLabel LBUsuario;
	private JLabel LBNomUsu;
	private JTextField TFPob;
	private JTextField TFDir;
	private JLabel LBImg;
	private JButton BTBorra;
	private JButton BTNuevo;
	private JButton BTPrimero;
	private JButton BTSiguiente;
	private JButton BTultimo;
	private JButton BTBuscar;
	private JButton BTAnterior;
	private JLabel LBRegistros;
	private JButton BTGuardar;
	private JButton BTSalir;


	/**
	 * Constructor con usuario e id de cliente
	 * @param miuser
	 * @param idCli
	 */
	public FichaClienteView(Usuarios miuser, int idCli) {
		usuario=miuser;
		miCliDAO = new ClientesDAO ();
		initialize();
		// carga usuario
		LBNomUsu.setText(usuario.getNick());
		if (idCli==0) {
			// carga primer registro
			cargaCliente(miCliDAO.primero());
		} else {
			// carga registro
			cargaCliente(miCliDAO.goToIdCli(idCli));
		}

		// refresca LBRegistros
		refrescaReg();
	}

	/**
	 * Refresca el label de control de registros
	 */
	private void refrescaReg() {
		String p="Registro " + String.valueOf(miCliDAO.buscaDNI(TFDni.getText()) + " de "+ String.valueOf(miCliDAO.count())+".");
		LBRegistros.setText(p);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Frame principal
					frame = new JFrame();
					frame.setAlwaysOnTop(true);
					frame.setBounds(100, 100, 900, 400);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
					
					// implementar las teclas en el frame
					KeyListener listener = new MyKeyListener();
					frame.addKeyListener(listener);
					frame.setFocusable(true);
					frame.requestFocus();
					
					// panel título
					JPanel PNTitulo = new JPanel();
					PNTitulo.setForeground(Color.BLUE);
					PNTitulo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					PNTitulo.setBackground(Color.LIGHT_GRAY);
					frame.getContentPane().add(PNTitulo, "cell 0 0,growx,aligny top");
					PNTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					PNTitulo.setBackground(Color.decode("#264653"));
					
					JLabel LBTitulo = new JLabel("Ficha de clientes");
					LBTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
					LBTitulo.setForeground(Color.ORANGE);
					PNTitulo.add(LBTitulo);
					
					// Panel medio
					JPanel PNMedio = new JPanel();
					PNMedio.setBackground(Color.decode("#2A9D8F"));
					frame.getContentPane().add(PNMedio);
					PNMedio.setLayout(new BoxLayout(PNMedio, BoxLayout.X_AXIS));
					
					// Panel Usuario
					JPanel PNUsuario = new JPanel();
					PNMedio.add(PNUsuario);
					PNUsuario.setLayout(new BoxLayout(PNUsuario,BoxLayout.Y_AXIS));
					PNUsuario.setBackground(Color.decode("#2A9D8F"));
					
					// panel titulo usuario
					JPanel PNTitUsu = new JPanel();
					PNTitUsu.setBackground(Color.decode("#2A9D8F"));
					PNUsuario.add(PNTitUsu);
					PNTitUsu.setLayout(new BoxLayout(PNTitUsu, BoxLayout.X_AXIS));
					
					LBUsuario = new JLabel("Usuario Actual");
					LBUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
					PNTitUsu.add(LBUsuario);
					LBUsuario.setForeground(Color.ORANGE);
					
					// panel imagen
					JPanel PNImg = new JPanel();
					PNImg.setBackground(Color.decode("#2A9D8F"));
					PNUsuario.add(PNImg);
					 PNImg.setLayout(new BoxLayout(PNImg, BoxLayout.Y_AXIS));
					// carga imagen
			         try {
						 BufferedImage img = ImageIO.read(new File(usuario.getFoto()));
						 ImageIcon icon = new ImageIcon(img);
						 LBImg = new JLabel(icon);
						 PNUsuario.add(LBImg);
			          } catch (IOException e) {
			             e.printStackTrace();
			          }
					
					LBNomUsu = new JLabel("Nombre de Usuario");
					PNUsuario.add(LBNomUsu);
		            LBNomUsu.setAlignmentX(0.5f);            
		            LBNomUsu.setText(usuario.getNick());
					
					//****************
		    		// Menú lateral
		    		JPanel PNMenu = new JPanel();
		    		PNMenu.setBackground(Color.decode("#2A9D8F"));
		    		PNUsuario.add(PNMenu);
		    		PNMenu.setLayout(new BoxLayout(PNMenu, BoxLayout.Y_AXIS));
		    		
		    		
		            JLabel JLB_buc_ventas = new JLabel("Buscar ventas");
		    		JLB_buc_ventas.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				VerVentasView miventa = new VerVentasView(usuario);
		    				miventa.getFrame().setVisible(true);
		    				miventa.getFrame().setAlwaysOnTop(true);
		    				frame.dispose();
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				JLB_buc_ventas.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				JLB_buc_ventas.setForeground(Color.ORANGE);
		    			}
		    		});
		    		JLB_buc_ventas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    		JLB_buc_ventas.setForeground(Color.decode("#E9C46A"));
		    		PNMenu.add(JLB_buc_ventas);
		    		
		    		JLabel JLB_buscar_cli = new JLabel("Buscar Clientes");
		    		JLB_buscar_cli.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				BusCliView miBuscCli = new BusCliView(frame, usuario,0);
		    				miBuscCli.getFrame().setAlwaysOnTop(true);
		    				miBuscCli.getFrame().setVisible(true);
		    				frame.dispose();
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				JLB_buscar_cli.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				JLB_buscar_cli.setForeground(Color.ORANGE);
		    			}
		    		});
		    		JLB_buscar_cli.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    		JLB_buscar_cli.setForeground(Color.decode("#E9C46A"));
		    		PNMenu.add(JLB_buscar_cli);
		    		
		    		JLabel JLB_ficha_cliente = new JLabel("Ficha Cliente");
		    		JLB_ficha_cliente.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				FichaClienteView miFichaClientes = new FichaClienteView(usuario,0);
		    				miFichaClientes.getFrame().setAlwaysOnTop(true);
		    				miFichaClientes.getFrame().setVisible(true);
		    				frame.dispose();
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				JLB_ficha_cliente.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				JLB_ficha_cliente.setForeground(Color.ORANGE);
		    			}
		    		});
		    		JLB_ficha_cliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    		JLB_ficha_cliente.setForeground(Color.decode("#E9C46A"));
		    		PNMenu.add(JLB_ficha_cliente);
		    		
		    		JLabel Busca_vehiculos = new JLabel("Busca Vehiculos");
		    		Busca_vehiculos.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				ConsVeh busqueda= new ConsVeh(frame, usuario,0);
		    				busqueda.getFrame().setAlwaysOnTop(true);
		    				busqueda.getFrame().setVisible(true);
		    				frame.dispose();
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				Busca_vehiculos.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				Busca_vehiculos.setForeground(Color.ORANGE);
		    			}
		    		});
		    		Busca_vehiculos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    		Busca_vehiculos.setForeground(Color.decode("#E9C46A"));
		    		PNMenu.add(Busca_vehiculos);
		    		
		    		JLabel JLB_ficha_vehiculo = new JLabel("Ficha Vehiculo");
		    		JLB_ficha_vehiculo.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				FichaVehiculoView miFicVeh = new FichaVehiculoView (usuario,0);
		    				miFicVeh.getFrame().setVisible(true);
		    				miFicVeh.getFrame().setAlwaysOnTop(true);
		    				frame.dispose();
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				JLB_ficha_vehiculo.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				JLB_ficha_vehiculo.setForeground(Color.ORANGE);
		    			}
		    		});
		    		JLB_ficha_vehiculo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		    		JLB_ficha_vehiculo.setForeground(Color.decode("#E9C46A"));
		    		PNMenu.add(JLB_ficha_vehiculo);
		    		
		    		
		    		JLabel JLB_cerrar_sesion = new JLabel("Cerrar sesi\u00F3n");
		    		JLB_cerrar_sesion.addMouseListener(new MouseAdapter() {
		    			@Override
		    			public void mouseClicked(MouseEvent e) {
		    				frame.dispose();
		    				LoginView miLogin = new LoginView();
		    				miLogin.getFrame().setAlwaysOnTop(true);
		    				miLogin.getFrame().setVisible(true);
		    			}
		    			@Override
		    			public void mouseEntered (MouseEvent e) {
		    				JLB_cerrar_sesion.setForeground(Color.RED);
		    			}
		    			@Override
		    			public void mouseExited (MouseEvent e) {
		    				JLB_cerrar_sesion.setForeground(Color.ORANGE);
		    			}
		    		});
		    		JLB_cerrar_sesion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		    		JLB_cerrar_sesion.setForeground(Color.decode("#E76F51"));
		    		PNMenu.add(JLB_cerrar_sesion);

			//***************		
					
					// panel central
					JPanel PNCentral = new JPanel();
					PNMedio.add(PNCentral);
					PNCentral.setBackground(Color.decode("#2A9D8F"));
					PNCentral.setLayout(new BoxLayout(PNCentral, BoxLayout.Y_AXIS));
		
			// panel linea 1
			JPanel PNLinea1 = new JPanel();
			PNCentral.add(PNLinea1, "cell 0 0,grow");
			PNLinea1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			PNLinea1.setBackground(Color.decode("#2A9D8F"));
			
			JLabel LBDni = new JLabel("DNI/NIE");
			PNLinea1.add(LBDni);
			
				TFDni = new JTextField();
				PNLinea1.add(TFDni);
				TFDni.setColumns(10);
				
				JLabel LBNombre = new JLabel("Nombre");
				PNLinea1.add(LBNombre);
				
					TFNombre = new JTextField();
					PNLinea1.add(TFNombre);
					TFNombre.setColumns(10);
			
			// panel linea 2
			JPanel PNLinea2 = new JPanel();
			PNCentral.add(PNLinea2, "cell 0 1,grow");
			PNLinea2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			PNLinea2.setBackground(Color.decode("#2A9D8F"));
				
				JLabel LBApellidos = new JLabel("Apellidos");
				PNLinea2.add(LBApellidos);
				
					TFApellidos = new JTextField();
					PNLinea2.add(TFApellidos);
					TFApellidos.setColumns(10);
					
					JLabel LBDir = new JLabel("Dirección");
					PNLinea2.add(LBDir);
					
						TFDir = new JTextField();
						PNLinea2.add(TFDir);
						TFDir.setColumns(10);
			
			// panel linea 3
			JPanel PNLinea3 = new JPanel();
			PNCentral.add(PNLinea3, "cell 0 2,grow");
			PNLinea3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			PNLinea3.setBackground(Color.decode("#2A9D8F"));
				
			JLabel LBProv = new JLabel("Provincia");
			PNLinea3.add(LBProv);
			
			TFProv = new JTextField();
			PNLinea3.add(TFProv);
			TFProv.setColumns(10);
			
			// panel linea 4
			JPanel PNLinea4 = new JPanel();
			PNCentral.add(PNLinea4, "cell 0 3,grow");
			PNLinea4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			PNLinea4.setBackground(Color.decode("#2A9D8F"));
			
			JLabel LBPob = new JLabel("Población");
			PNLinea4.add(LBPob);
			
			TFPob = new JTextField();
			PNLinea4.add(TFPob);
			TFPob.setText("Málaga");
			TFPob.setColumns(10);
			
			// Panel para los botones del control de registros
			JPanel panelBotoneras = new JPanel();
			panelBotoneras.setMaximumSize(new Dimension(1000, 60));
			panelBotoneras.setLayout(new BoxLayout(panelBotoneras, BoxLayout.Y_AXIS));
			PNCentral.add(panelBotoneras, "cell 0 4");
			
			JPanel panelBotonera = new JPanel();
			panelBotonera.setBackground(new Color(42, 157, 143));
			panelBotoneras.add(panelBotonera);
			
			BTBuscar = new JButton("Buscar");
			panelBotonera.add(BTBuscar);
			BTBuscar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// llamada a buscar cliente
					frame.dispose();
					BusCliView miBusqueda = new BusCliView(frame, usuario,1);
					miBusqueda.getFrame().setAlwaysOnTop(true);
					miBusqueda.getFrame().setVisible(true);					
				}
			});
			
			BTBorra = new JButton("Borrar");
			panelBotonera.add(BTBorra);
			BTBorra.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							frame, 
							"¿Seguro que quiere borrar el registro?", 
							"Borrar registro", 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_NO_OPTION) {
						miCliDAO.borraCliente(TFDni.getText());	
						Clientes miCliente = miCliDAO.primero();
						// cargar cliente en form
						cargaCliente(miCliente);
						refrescaReg();
					}

				}
			});
			
			BTGuardar = new JButton("Guardar");
			panelBotonera.add(BTGuardar);
			BTGuardar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (comprobarDatos()) {
						// crea el nuevo cliente
						Clientes cliente=new Clientes(0,TFDni.getText(),TFNombre.getText(),TFApellidos.getText(),TFDir.getText(),
								TFProv.getText(),TFPob.getText(),Date.valueOf(LocalDate.now()));
						// comprobar si ya existe el registro
						if (miCliDAO.ComprobarCliente(TFDni.getText())) {
							// guardar el registro
							miCliDAO.updateCliente(cliente);	
						} else {
							// insertar el registro
							miCliDAO.addCliente(cliente);
						}
						daBotones(true);
						refrescaReg();
					}
				}
			});
			
			BTNuevo = new JButton("Nuevo");
			panelBotonera.add(BTNuevo);
			BTNuevo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TFDni.setText("");
					TFDni.requestFocus();
					TFNombre.setText("");
					TFApellidos.setText("");
					TFDir.setText("");
					TFProv.setText("");
					TFPob.setText("");
					daBotones(false);
				}
			});
			
			BTSalir = new JButton("Salir");
			panelBotonera.add(BTSalir);
			BTSalir.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					salir();
				}
			});
			
			JPanel panelRegistros = new JPanel();
			panelRegistros.setBackground(Color.decode("#2A9D8F"));
			panelBotoneras.add(panelRegistros);
			panelRegistros.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
			
			BTPrimero = new JButton("<<");
			BTPrimero.setToolTipText("Primer registro.");
			BTPrimero.setForeground(Color.RED);
			BTPrimero.setFont(new Font("Tahoma", Font.BOLD, 8));
			panelRegistros.add(BTPrimero);
			BTPrimero.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Clientes miCliente = miCliDAO.primero();
					// cargar cliente en form
					cargaCliente(miCliente);
					refrescaReg();
				}
			});
			
			BTAnterior = new JButton("<");
			BTAnterior.setToolTipText("Registro anterior.");
			BTAnterior.setForeground(Color.RED);
			BTAnterior.setFont(new Font("Tahoma", Font.BOLD, 8));
			panelRegistros.add(BTAnterior);
			BTAnterior.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Clientes miCliente = miCliDAO.anterior(TFDni.getText());
					// cargar cliente en form
					if (miCliente!=null){
						cargaCliente(miCliente);
						refrescaReg();
					}

				}
			});
			
			LBRegistros = new JLabel("No se han encontrado registros.");
			LBRegistros.setBorder(new LineBorder(Color.BLUE, 1, true));
			LBRegistros.setBackground(Color.WHITE);
			panelRegistros.add(LBRegistros);
			
			BTSiguiente = new JButton(">");
			BTSiguiente.setToolTipText("Registro siguiente.");
			BTSiguiente.setForeground(Color.RED);
			BTSiguiente.setFont(new Font("Tahoma", Font.BOLD, 8));
			panelRegistros.add(BTSiguiente);
			BTSiguiente.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Clientes miCliente = miCliDAO.siguiente(TFDni.getText());
					if (miCliente!=null){
						cargaCliente(miCliente);
						refrescaReg();
					}
				}
			});
			
			BTultimo = new JButton(">>");
			BTultimo.setToolTipText("Último registro.");
			BTultimo.setForeground(Color.RED);
			BTultimo.setFont(new Font("Tahoma", Font.BOLD, 8));
			panelRegistros.add(BTultimo);
			BTultimo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Clientes miCliente = miCliDAO.ultimo();
					// cargar cliente en form
					cargaCliente(miCliente);
					refrescaReg();
				}
			});
	}
	
	// comprueba que los datos son correctos 
	protected boolean comprobarDatos() {

		if (!Constantes.comprobarDNI(TFDni.getText()) || (TFDni.getText().length()==0) || (TFApellidos.getText().length()==0)  || (TFDir.getText().length()==0) 
				||  (TFNombre.getText().length()==0)  || (TFPob.getText().length()==0)  || (TFProv.getText().length()==0)) {
			return false;
		} else {
			return true;
		}

	}

		/**
	 * Activa/desactiva botones
	 * @param estado
	 */
	protected void daBotones(boolean estado) {
		BTBorra.setEnabled(estado);
		BTAnterior.setEnabled(estado);
		BTNuevo.setEnabled(estado);
		BTPrimero.setEnabled(estado);
		BTSiguiente.setEnabled(estado);
		BTultimo.setEnabled(estado);
		BTBuscar.setEnabled(estado);
	}

	/**
	 * Carga el formulario con los datos de un cliente 
	 * @param miCliente
	 */
	protected void cargaCliente(Clientes miCliente) {
		TFDni.setText(miCliente.getDNI());
		TFNombre.setText(miCliente.getNombre());
		TFApellidos.setText(miCliente.getApellido());
		TFDir.setText(miCliente.getDireccion());
		TFProv.setText(miCliente.getProvincia());
		TFPob.setText(miCliente.getProvincia());
	}

	/**
	 * Get Frame
	 */
	public Window getFrame() {
		return frame;
	}
	
	/**
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
			}
			
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
		}
	
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

	public void salir() {
		if (usuario.getRango().equals("jefeTaller")) {
			MenuJTallerView view= new MenuJTallerView(usuario);
			view.getFrame().setVisible(true);
			view.getFrame().setAlwaysOnTop(true);
			frame.dispose();
		} else {
		frame.dispose();
		MenuVentasView miMenuVentas = new MenuVentasView(usuario);
		miMenuVentas.getFrame().setAlwaysOnTop(true);
		miMenuVentas.getFrame().setVisible(true);
		}
	}
	
//************************************************************* fin
	
}
