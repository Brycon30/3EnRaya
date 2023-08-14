package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombreUsuario;
	private JPasswordField tfPassword;
	private JButton btnVolver;
	private JButton btnLogin;
	private ManejadorBoton manejadorB = new ManejadorBoton();
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class ManejadorBoton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// aqui se controlaran los clicks de botones ouyeah
			if (e.getSource().equals(btnVolver)) {
				Inicio i = new Inicio();// crea la nueva ventana
				i.setVisible(true);// la hacemos visible
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual

			}
			if (e.getSource().equals(btnLogin)) {

				// se verifica que el usuario y password sean los de algun usuario registrado en
				// la base de datos
				if (iniciarSecion(tfNombreUsuario.getText())) {
					Conexion.nombre = tfNombreUsuario.getText();

					// aqui devemos poner un metodo que guarde el id en conexion.id
					getId(tfNombreUsuario.getText());

					MenuInicio m = new MenuInicio();
					m.setVisible(true);
					((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual
				} else {
					JOptionPane.showMessageDialog(null, "usuario o contraseña incorrectos");
				}
			}

		}
	}

	private void getId(String nombreJugador) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select idJugador from jugadores where nombre = ?";

		try {
			// se Prepara el sql
			ps = Conexion.getConnection().prepareStatement(sql);
			ps.setString(1, nombreJugador);
			// Se ejecuta el sql
			rs = ps.executeQuery();

			while (rs.next()) {
				// Obtener los datos de cada columna
				conexion.Conexion.idJugador = rs.getInt("idJugador");

				// Hacer algo con los datos obtenidos
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(conexion.Conexion.idJugador);
	}

	private boolean iniciarSecion(String usuario) {
		PreparedStatement sentencia = null;
		boolean b = false;

		// la sentencia sql
		String sql = "select contrase\u00f1a from jugadores where nombre = ?";
		try {
			// aqui se hace la conexion y se reciven los datos que regresa el select
			sentencia = Conexion.getConnection().prepareStatement(sql);
			sentencia.setString(1, usuario);

			ResultSet resultado = sentencia.executeQuery();// se ejecuta

			// si hay un registro este se podra usar, en dado caso de que no , no se harea
			// nada
			if (resultado.next()) {

				// Mover el cursor al primer registro
				String pass = resultado.getString("contrase\u00f1a");

				if (pass.equals(tfPassword.getText())) {
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
	
	public void setupEnterKeyForLogin() {
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	        @Override
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
	                if (iniciarSecion(tfNombreUsuario.getText())) {
	                    Conexion.nombre = tfNombreUsuario.getText();
	                    getId(tfNombreUsuario.getText());

	                    MenuInicio m = new MenuInicio();
	                    m.setVisible(true);
	                    ((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
	                } else {
	                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
	                }
	            }
	            return false; // Permite que otros componentes también reciban el evento
	        }
	    });
	}


	public void ponerEscuchador() {
		// se ponen escuchadores en los botones
		btnVolver.addActionListener(manejadorB);
		btnLogin.addActionListener(manejadorB);
	}

	public Login() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(164, 5, 287, 81);
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 54));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLogin);

		label = new JLabel("");
		label.setBounds(292, 5, 287, 81);
		contentPane.add(label);

		JLabel lblNombreUsuario = new JLabel("Nombre de usuario");
		lblNombreUsuario.setForeground(Color.WHITE);
		lblNombreUsuario.setBounds(164, 97, 287, 41);
		lblNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNombreUsuario);

		tfNombreUsuario = new JTextField();
		tfNombreUsuario.setText("Ingrese un nickname");
		tfNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		tfNombreUsuario.setForeground(Color.GRAY);
		tfNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		tfNombreUsuario.setBounds(164, 138, 287, 51);
		contentPane.add(tfNombreUsuario);
		tfNombreUsuario.setColumns(10);

		tfNombreUsuario.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfNombreUsuario.getText().equals("Ingrese un nickname")) {
					tfNombreUsuario.setText("");
					tfNombreUsuario.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (tfNombreUsuario.getText().isEmpty()) {
					tfNombreUsuario.setForeground(Color.GRAY);
					tfNombreUsuario.setText("Ingrese un nickname");
				}
			}

		});

		JLabel lblPassword = new JLabel("contrase\u00f1a");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(164, 200, 287, 41);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPassword);

		tfPassword = new JPasswordField();
		tfPassword.setText("Ingrese una contrase\u00f1a");
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		tfPassword.setForeground(Color.GRAY);
		tfPassword.setBounds(164, 241, 287, 51);
		contentPane.add(tfPassword);
		tfPassword.setColumns(10);

		tfPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfPassword.getText().equals("Ingrese una contrase\u00f1a")) {
					tfPassword.setText("");
					tfPassword.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (tfPassword.getText().isEmpty()) {
					tfPassword.setForeground(Color.GRAY);
					tfPassword.setText("Ingrese una contrase\u00f1a");
				}
			}

		});

		btnVolver = new JButton("Volver");
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.setBounds(7, 436, 234, 51);
		contentPane.add(btnVolver);

		btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(30, 144, 255));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 25));
		btnLogin.setBounds(343, 437, 234, 51);
		contentPane.add(btnLogin);

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 584, 497);
		ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
		Image backgroundImg = backgroundIcon.getImage();
		Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(),
				Image.SCALE_SMOOTH);
		backgroundLabel.setIcon(new ImageIcon(scaledImg));

		contentPane.add(backgroundLabel, BorderLayout.CENTER);

		contentPane.add(backgroundLabel, BorderLayout.CENTER);

		//pone escuchadores en los botones
		ponerEscuchador();
		
		
		//Pone el escuchador del teclado para comprobar que se pulso el intro
		setupEnterKeyForLogin();
		
	}
}
