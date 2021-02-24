package Common;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

public class FlujoServer {
    private Socket clienteConectado = null;
	private ServerSocket servidor = null;
    private ObjectInputStream flujoEntrada=null;
    private ObjectOutputStream flujoSalida=null;
    
    /**
     * Constructor
     * @param puerto
     */
	public FlujoServer(int puerto) {
		serverListen(puerto);
	}


	/**
	 * crea el socket servidor y espera respuesta del cliente
	 * @param puerto
	 */
	public ServerSocket serverListen (int puerto) {	
        try {
        	int numeroPuerto = puerto;// Puerto
            servidor = new ServerSocket(numeroPuerto);
            System.out.println("Esperando al cliente.....");
			// esperando respuesta del cliente
            clienteConectado = servidor.accept();
            System.out.println("el cliente acepta...");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servidor;
	}
	
	
	/**
	 * Creamos flujo de entrada al servidor
	 * y recibimos mensaje del cliente
	 */
	public JSONObject serverReceive () {
		JSONObject mensaje=null;
        try {
        	// CREO FLUJO DE ENTRADA DEL CLIENTE   
	        flujoEntrada = new ObjectInputStream(clienteConectado.getInputStream());
	        mensaje= (JSONObject) flujoEntrada.readObject();
	        // EL servidor ENVIA UN MENSAJE
	        System.out.println("Recibiendo del CLIENTE: \n\t" + mensaje);
	        
        } catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return mensaje;
	}
	
	/**
	 * creamos flujo de salida y
	 * enviamos mensaje al cliente
	 */
	public void serverSend(JSONObject mensaje) {
		// CREO FLUJO DE SALIDA AL CLIENTE
        try {
	        flujoSalida = new ObjectOutputStream(clienteConectado.getOutputStream());
	        // ENVIO UN SALUDO AL CLIENTE
	        flujoSalida.writeObject(mensaje);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cerramos todos los objetos usados
	 */
	public void serverClose() {
        // CERRAR STREAMS Y SOCKETS
        try {
	        flujoSalida.close();
	        clienteConectado.close();
	        servidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
