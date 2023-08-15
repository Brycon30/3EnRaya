package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class confirmarUpdateUsuario extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JButton btnActualizar;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					confirmarUpdateUsuario frame = new confirmarUpdateUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public confirmarUpdateUsuario() {
		setTitle(messages.getString("Confirm") + " " + messages.getString("password"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPassword = new JLabel(messages.getString("password"));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 25));
		lblPassword.setBounds(10, 24, 330, 31);
		contentPane.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setForeground(new Color(30, 144, 255));
		passwordField.setFont(new Font("Arial", Font.PLAIN, 25));
		passwordField.setBounds(10, 56, 330, 45);
		contentPane.add(passwordField);
		
		btnActualizar = new JButton(messages.getString("Confirm"));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("boton pulsado");
				PreparedStatement sentencia = null;
				String sql = "select contrase\u00f1a from jugadores where idJugador = ?";
				boolean b = false;
				try {
					// aqui se hace la conexion y se reciven los datos que regresa el select
					sentencia = Conexion.getConnection().prepareStatement(sql);
					sentencia.setInt(1, conexion.Conexion.idJugador);

					ResultSet resultado = sentencia.executeQuery();// se ejecuta

					// si hay un registro este se podra usar, en dado caso de que no , no se harea
					// nada
					if (resultado.next()) {

						// Mover el cursor al primer registro
						String pass = resultado.getString("contrase\u00f1a");

						if (pass.equals(passwordField.getText())) {
							System.out.println("Contraseña correcta");
							b = true;
						} else {
							b = false;
							System.out.println("Contraseña incorrecta");
						}
					}
					resultado.close();
					sentencia.close();

				} catch (SQLException e3) {
					System.err.print("Error de SQL: " + e3.getMessage());
					e3.printStackTrace();
				}
				
				if (b) {
					PreparedStatement sentencia2 = null;
					
					String sql2 = "update jugadores set contrase\u00f1a = ? where nombre = ?";
					try {
						sentencia = Conexion.getConnection().prepareStatement(sql2);
						sentencia.setString(1, PerfilOpciones.nuevaPassword);
						sentencia.setString(2, conexion.Conexion.nombre);
						
						sentencia.executeUpdate();
						if (sentencia.executeUpdate() > 0) {
							// si se hace el insert muestra este mensaje
							JOptionPane.showMessageDialog(null, messages.getString("succesfulRegister"));
						}
						sentencia.close();
					} catch (Exception e2) {
						System.err.print("Error de SQL: " + e2.getMessage());
						e2.printStackTrace();
					}
				}
			}
		});
		btnActualizar.setForeground(new Color(30, 144, 255));
		btnActualizar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.setBounds(371, 56, 203, 45);
		contentPane.add(btnActualizar);


		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon(confirmarUpdateUsuario.class.getResource("/driver/fondoGeneral.png")));
		label.setBounds(0, 0, 584, 136);
		contentPane.add(label);
	}
}
