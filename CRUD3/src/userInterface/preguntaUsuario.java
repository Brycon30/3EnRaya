package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class preguntaUsuario extends JFrame {

	private JPanel contentPane;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);
	private JTextField tfRespuesta;
	public static String preguntaField = "";
	public static String respeustaField = "";
	public static String passwordField = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					preguntaUsuario frame = new preguntaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void conseguirPregunta(int id) {
		String sql = "select pregunta from preguntasSeguridad where usuario = ?";
		//System.out.println("Se entra");

		try {
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				preguntaField = rs.getString("pregunta");
			}

		} catch (Exception e) {
			// un error aun peor
			// JOptionPane.showMessageDialog(null, messages.getString("databaseError"), "",
			// JOptionPane.ERROR_MESSAGE);
			// .printStackTrace();
		}

		//System.out.println(id);

	}

	public static void conseguirRespuesta(int id) {
		String sql = "select respuesta from preguntasSeguridad where usuario = ?";
		//System.out.println("Se entra");

		try {
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				respeustaField = rs.getString("respuesta");
			}

		} catch (Exception e) {
			// un error aun peor
			// JOptionPane.showMessageDialog(null, messages.getString("databaseError"), "",
			// JOptionPane.ERROR_MESSAGE);
			// .printStackTrace();
		}

		//System.out.println(id);

	}

	public static void conseguirPassword(int id) {
		String sql = "select contrase\u00f1a from jugadores where idJugador = ?";
		//System.out.println("Se entra");

		try {
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				passwordField = rs.getString("contrase\u00f1a");
			}

			//System.out.println(passwordField);

		} catch (Exception e) {
			// un error aun peor
			// JOptionPane.showMessageDialog(null, messages.getString("databaseError"), "",
			// JOptionPane.ERROR_MESSAGE);
			// .printStackTrace();
		}

		//System.out.println(id);

	}

	/**
	 * Create the frame.
	 */
	public preguntaUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		conseguirPregunta(iftp.idDesdeIftp);
		conseguirRespuesta(iftp.idDesdeIftp);

		JLabel lblPregunta = new JLabel(preguntaField);
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta.setForeground(Color.WHITE);
		lblPregunta.setFont(new Font("Arial", Font.BOLD, 30));
		lblPregunta.setBounds(10, 11, 414, 86);
		contentPane.add(lblPregunta);

		if (preguntaField.isEmpty()) {
			lblPregunta.setText(messages.getString("noQ"));
		}

		tfRespuesta = new JTextField();
		tfRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		tfRespuesta.setForeground(new Color(30, 144, 255));
		tfRespuesta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfRespuesta.setColumns(10);
		tfRespuesta.setBounds(67, 108, 311, 36);
		contentPane.add(tfRespuesta);

		JButton btnIngresar = new JButton(messages.getString("Enter"));
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conseguirPassword(iftp.idDesdeIftp);
				String respuestaUsuario = tfRespuesta.getText();
				if (respuestaUsuario.equals(respeustaField)) {
					if (respeustaField.isEmpty()) {
						
					} else {
						JOptionPane.showMessageDialog(null, messages.getString("password") + ": " + passwordField);
					}
				} else {
					JOptionPane.showMessageDialog(null, messages.getString("invalidAns"));
				}
			}
		});
		btnIngresar.setForeground(new Color(30, 144, 255));
		btnIngresar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnIngresar.setBackground(Color.WHITE);
		btnIngresar.setBounds(254, 155, 170, 51);
		contentPane.add(btnIngresar);

		JButton btnVolver = new JButton(messages.getString("Back"));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login l = new Login();
				l.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
			}
		});
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBounds(10, 155, 170, 51);
		contentPane.add(btnVolver);

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(new ImageIcon(preguntaUsuario.class.getResource("/driver/fondoGeneral.png")));
		backgroundLabel.setBounds(0, 0, 600, 536);
		contentPane.add(backgroundLabel);
	}

}
