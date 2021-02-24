package Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.simple.JSONObject;

public class FlujoClient {
    private Socket Cliente=null;
    private ObjectOutputStream flujoSalida=null;
    private ObjectInputStream flujoEntrada=null;
    
	 /**
     * Constructor
     * @param puerto
     */

    
    public FlujoClient(int puerto) {
    	clientListen(puerto);
	}

	/**
     * Creamos el socket cliente y esperamos respuesta del servidor
     * @param puerto
     */
	public Socket clientListen (int puerto) {
		String Host = "localhost";
        int Puerto = puerto;//puerto remoto

		try {       
	        System.out.println("PROGRAMA CLIENTE INICIADO....");
			Cliente = new Socket(Host, Puerto);
			System.out.println("socket esperando");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Cliente;
	}
	
	/**
	 * El cliente recibe mensaje del servidor
	 */
	public JSONObject clientReceive() {
		JSONObject mensaje=null;
		try {
			// CREO FLUJO DE ENTRADA AL SERVIDOR
			flujoEntrada = new ObjectInputStream(Cliente.getInputStream());
	        mensaje = (JSONObject) flujoEntrada.readObject();
	        // EL SERVIDOR ME ENVIA UN MENSAJE   
	        System.out.println("Recibiendo del SERVIDOR: \n\t" + mensaje);

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return mensaje;
	}
	
	/**
	 * Envia un mensaje como cliente al servidor
	 */
	public void clientSend(JSONObject mensaje) {
        try {
            // CREO FLUJO DE SALIDA AL SERVIDOR
            flujoSalida = new ObjectOutputStream(Cliente.getOutputStream());
            // ENVIO UN SALUDO AL SERVIDOR
			flujoSalida.writeObject(mensaje);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * cierra los objetos
	 */
	public void clientClose() {
		// CERRAR STREAMS Y SOCKETS
        try {
			flujoEntrada.close();
	        flujoSalida.close();
	        Cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
