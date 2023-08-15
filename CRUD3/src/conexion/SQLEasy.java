package conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import userInterface.elegirIdioma;

public class SQLEasy {
	
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);
	
	public static void updName(String name) {
		String sql = "update jugadores set nombre = ? where idJugador = ?";

		try {
			// se Prepara el sql
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, conexion.Conexion.idJugador);
			// Se ejecuta el sql

			if (ps.executeUpdate() > 0) {
				// si se hace el insert muestra este mensaje
				
				//Nombre actualizado con exito
				JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		} catch (Exception e) {
			
			//Nombre no valido
			JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void updPass(String pass) {
		
		String sql = "update jugadores set contrase\\u00f1a = ? where idJugador = ?";

		try {
			// se Prepara el sql
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setString(1, pass);
			ps.setInt(2, conexion.Conexion.idJugador);
			// Se ejecuta el sql

			if (ps.executeUpdate() > 0) {
				// si se hace el insert muestra este mensaje
				
				//password actualizada con exito
				JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		} catch (Exception e) {
			
			//password no valida
			JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public static void updNumero(String numero) {
		
		
		String sql = "update jugadores set telefono = ? where idJugador = ?";

		try {
			// se Prepara el sql
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setString(1, numero);
			ps.setInt(2, conexion.Conexion.idJugador);
			// Se ejecuta el sql

			if (ps.executeUpdate() > 0) {
				// si se hace el insert muestra este mensaje
				
				//Nombre actualizado con exito
				JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		} catch (Exception e) {
			
			//Nombre no valido
			JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"), messages.getString("Information"),
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public static void insertJugador(String pregunta, String respuesta) {
	    String sql = "insert into preguntasSeguridad values (?,?,?)";
	    
	    try{
	    	PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
	        ps.setInt(1, conexion.Conexion.idJugador);
	        ps.setString(2, pregunta);
	        ps.setString(3, respuesta);
	        
	        if (ps.executeUpdate() > 0) {
	        	//todo bien 
	            JOptionPane.showMessageDialog(null, messages.getString("succesfulInsert"), messages.getString("Information"),
	                    JOptionPane.INFORMATION_MESSAGE);
	        } else {
	        	
	        	//todo mal :c
	            JOptionPane.showMessageDialog(null, messages.getString("unsuccessfulInsert"), messages.getString("Information"),
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (Exception e) {
	    	//un error aun peor
	        JOptionPane.showMessageDialog(null, messages.getString("databaseError"), messages.getString("Error"),
	                JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}

}

