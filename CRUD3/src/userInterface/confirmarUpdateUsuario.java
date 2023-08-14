package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class confirmarUpdateUsuario extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JButton btnActualizar;

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
		setTitle("Confirmar passwod");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 25));
		lblPassword.setBounds(10, 24, 330, 31);
		contentPane.add(lblPassword);

		btnActualizar = new JButton("Confirmar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnActualizar.setForeground(new Color(30, 144, 255));
		btnActualizar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.setBounds(371, 56, 203, 45);
		contentPane.add(btnActualizar);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setForeground(new Color(30, 144, 255));
		passwordField.setFont(new Font("Arial", Font.PLAIN, 25));
		passwordField.setBounds(10, 56, 330, 45);
		contentPane.add(passwordField);

		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon(confirmarUpdateUsuario.class.getResource("/driver/fondoGeneral.png")));
		label.setBounds(0, 0, 584, 136);
		contentPane.add(label);
	}
}
