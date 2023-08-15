package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;

public class PerfilOpciones extends JFrame {

	private JPanel contentPane;
	private JTextField tfEditarNombreUsuario;
	private JPasswordField pfNuevaPassword;
	private ManejadorActualizar manejadorAct = new ManejadorActualizar();
	private JButton btnVolver;
	private JButton btnActualizar;
	private JLabel lblEditarPerfil;
	private JFormattedTextField tfTelefonoUpdate;
	public static String nuevaPassword = "";
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PerfilOpciones frame = new PerfilOpciones();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class ManejadorActualizar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// SI SE PRESIONA EL BOTON VOLVER LA VENTANA SE CIERRA
			if (e.getSource().equals(btnVolver)) {
				Perfil p = new Perfil();
				p.setVisible(true);
				if (p.isVisible()) {
					((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
				}
			}
			// SI PRESIONA EL BOTON ACTUALIZAR SE HARA LO SIGUIENTE
			if (e.getSource().equals(btnActualizar)) {

				if (!(tfEditarNombreUsuario.getText().equals(messages.getString("Enter") + " " + messages.getString("Name")))) {
					if (tfEditarNombreUsuario.getText().length() > 10) {
						JOptionPane.showMessageDialog(null, messages.getString("invalidName2"));
					} else if (tfEditarNombreUsuario.getText().length() < 4) {
						JOptionPane.showMessageDialog(null, messages.getString("invalidName1"));
					}
					if (!(pfNuevaPassword.getText().equals(messages.getString("password")))) {
						if (!(regexPassword(pfNuevaPassword.getText()))) {
							JOptionPane.showMessageDialog(null, messages.getString("regexPassword"));
						}
					}
					if (tfTelefonoUpdate.getText().contains(" ")) {
						JOptionPane.showMessageDialog(null, messages.getString("invalidPhone"));
					} else {
						confirmarUpdateUsuario cup = new confirmarUpdateUsuario();
						cup.setVisible(true);
						if (cup.isVisible()) {
							nuevaPassword = pfNuevaPassword.getText();
							((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, messages.getString("invalidName"));
				}
			}
		}
	}

	public boolean regexPassword(String password) {
		// Definir la expresión regular para la validación de la contraseña
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!(),.*&?])[A-Za-z\\d@#$%!(),.*&?]{8,}$";

		// Compilar la expresión regular en un objeto Pattern
		Pattern pattern = Pattern.compile(regex);

		// Crear un objeto Matcher con la contraseña ingresada
		Matcher matcher = pattern.matcher(password);

		// Verificar si la contraseña cumple con el patrón definido
		return matcher.matches();
	}

	/**
	 * Create the frame.
	 */

	private String obtenerPassword(String passValor) {
		PreparedStatement sentencia = null;
		String password = null;

		// la sentencia sql
		String sql = "select contrase\u00f1a from jugadores where nombre = ?";
		try {
			// aqui se hace la coneccion y se reciven los datos que regresa el select
			sentencia = Conexion.getConnection().prepareStatement(sql);
			sentencia.setString(1, conexion.Conexion.nombre);

			ResultSet resultado = sentencia.executeQuery();// se ejecuta

			// si hay un registro este se podra usar, en dado caso de que no , no se harea
			// nada
			if (resultado.next()) {

				// Mover el cursor al primer registro
				password = resultado.getString("contrase\u00f1a");

			}
			resultado.close();
			sentencia.close();

		} catch (SQLException e) {
			System.err.print("Error de SQL: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (sentencia != null) {
					sentencia.close();
				}
			} catch (SQLException e) {
				System.err.print("Error al cerrar la sentencia: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return password;

	}

	public PerfilOpciones() {
		setTitle(messages.getString("profileMod"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTelefono = new JLabel(messages.getString("phoneNumber"));
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setForeground(Color.WHITE);
		lblTelefono.setFont(new Font("Arial", Font.PLAIN, 25));
		lblTelefono.setBounds(79, 284, 434, 31);
		contentPane.add(lblTelefono);

		MaskFormatter telefono = null;
		try {
			telefono = new MaskFormatter("##########");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tfTelefonoUpdate = new JFormattedTextField(telefono);
		tfTelefonoUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		tfTelefonoUpdate.setForeground(new Color(30, 144, 255));
		tfTelefonoUpdate.setFont(new Font("Arial", Font.PLAIN, 25));
		tfTelefonoUpdate.setColumns(10);
		tfTelefonoUpdate.setBounds(140, 315, 311, 45);
		contentPane.add(tfTelefonoUpdate);

		lblEditarPerfil = new JLabel(messages.getString("Edit") + " " + messages.getString("Profile"));
		lblEditarPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarPerfil.setForeground(Color.WHITE);
		lblEditarPerfil.setFont(new Font("Arial", Font.BOLD, 54));
		lblEditarPerfil.setBounds(10, 25, 564, 63);
		contentPane.add(lblEditarPerfil);

		tfEditarNombreUsuario = new JTextField();
		tfEditarNombreUsuario.setText(messages.getString("Enter") + " " + messages.getString("Name"));
		tfEditarNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		tfEditarNombreUsuario.setForeground(Color.GRAY);
		tfEditarNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		tfEditarNombreUsuario.setColumns(10);
		tfEditarNombreUsuario.setBounds(140, 141, 311, 51);
		contentPane.add(tfEditarNombreUsuario);

		tfEditarNombreUsuario.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfEditarNombreUsuario.getText().equals(messages.getString("Enter") + " " + messages.getString("Name"))) {
					tfEditarNombreUsuario.setText("");
					tfEditarNombreUsuario.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingreso ningún dato
				if (tfEditarNombreUsuario.getText().isEmpty()) {
					tfEditarNombreUsuario.setForeground(Color.GRAY);
					tfEditarNombreUsuario.setText(messages.getString("Enter") + " " + messages.getString("Name"));
				}
			}
		});

		JLabel lblNombreUsuario = new JLabel(messages.getString("Edit") + " " + messages.getString("userName"));
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreUsuario.setForeground(Color.WHITE);
		lblNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNombreUsuario.setBounds(79, 99, 434, 41);
		contentPane.add(lblNombreUsuario);

		pfNuevaPassword = new JPasswordField();
		pfNuevaPassword.setText(messages.getString("password"));
		pfNuevaPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pfNuevaPassword.setForeground(Color.GRAY);
		pfNuevaPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		pfNuevaPassword.setColumns(10);
		pfNuevaPassword.setBounds(140, 233, 311, 51);
		contentPane.add(pfNuevaPassword);

		pfNuevaPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (pfNuevaPassword.getText().equals(messages.getString("password"))) {
					pfNuevaPassword.setText("");
					pfNuevaPassword.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (pfNuevaPassword.getText().isEmpty()) {
					pfNuevaPassword.setForeground(Color.GRAY);
					pfNuevaPassword.setText(messages.getString("password"));
				}
			}

		});

		JLabel lblEditarContrasea = new JLabel(messages.getString("Enter") + " " + messages.getString("New") + " " + messages.getString("password"));
		lblEditarContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarContrasea.setForeground(Color.WHITE);
		lblEditarContrasea.setFont(new Font("Arial", Font.PLAIN, 25));
		lblEditarContrasea.setBounds(79, 192, 434, 41);
		contentPane.add(lblEditarContrasea);

		btnVolver = new JButton(messages.getString("Back"));
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBounds(10, 435, 203, 51);
		contentPane.add(btnVolver);

		btnActualizar = new JButton(messages.getString("Update"));
		btnActualizar.setForeground(new Color(30, 144, 255));
		btnActualizar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.setBounds(371, 435, 203, 51);
		contentPane.add(btnActualizar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PerfilOpciones.class.getResource("/driver/fondoGeneral.png")));
		label.setBounds(0, 0, 584, 497);
		contentPane.add(label);

		btnVolver.addActionListener(manejadorAct);
		btnActualizar.addActionListener(manejadorAct);

	}
}
