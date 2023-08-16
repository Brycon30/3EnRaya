package userInterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class OpcionesRecuperacion extends JFrame {

	private JPanel contentPane;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);
	private JButton btnVolver;
	private JButton btnIngresar;
	public static String pregunta = "";
	public static String respuesta = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpcionesRecuperacion frame = new OpcionesRecuperacion();
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
	public OpcionesRecuperacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		btnVolver = new JButton(messages.getString("Back"));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Perfil p = new Perfil();
				p.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
			}
		});
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBounds(10, 435, 223, 51);
		contentPane.add(btnVolver);

		JLabel lblPerfil = new JLabel(messages.getString("Recover"));
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setFont(new Font("Arial", Font.BOLD, 70));
		lblPerfil.setBounds(0, 11, 584, 81);
		contentPane.add(lblPerfil);

		JLabel lblPregunta = new JLabel(messages.getString("recoveryQuestion"));
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta.setBounds(10, 123, 564, 30);
		contentPane.add(lblPregunta);
		lblPregunta.setForeground(Color.WHITE);
		lblPregunta.setFont(new Font("Arial", Font.BOLD, 25));

		JLabel lblRespuesta = new JLabel(messages.getString("Answer"));
		lblRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRespuesta.setBounds(10, 226, 564, 30);
		contentPane.add(lblRespuesta);
		lblRespuesta.setForeground(Color.WHITE);
		lblRespuesta.setFont(new Font("Arial", Font.BOLD, 25));

		JTextField tfPregunta = new JTextField(messages.getString("Enter") + " " + messages.getString("Question"));
		tfPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		tfPregunta.setForeground(Color.GRAY);
		tfPregunta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfPregunta.setColumns(10);
		tfPregunta.setBounds(137, 164, 311, 51);
		contentPane.add(tfPregunta);

		tfPregunta.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfPregunta.getText().equals(messages.getString("Enter") + " " + messages.getString("Question"))) {
					tfPregunta.setText("");
					tfPregunta.setFont(new Font("Arial", Font.PLAIN, 25));
					tfPregunta.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (tfPregunta.getText().isEmpty()) {
					tfPregunta.setFont(new Font("Arial", Font.PLAIN, 20));
					tfPregunta.setForeground(Color.GRAY);
					tfPregunta.setText(messages.getString("Enter") + " " + messages.getString("Question"));
				}
			}

		});

		JTextField tfRespuesta = new JTextField();
		tfRespuesta.setText(messages.getString("Enter") + " " + messages.getString("Answer"));
		tfRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		tfRespuesta.setForeground(Color.GRAY);
		tfRespuesta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfRespuesta.setColumns(10);
		tfRespuesta.setBounds(137, 267, 311, 51);
		contentPane.add(tfRespuesta);

		tfRespuesta.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Limpiar el texto de ejemplo cuando se obtiene el foco
				if (tfRespuesta.getText().equals(messages.getString("Enter") + " " + messages.getString("Answer"))) {
					tfRespuesta.setText("");
					tfRespuesta.setFont(new Font("Arial", Font.PLAIN, 25));
					tfRespuesta.setForeground(new Color(30, 144, 255));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Restaurar el texto de ejemplo si no se ingresó ningún texto
				if (tfRespuesta.getText().isEmpty()) {
					tfRespuesta.setFont(new Font("Arial", Font.PLAIN, 20));
					tfRespuesta.setForeground(Color.GRAY);
					tfRespuesta.setText(messages.getString("Enter") + " " + messages.getString("Answer"));
				}
			}

		});

		btnIngresar = new JButton(messages.getString("Enter"));
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!(tfPregunta.getText().equals(messages.getString("Enter") + " " + messages.getString("Question")) && (!(tfRespuesta.getText().equals(messages.getString("Enter") + " " + messages.getString("Answer")))))) {
					pregunta = tfPregunta.getText();
					respuesta = tfRespuesta.getText();
					conexion.SQLEasy.insertJugador(pregunta, respuesta);
				} else {
					JOptionPane.showMessageDialog(null, messages.getString("invalidQuestion"));
				}
			}

		});
		btnIngresar.setForeground(new Color(30, 144, 255));
		btnIngresar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnIngresar.setBackground(Color.WHITE);
		btnIngresar.setBounds(351, 435, 223, 51);
		contentPane.add(btnIngresar);

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(0, 0, 584, 497);
		ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
		Image backgroundImg = backgroundIcon.getImage();
		Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(),
				Image.SCALE_SMOOTH);
		contentPane.setLayout(null);
		backgroundLabel.setIcon(new ImageIcon(scaledImg));

		contentPane.add(backgroundLabel);

		btnIngresar.addActionListener(null);

	}
}
