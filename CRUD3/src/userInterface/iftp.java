package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.SQLEasy;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class iftp extends JFrame {

	private JPanel contentPane;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);
	private JTextField tfUsername;
	public static String pregunta = "";
	public static String respuesta = "";
	public static String username = "";
	public static int idDesdeIftp = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					iftp frame = new iftp();
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
	public iftp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfUsername = new JTextField();
		tfUsername.setHorizontalAlignment(SwingConstants.CENTER);
		tfUsername.setForeground(new Color(30, 144, 255));
		tfUsername.setFont(new Font("Arial", Font.PLAIN, 25));
		tfUsername.setColumns(10);
		tfUsername.setBounds(137, 155, 311, 51);
		contentPane.add(tfUsername);
		
		JLabel lblPregunta_1 = new JLabel(messages.getString("Enter")+" "+messages.getString("userName"));
		lblPregunta_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta_1.setForeground(Color.WHITE);
		lblPregunta_1.setFont(new Font("Arial", Font.BOLD, 25));
		lblPregunta_1.setBounds(10, 114, 564, 30);
		contentPane.add(lblPregunta_1);
		
		JLabel lblPerfil = new JLabel(messages.getString("Recover"));
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setFont(new Font("Arial", Font.BOLD, 70));
		lblPerfil.setBounds(0, 11, 584, 81);
		contentPane.add(lblPerfil);
		
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
		btnVolver.setBounds(10, 243, 223, 51);
		contentPane.add(btnVolver);
		
		JButton btnIngresar = new JButton(messages.getString("rp"));
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = tfUsername.getText();
				conexion.SQLEasy.conseguirID(username);
				idDesdeIftp = conexion.SQLEasy.id;
				if (idDesdeIftp == 0) {
					JOptionPane.showMessageDialog(null, messages.getString("invalidName"));
				} else {
					preguntaUsuario pu = new preguntaUsuario();
					pu.setVisible(true);
					((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
				}

			}
		});
		btnIngresar.setForeground(new Color(30, 144, 255));
		btnIngresar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnIngresar.setBackground(Color.WHITE);
		btnIngresar.setBounds(351, 243, 223, 51);
		contentPane.add(btnIngresar);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(new ImageIcon(iftp.class.getResource("/driver/fondoGeneral.png")));
		backgroundLabel.setBounds(0, 0, 584, 497);
		contentPane.add(backgroundLabel);
	}
}
