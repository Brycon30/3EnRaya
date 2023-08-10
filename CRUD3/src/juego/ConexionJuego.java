package juego;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ConexionJuego {
	//Con esta clase guardamos el estado de la conexion
	public static final String SERVIDOR_IP = "localhost"; // Cambia esta dirección IP por la del servidor
	public static final int SERVIDOR_PUERTO = 1233; // Cambia este puerto por el del servidor

    public static Socket cliente;
    public static OutputStream outputStream;

	public static void main(String[] args) {
		

	}
	
	public static void conectarAlServidor(String nombreCliente) {//Esta funcion sirve para conectarse al servidor
    	try {
            cliente = new Socket(SERVIDOR_IP, SERVIDOR_PUERTO);
            outputStream = cliente.getOutputStream();

            // Enviar el nombre del cliente al servidor
            
                outputStream.write(nombreCliente.getBytes());
                outputStream.flush();
                
            
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
