package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class PerfilOpciones extends JFrame {

	private JPanel contentPane;
	private JTextField tfEditarNombreUsuario;
	private JPasswordField pfNuevaPassword;
	private JPasswordField pfConfirmarPassword;
	private ManejadorActualizar manejadorAct = new ManejadorActualizar();
	private JButton btnVolver;
	private JButton btnActualizar;

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
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
			}
			// SI PRESIONA EL BOTON ACTUALIZAR SE HARA LO SIGUIENTE
			if (e.getSource().equals(btnActualizar)) {
				if (!(tfEditarNombreUsuario.getText().equals("Ingrese nuevo nickname"))) {
					// SI LAS CONTRASEÑAS COINCIDEN SE DEBE ACTUALIZAR EL NOMBRE Y LA CONTRASEÑA DEL
					// USUARIO EN SQL
					if (pfNuevaPassword.getText().equals(pfConfirmarPassword.getText())) {
						// SI LA CONTRASEÑA NO SE CAMBIO ENTONCES AQUI DEBERIAS HACER QUE LA CONTRASEÑA
						// DEL USUARIO NO SE ACTUALICE EN SQL
						if (pfNuevaPassword.getText().equals("password")) {
						}
						// SI LAS CONTRASEÑAS NO COINCIDEN SE MUESTRA EL MENSAJE DE QUE NO COINCIDEN
					} else {
						JOptionPane.showMessageDialog(null, "Las contrase\u00f1as no coinciden");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se ingreso un nombre de usuario valido");
				}
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public PerfilOpciones() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfEditarNombreUsuario = new JTextField();
		tfEditarNombreUsuario.setText("Ingrese nuevo nickname");
		tfEditarNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		tfEditarNombreUsuario.setForeground(Color.GRAY);
		tfEditarNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		tfEditarNombreUsuario.setColumns(10);
		tfEditarNombreUsuario.setBounds(61, 53, 311, 51);
		contentPane.add(tfEditarNombreUsuario);

		tfEditarNombreUsuario.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfEditarNombreUsuario.getText().equals("Ingrese nuevo nickname")) {
					tfEditarNombreUsuario.setText("");
					tfEditarNombreUsuario.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingreso ningún dato
				if (tfEditarNombreUsuario.getText().isEmpty()) {
					tfEditarNombreUsuario.setForeground(Color.GRAY);
					tfEditarNombreUsuario.setText("Ingrese nuevo nickname");
				}
			}
		});

		JLabel lblNombreUsuario = new JLabel("Editar nombre de usuario");
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreUsuario.setForeground(Color.WHITE);
		lblNombreUsuario.setFont(new Font("Arial", Font.PLAIN, 25));
		lblNombreUsuario.setBounds(0, 11, 434, 41);
		contentPane.add(lblNombreUsuario);

		pfNuevaPassword = new JPasswordField();
		pfNuevaPassword.setText("password");
		pfNuevaPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pfNuevaPassword.setForeground(Color.GRAY);
		pfNuevaPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		pfNuevaPassword.setColumns(10);
		pfNuevaPassword.setBounds(61, 145, 311, 51);
		contentPane.add(pfNuevaPassword);

		pfNuevaPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (pfNuevaPassword.getText().equals("password")) {
					pfNuevaPassword.setText("");
					pfNuevaPassword.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (pfNuevaPassword.getText().isEmpty()) {
					pfNuevaPassword.setForeground(Color.GRAY);
					pfNuevaPassword.setText("password");
				}
			}

		});

		JLabel lblEditarContrasea = new JLabel("Editar password");
		lblEditarContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarContrasea.setForeground(Color.WHITE);
		lblEditarContrasea.setFont(new Font("Arial", Font.PLAIN, 25));
		lblEditarContrasea.setBounds(0, 104, 434, 41);
		contentPane.add(lblEditarContrasea);

		pfConfirmarPassword = new JPasswordField();
		pfConfirmarPassword.setText("password");
		pfConfirmarPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pfConfirmarPassword.setForeground(Color.GRAY);
		pfConfirmarPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		pfConfirmarPassword.setColumns(10);
		pfConfirmarPassword.setBounds(61, 238, 311, 51);
		contentPane.add(pfConfirmarPassword);

		pfConfirmarPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (pfConfirmarPassword.getText().equals("password")) {
					pfConfirmarPassword.setText("");
					pfConfirmarPassword.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (pfConfirmarPassword.getText().isEmpty()) {
					pfConfirmarPassword.setForeground(Color.GRAY);
					pfConfirmarPassword.setText("password");
				}
			}

		});

		JLabel lblEditarPassword = new JLabel("Confirmar nueva password");
		lblEditarPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarPassword.setForeground(Color.WHITE);
		lblEditarPassword.setFont(new Font("Arial", Font.PLAIN, 25));
		lblEditarPassword.setBounds(0, 197, 434, 41);
		contentPane.add(lblEditarPassword);

		btnVolver = new JButton("Volver");
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBounds(10, 319, 170, 51);
		contentPane.add(btnVolver);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setForeground(new Color(30, 144, 255));
		btnActualizar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.setBounds(254, 319, 170, 51);
		contentPane.add(btnActualizar);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PerfilOpciones.class.getResource("/driver/fondoGeneral.png")));
		label.setBounds(0, 0, 434, 379);
		contentPane.add(label);

		btnVolver.addActionListener(manejadorAct);
		btnActualizar.addActionListener(manejadorAct);
	}

}
