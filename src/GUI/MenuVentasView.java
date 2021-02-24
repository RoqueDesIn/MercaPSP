package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Common.Constantes;
import Common.FlujoClient;
import DAO.ProductDAO;
import Models.Employee;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Component;

public class MenuVentasView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Employee employee;
	private ImageIcon imagenuser;
	private JPanel panel_general;
	private JPanel PNIzquierdo;
	private JPanel panel_user_actual;
	private JPanel panel_opciones;
	private JPanel PNDerecho;
	private JPanel pnCentral;
	private JTable tbVenta;
	private JTextField tfProducto;
	private JTextField tfCantidad;
	private JTable tbProduct;
	private JScrollPane scrollPane;
	// variables para la tabla
	private DefaultTableModel modeloTbP;
	private DefaultTableModel modeloTbv;

	private TableRowSorter<TableModel> modeloOrdenadoP;
	private TableRowSorter<TableModel> modeloOrdenadoV;
	private JLabel lbCurrUsu;
	private JLabel lb_USER_actual;

	
	/**
	 * Create the application.
	 */
	public MenuVentasView(JSONObject mensaje) {
		employee=new Employee(
				Integer.valueOf(String.valueOf(mensaje.get("id"))), 
				Date.valueOf(String.valueOf(mensaje.get("last_session"))), 
				Date.valueOf(String.valueOf(mensaje.get("contract_date")))
				);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		creaFrame();
		creaPanelTitulo();
		creaPanelGeneral();
		creaPanelIzquierdo();
		creaPanelUser();	
		creaPanelOpciones();
		creaPanelCentral();
		creaPanelDerecho();
		frame.pack();
		
	}
	/**
	 * Crea panel Central
	 */

	private void creaPanelCentral() {
		pnCentral = new JPanel();
		panel_general.add(pnCentral);
		pnCentral.setBackground(Color.decode("#2A9D8F"));
		pnCentral.setLayout(new BoxLayout(pnCentral, BoxLayout.Y_AXIS));
		
		JPanel PNBusquedas = new JPanel();
		pnCentral.add(PNBusquedas);
		PNBusquedas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btCobro = new JButton("Cobro");
		PNBusquedas.add(btCobro);
		btCobro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				enviarCobro();
			}
		});
		
		JLabel LBL_idbusc = new JLabel("Producto");
		PNBusquedas.add(LBL_idbusc);
		
		tfProducto = new JTextField();
		tfProducto.setColumns(10);
		PNBusquedas.add(tfProducto);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		PNBusquedas.add(lblCantidad);
		
		tfCantidad = new JTextField();
		tfCantidad.setColumns(10);
		PNBusquedas.add(tfCantidad);
		
		JScrollPane scrollPane = new JScrollPane();
		pnCentral.add(scrollPane);
		tbVenta = new JTable();
		scrollPane.setViewportView(tbVenta);
		creaTablaVentas();

	}
	
	private void creaTablaVentas() {
		
		tbVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// crea modelo de la tabla
		modeloTbv = new DefaultTableModel(){
			/**
			 * definimos el modelo de la tabla con 
			 * la 2ªcolumna integer
			 * la 3ª y 4ª columna float
			 * y la 1ª String
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public Class getColumnClass(int columna) {
				// primera columna integer
				if (columna == 1) {
					return Integer.class;
				} else {
					if (columna == 2 || columna==3) {
						return Float.class;
					} else {
						return String.class;
					}
				} 
			}
			@Override
			public boolean isCellEditable (int row, int column)
			   {
			       // Aquí devolvemos true o false según queramos que una celda
			       // identificada por fila,columna (row,column), sea o no editable
			       if (column >=0)
			          return false;
			       return true;
			   }
			};
	
			// carga columnas de la tabla
			modeloTbv.addColumn("Producto");
			modeloTbv.addColumn("Cantidad");
			modeloTbv.addColumn("Precio");
			modeloTbv.addColumn("Total");
			//añade el modelo a la tabla
			tbVenta.setModel(modeloTbv);
			
			// hace ordenable la tabla
			modeloOrdenadoV = new TableRowSorter<TableModel>(modeloTbv);
			tbVenta.setRowSorter(modeloOrdenadoV);	
	}

	/**
	 * envia un cobro
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private void enviarCobro(){
		if (!(tfCantidad.getText()==null || tfProducto.getText()==null)) {
			// crea el socket cliente
			FlujoClient client = new FlujoClient(Constantes.PUERTO);
			// crea el objeto a enviar
			JSONObject mensaje= new JSONObject();
			mensaje.put("orden","Cobro");
			mensaje.put("idproduct",tfProducto.getText());
			mensaje.put("quantity",tfCantidad.getText());
			mensaje.put("id_employee",lb_USER_actual.getText());
			
			// el cliente envia la orden de recibir lista de productos
			client.clientSend(mensaje);
			// recibe el mensaje
			mensaje=client.clientReceive();
			cargaLineaV();
		} else {
			JOptionPane.showMessageDialog(frame, "Debe introducir Artículo y Cantidad.", 
	    			"Datos nulos", JOptionPane.ERROR_MESSAGE);
		}

	}
/**
 * carga una linea en la tabla de venta
 */
	private void cargaLineaV() {
		ProductDAO productdao = new ProductDAO();
		Object [] fila = new Object[4];
		
			fila[0]=productdao.getProductById(Integer.valueOf(tfProducto.getText())).getProduct();
			fila[1]=Integer.valueOf(tfCantidad.getText());
			fila[2]=Float.valueOf(tfProducto.getText());
			
			float Cprice = productdao.getProductById(Integer.valueOf(tfProducto.getText())).getCustomer_price();
			fila[3]=Float.valueOf(Integer.valueOf(tfCantidad.getText()) * Cprice);

			modeloTbv.addRow(fila);		
	}

	/**
	 * Crea panel derecho
	 */
	private void creaPanelDerecho() {
		PNDerecho = new JPanel();
		panel_general.add(PNDerecho);
		PNDerecho.setBackground(Color.decode("#2A9D8F"));
		PNDerecho.setLayout(new BoxLayout(PNDerecho, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane();
		PNDerecho.add(scrollPane);
		creaTablaProductos();	
		scrollPane.setViewportView(tbProduct);
	}
/**
 * crea la tabla de productos
 */
	private void creaTablaProductos() {
		tbProduct = new JTable();
		tbProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// crea modelo de la tabla
		modeloTbP = new DefaultTableModel(){
			/**
			 * definimos el modelo de la tabla con 
			 * la 1ª y 5ª columna integer
			 * la 2ª y 3ª columna float
			 * y la 4ª String
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public Class getColumnClass(int columna) {
				// primera columna integer
				if (columna == 0 || columna==4) {
					return Integer.class;
				} else {
					if (columna == 2 || columna==3) {
						return Float.class;
					} else {
						return String.class;
					}
				} 
			}
			@Override
			public boolean isCellEditable (int row, int column)
			   {
			       // Aquí devolvemos true o false según queramos que una celda
			       // identificada por fila,columna (row,column), sea o no editable
			       if (column >=0)
			          return false;
			       return true;
			   }
			};
	
			// carga columnas de la tabla
			modeloTbP.addColumn("ID");
			modeloTbP.addColumn("Producto");
			modeloTbP.addColumn("Precio de venta");
			modeloTbP.addColumn("Precio de compra");
			modeloTbP.addColumn("Stock");
			//añade el modelo a la tabla
			tbProduct.setModel(modeloTbP);
			
			// hace ordenable la tabla
			modeloOrdenadoP = new TableRowSorter<TableModel>(modeloTbP);
			tbProduct.setRowSorter(modeloOrdenadoP);
			
			// crea el socket cliente
			FlujoClient client = new FlujoClient(Constantes.PUERTO);
			// crea el objeto a enviar
			JSONObject mensaje= new JSONObject();
			mensaje.put("orden","Lista");
			// el cliente envia la orden de recibir lista de productos
			client.clientSend(mensaje);
			// recibe el mensaje
			mensaje=client.clientReceive();
			// carga datos de productos en la tabla
			cargaProd(mensaje);	
	}

	/**
	 * Carga la tabla con los productos  de la base de datos
	 * @param mensaje 
	 */
	private void cargaProd(JSONObject mensaje) {
		Object [] fila = new Object[5];
		JSONObject reg = new JSONObject();
		JSONArray json = new JSONArray();
		
		json = (JSONArray) mensaje.get("todo");
		System.out.println(json);
		for (int j=0;j<json.size();j++) {
			reg=(JSONObject) json.get(j);	

			fila[0]=(int) reg.get("id");
			fila[1]=reg.get("product");
			fila[2]=reg.get("customer_price");
			fila[3]=reg.get("provider_price");
			fila[4]=reg.get("stock_amount");
			modeloTbP.addRow(fila);

		}

	}
	
	/**
	 * Crea panel de opciones
	 */
	private void creaPanelOpciones() {
		panel_opciones = new JPanel();
		panel_opciones.setBackground(Color.decode("#2A9D8F"));
		panel_opciones.setLayout(new BoxLayout(panel_opciones, BoxLayout.Y_AXIS));
		PNIzquierdo.add(panel_opciones);
		
		JLabel lb_buscar_cli = new JLabel("1. Cobrar compra");
		lb_buscar_cli.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		lb_buscar_cli.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lb_buscar_cli.setForeground(Color.decode("#E9C46A"));
		panel_opciones.add(lb_buscar_cli);
		lb_buscar_cli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				enviarCobro();
			}
			@Override
			public void mouseEntered (MouseEvent e) {
				lb_buscar_cli.setForeground(Color.RED);
				lb_buscar_cli.setFont(new Font("Tahoma",Font.BOLD,14));

			}
			@Override
			public void mouseExited (MouseEvent e) {
				lb_buscar_cli.setForeground(Color.ORANGE);
				lb_buscar_cli.setFont(new Font("Tahoma",Font.PLAIN,14));
			}
		});

		
		JLabel JLB_ficha_cliente = new JLabel("2. Obtener la caja del día");
		JLB_ficha_cliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLB_ficha_cliente.setForeground(Color.decode("#E9C46A"));
		JLB_ficha_cliente.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		panel_opciones.add(JLB_ficha_cliente);
		JLB_ficha_cliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//  muestra las caja total
				// crea el socket cliente
				FlujoClient client = new FlujoClient(Constantes.PUERTO);
				// crea el objeto a enviar
				JSONObject mensaje= new JSONObject();
				mensaje.put("orden","Caja");
				mensaje.put("id_employee",lb_USER_actual.getText());
				
				// el cliente envia la orden de recibir lista de productos
				client.clientSend(mensaje);
				// recibe el mensaje
				mensaje=client.clientReceive();
				JOptionPane.showMessageDialog(frame, "Total ventas Empleado "+ lb_USER_actual.getText() + "\n " + mensaje.get("Resultado"), 
		    			"Total ventas", JOptionPane.INFORMATION_MESSAGE);
			}
			@Override
			public void mouseEntered (MouseEvent e) {
				JLB_ficha_cliente.setForeground(Color.RED);
				JLB_ficha_cliente.setFont(new Font("Tahoma",Font.BOLD,14));
			}
			@Override
			public void mouseExited (MouseEvent e) {
				JLB_ficha_cliente.setForeground(Color.ORANGE);
				JLB_ficha_cliente.setFont(new Font("Tahoma",Font.PLAIN,14));
			}
		});
		
		
		JLabel JLB_cerrar_sesion = new JLabel("3. Salir");
		JLB_cerrar_sesion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLB_cerrar_sesion.setForeground(Color.decode("#E9C46A"));
		JLB_cerrar_sesion.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		panel_opciones.add(JLB_cerrar_sesion);
		
		JLB_cerrar_sesion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				salir();
			}
			@Override
			public void mouseEntered (MouseEvent e) {
				JLB_cerrar_sesion.setForeground(Color.RED);
				JLB_cerrar_sesion.setFont(new Font("Tahoma",Font.BOLD,14));
			}
			@Override
			public void mouseExited (MouseEvent e) {
				JLB_cerrar_sesion.setForeground(Color.ORANGE);
				JLB_cerrar_sesion.setFont(new Font("Tahoma",Font.PLAIN,14));
			}
		});		
	}

	/**
	 * Crea panel datos del usuario
	 */
	private void creaPanelUser() {
		panel_user_actual = new JPanel();
		panel_user_actual.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 128, 128), Color.ORANGE, null, null));
		PNIzquierdo.add(panel_user_actual);
		panel_user_actual.setBackground(Color.decode("#2A9D8F"));
		panel_user_actual.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
		panel_user_actual.setLayout(new BoxLayout(panel_user_actual, BoxLayout.Y_AXIS));
		
		imagenuser = new ImageIcon(Constantes.ICONO);
		JLabel JLB_image_user = new JLabel(imagenuser);
		JLB_image_user.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		panel_user_actual.add(JLB_image_user);
		
		lbCurrUsu = new JLabel("Usuario actual");
		lbCurrUsu.setHorizontalAlignment(SwingConstants.CENTER);
		lbCurrUsu.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		panel_user_actual.add(lbCurrUsu);
		
		lb_USER_actual = new JLabel("ACTUAL");
		panel_user_actual.add(lb_USER_actual);
		lb_USER_actual.setText(String.valueOf(employee.getId()));
		lb_USER_actual.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		JLabel lbLastSession = new JLabel("Last Session");
		panel_user_actual.add(lbLastSession);
		lbLastSession.setText(String.valueOf(employee.getLast_session()));
		lbLastSession.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		JLabel lbContractDate = new JLabel("Contract Date");
		panel_user_actual.add(lbContractDate);
		lbContractDate.setText(String.valueOf(employee.getContract_date()));
		lbContractDate.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));		
	}
	
	/**
	 * crea panelizquierdo
	 */

	private void creaPanelIzquierdo() {
		PNIzquierdo = new JPanel();
		panel_general.add(PNIzquierdo);
		PNIzquierdo.setBackground(Color.decode("#2A9D8F"));
		PNIzquierdo.setLayout(new BoxLayout(PNIzquierdo, BoxLayout.Y_AXIS));		
	}
	/**
	 * crea panel general
	 */
	private void creaPanelGeneral() {
		panel_general = new JPanel();
		frame.getContentPane().add(panel_general);
		panel_general.setBackground(Color.decode("#2A9D8F"));
		panel_general.setLayout(new BoxLayout(panel_general, BoxLayout.X_AXIS));		
	}
	
	/**
	 * Crea Panel título
	 */

	private void creaPanelTitulo() {
		// panel título
		JPanel PNTitulo = new JPanel();
		PNTitulo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PNTitulo.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(PNTitulo);
		PNTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		PNTitulo.setBackground(Color.decode("#264653"));
		PNTitulo.setMaximumSize(new Dimension(2000,30));;
		
		JLabel LBTitulo = new JLabel("Artículos de los buenos");
		LBTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LBTitulo.setForeground(Color.decode("#F4A261"));
		PNTitulo.add(LBTitulo);		
	}
/**
 * CRea Frame principal
 */
	private void creaFrame() {
		// Frame principal
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#2A9D8F"));
		frame.setBounds(100, 100, 500, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		// implementar las teclas en el frame
		KeyListener listener = new MyKeyListener();
		frame.addKeyListener(listener);
		frame.setFocusable(true);
		frame.requestFocus();
	}

	/*
	 * salir de la vista
	 */
	protected void salir() {
		frame.dispose();
		LoginView miLogin = new LoginView();
		miLogin.getFrame().setAlwaysOnTop(true);
		miLogin.getFrame().setVisible(true);		
	}

	/*
	 * Getters
	 */
	/*
	 * Get Frame
	 */
	public Window getFrame() {
		return frame;
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
					enviarCobro();
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
	
//************************************************************* fin
}
