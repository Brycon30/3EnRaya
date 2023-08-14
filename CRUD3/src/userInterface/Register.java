package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import conexion.Conexion;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JFormattedTextField tfTelefono;
	private JPasswordField tfPassword;
	private JPasswordField tfConfirmPassword;

	public static String sql;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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

	public void registrarUsuario() {

		//se checa que el tfNombre tenga un nombre mayor a 3 caracteres
		if (tfNombre.getText().length()<4) {
			JOptionPane.showMessageDialog(null, "Debes escribir un nombre de usuario con mas de 3 caracteres");
			// se checa que el telefono sea de 10 digitos
		} else if (tfNombre.getText().length()>10) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario debe de contener menos de 10 caracteres");
		} else if (tfTelefono.getText().length() != 10) {
			JOptionPane.showMessageDialog(null, "Debes escribir un numero de telefono valido");
		//se checa que la password tenga algo escrito
		} else if (tfTelefono.getText().contains(" ")) {
			JOptionPane.showMessageDialog(null, "Numero de telefono no valido");
		//se comprueba que el telefono tenga 10 digitos
		}
		else if (tfPassword.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Debes escribir una contrase\u00f1a");
		//se checa que se haya confirmado la password
		} else if (tfConfirmPassword.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Debes confirmar la contrase\u00f1a");
		//se checa que las passwords sean iguales
		} else if (!tfConfirmPassword.getText().equals(tfPassword.getText())) {
			JOptionPane.showMessageDialog(null, "Las contrase\u00f1as no coinciden");
		//se valida que la password sea segura con reglas comunes
		}else if (!regexPassword(tfPassword.getText())) {
			JOptionPane.showMessageDialog(null, "La contraseña no es segura\r\n"
					+ "Necesita tener al menos 8 caracteres de longitud\r\n"
					+ "Contener al menos una letra minuscula [a-z]\r\n"
					+ "Contener al menos una letra mayuscula [A-Z]\r\n"
					+ "Contener al menos un numero\r\n"
					+ "Contener al menos un caracter especial [@#$%!]");

			// se llama a una funcion que checa si el nombre de usuario ya existe en la base
			// de datos
		} else if (checarUsuario(tfNombre.getText())) {
			JOptionPane.showMessageDialog(null, "Ese nombre de usuario ya esta en uso, elige otro");
		} else {

			PreparedStatement sentencia = null;
			//\u00f1 es la letra n con ceja
			// se prepara el codigo sql para ingresar datos
			sql = "insert into jugadores (nombre,contrase\u00f1a,telefono,partidasGanadas) values(?,?,?,?)";
			try {// preparedStatement es para ingresar datos
				sentencia = Conexion.getConnection().prepareStatement(sql);
				sentencia.setString(1, tfNombre.getText());
				sentencia.setString(2, tfPassword.getText());
				sentencia.setString(3, tfTelefono.getText());
				sentencia.setInt(4, 0);
				

				if (sentencia.executeUpdate() > 0) {
					// si se hace el insert muestra este mensaje
					JOptionPane.showMessageDialog(null, "Se ha registrado Exitosamente", "InformaciÃ³n",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (SQLException e) {
				System.err.print("Error de SQL " + e.getMessage());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "No se Registro, verifique la consola para ver el error", "Error",
						JOptionPane.ERROR_MESSAGE);
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

	public boolean checarUsuario(String usuario) {
		PreparedStatement sentencia = null;
		boolean b = false;
		
		//la sentencia sql
		String sql = "select nombre from jugadores where nombre = ?";
		try {
			//aqui se hace la coneccion y se reciven los datos que regresa el select
			sentencia = Conexion.getConnection().prepareStatement(sql);
			sentencia.setString(1, usuario);

			ResultSet resultado = sentencia.executeQuery();//se ejecuta

			//si hay un registro este se podra usar, en dado caso de que no , no se harea nada
			if (resultado.next()) {
				
				// Mover el cursor al primer registro
				String name = resultado.getString("nombre");

				if (usuario.equals(name)) {
					b = true;
				} else {
					b = false;
				}
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
		return b;

	}

	public Register() {
		setTitle("Registro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegistro = new JLabel("Registro");
		lblRegistro.setForeground(Color.WHITE);
		lblRegistro.setFont(new Font("Arial", Font.BOLD, 54));
		lblRegistro.setBounds(176, 14, 225, 63);
		contentPane.add(lblRegistro);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNombre.setBounds(190, 88, 196, 31);
		contentPane.add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setHorizontalAlignment(SwingConstants.CENTER);
		tfNombre.setFont(new Font("Arial", Font.PLAIN, 25));
		tfNombre.setForeground(new Color(30, 144, 255));
		tfNombre.setBounds(174, 121, 237, 45);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfNombre.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfNombre.getText().equals("Ingrese un nickname")) {
					tfNombre.setText("");
					tfNombre.setFont(new Font("Arial", Font.PLAIN, 25));
					tfNombre.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (tfNombre.getText().isEmpty()) {
					tfNombre.setFont(new Font("Arial", Font.PLAIN, 20));
					tfNombre.setForeground(Color.GRAY);
					tfNombre.setText("Ingrese un nickname");
				}
			}

		});

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setForeground(Color.WHITE);
		lblTelefono.setFont(new Font("Arial", Font.PLAIN, 25));
		lblTelefono.setBounds(190, 177, 196, 31);
		contentPane.add(lblTelefono);

		MaskFormatter telefono = null;
		try {
			telefono = new MaskFormatter("##########");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tfTelefono = new JFormattedTextField(telefono);
		tfTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		tfTelefono.setFont(new Font("Arial", Font.PLAIN, 25));
		tfTelefono.setForeground(new Color(30, 144, 255));
		tfTelefono.setBounds(174, 208, 237, 45);
		contentPane.add(tfTelefono);
		tfTelefono.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		lblPassword.setBounds(190, 264, 196, 31);
		contentPane.add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("Confirma password");
		lblConfirmPassword.setForeground(Color.WHITE);
		lblConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		lblConfirmPassword.setBounds(180, 351, 219, 31);
		contentPane.add(lblConfirmPassword);

		tfPassword = new JPasswordField();
		tfPassword.setForeground(new Color(30, 144, 255));
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		tfPassword.setBounds(174, 296, 237, 45);
		contentPane.add(tfPassword);

		tfConfirmPassword = new JPasswordField();
		tfConfirmPassword.setForeground(new Color(30, 144, 255));
		tfConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		tfConfirmPassword.setBounds(174, 386, 237, 45);
		contentPane.add(tfConfirmPassword);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBackground(Color.WHITE);
		btnRegistrar.setForeground(new Color(30, 144, 255));
		btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarUsuario();
			}
		});
		btnRegistrar.setBounds(424, 441, 150, 45);
		contentPane.add(btnRegistrar);

		JButton btnAtras = new JButton("Volver");
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setForeground(new Color(30, 144, 255));
		btnAtras.setFont(new Font("Arial", Font.PLAIN, 25));
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inicio i = new Inicio();
				i.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual

			}
		});
		btnAtras.setBounds(10, 441, 150, 45);
		contentPane.add(btnAtras);
		
		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 584, 497);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel, BorderLayout.CENTER);
	}
}
