package juego;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import conexion.Conexion;

public class Server {

	public static Socket cliente = null;

	public static Map<String, Socket> clientes = new HashMap<>();

	public static void main(String[] args) {
		int puerto = 1233; // Puerto en el que escuchará el servidor

		//System.out.println(clientes.size());
		
		try {
			ServerSocket servidor = new ServerSocket(puerto);
			System.out.println("Servidor iniciado. Esperando conexiones...");

			while (true) {

				cliente = servidor.accept();
				System.out.println("Nueva conexión aceptada: " + cliente.getInetAddress().getHostAddress());
				System.out.println("nombre:" + Conexion.nombre);

				if (clientes.size() < 2) {
					boolean b = true;
					// Leer el nombre del cliente
					InputStream inputStream = cliente.getInputStream();
					byte[] nombreBytes = new byte[1024];
					int bytesRead = inputStream.read(nombreBytes);
					String nombreCliente = new String(nombreBytes, 0, bytesRead);

					clientes.put(nombreCliente, cliente);
					
					System.out.println(clientes.size());

					System.out.println("Usuario: " + nombreCliente);

					// Aquí puedes manejar la conexión con el cliente en un hilo separado
					Thread clienteThread = new Thread(new ClienteHandler(cliente, nombreCliente));
					clienteThread.start();
					if (clientes.size() == 2 && b) {
						enviarMensaje("tu turno", 0);
						enviarMensaje("espera turno", 1);
						b = false;
					}
				} else {
					System.out.println(
							"Servidor lleno. Rechazando conexión de " + cliente.getInetAddress().getHostAddress());
					cliente.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ClienteHandler implements Runnable {
		private Socket cliente;
		private String nombreCliente;

		public ClienteHandler(Socket cliente, String clienteId) {
			this.cliente = cliente;
			this.nombreCliente = clienteId;
		}

		@Override
		public void run() {
			try {
				InputStream inputStream = cliente.getInputStream();
				byte[] buffer = new byte[1024];
				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					String mensaje = new String(buffer, 0, bytesRead);
					//System.out.println("Mensaje recibido de " + nombreCliente + ": " + mensaje);

					// Enviar el mensaje a todos los otros clientes
					enviarMensajeAClientes(mensaje, nombreCliente);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					cliente.close();
					clientes.remove(nombreCliente);
					System.out.println("Cliente desconectado: " + nombreCliente);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// este metodo se utiliza para mandar un mensaje a un jugador en especifico
	public static void enviarMensaje(String mensajePriv, int posicion) {
		if (posicion < clientes.size()) {
			String nombreUsuario = (String) clientes.keySet().toArray()[posicion];
			Socket cliente = clientes.get(nombreUsuario);
			/*
			System.out.println(nombreUsuario + cliente);
			System.out.println(clientes.size());
			System.out.println(posicion);
			System.out.println(mensajePriv);
			*/
			try {
				OutputStream outputStream = cliente.getOutputStream();
				PrintWriter writer = new PrintWriter(outputStream);
				writer.println(mensajePriv);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No se encontró cliente en la posición " + posicion);
		}
	}

	// esta funcion checa el mensaje y se le manda a todas las personas conectadas
	// que no sean el que lo mando
	private static void enviarMensajeAClientes(String mensajeA, String remitente) {
		try {

			for (Map.Entry<String, Socket> entry : clientes.entrySet()) {
				String clienteId = entry.getKey();
				Socket cliente = entry.getValue();

				if (!clienteId.equals(remitente)) {
					OutputStream outputStream = cliente.getOutputStream();
					PrintWriter writer = new PrintWriter(outputStream);
					writer.println(mensajeA); // Agregar un carácter de nueva línea al final del mensaje
					writer.flush();
				}
			}
			if (mensajeA.equals("Gane")) {
				// 0=x
				
				//System.out.println(clientes.size());
				
				String ganador = null;

				String usuarioX = (String) clientes.keySet().toArray()[0];
				//Socket nombreX = clientes.get(usuarioX);

				String usuarioO = (String) clientes.keySet().toArray()[1];
				//Socket nombreO = clientes.get(usuarioO);

				if (usuarioX.equals(remitente)) {
					ganador = usuarioX;
				} else {
					ganador = usuarioO;
				}

				PreparedStatement sentencia = null;
				// \u00f1 es la letra n con ceja
				// se prepara el codigo sql para ingresar datos
				String sql = "exec crearPartida ?,?,?";
				try {// preparedStatement es para ingresar datos
					sentencia = Conexion.getConnection().prepareStatement(sql);
					sentencia.setString(1, usuarioX);
					sentencia.setString(2, usuarioO);
					sentencia.setString(3, ganador);

					if (sentencia.executeUpdate() > 0) {
						// si se hace el insert muestra este mensaje
						clientes.clear();
						JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente" + clientes, "InformaciÃ³n",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (SQLException e) {
					System.err.print("Error de SQL " + e.getMessage());
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "No se Registro, verifique la consola para ver el error");

					for (Map.Entry<String, Socket> entry : clientes.entrySet()) {
						String clienteId = entry.getKey();
						Socket cliente = entry.getValue();
						cliente.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
