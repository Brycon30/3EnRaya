package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Inicio extends JFrame {

	//PRUEBA DE COMMIT Y PUSH DESDE EL GATO DE LARA PARA EL MUNDO
	//INTENTANDO CONSEGUIR COMMIT DESDE GIT A JAVA
	private JPanel contentPane;
	private JButton btnRegistro;
	private JButton btnLogin;
	
	private ManejadorBoton manejadorB= new ManejadorBoton();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		
			public void run() {
				try {
					
					Inicio frame = new Inicio();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Aqui se declara una clase para poder saber que boton ha sido pulsado
	class ManejadorBoton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// aqui se controlaran los clicks de botones ouyeah
			if (e.getSource().equals(btnRegistro)) {
				Register r= new Register();//crea la nueva ventana
				r.setVisible(true);//la hacemos visible
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();//cerramos la pantalla actual
				
			}if (e.getSource().equals(btnLogin)) {
				Login l= new Login();//crea la nueva ventana
				l.setVisible(true);//la hacemos visible
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();//cerramos la pantalla actual
			
			}
			
			
		}
	}
	
	public void ponerEscuchador() {
		//se ponen los escuchadores en cada boton
		
		btnRegistro.addActionListener(manejadorB);
		btnLogin.addActionListener(manejadorB);
	}

	/**
	 * Create the frame.
	 */
	public Inicio() {
		setResizable(false);
		this.setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTresEnRaya = new JLabel("Tres en raya");
		lblTresEnRaya.setFont(new Font("Arial", Font.BOLD, 54));
		lblTresEnRaya.setForeground(Color.WHITE);
		lblTresEnRaya.setHorizontalAlignment(SwingConstants.CENTER);
		lblTresEnRaya.setBounds(115, 11, 352, 86);
		contentPane.add(lblTresEnRaya);
		
		btnRegistro = new JButton("Registro");
		btnRegistro.setBackground(Color.WHITE);
		btnRegistro.setForeground(new Color(30, 144, 255));
		btnRegistro.setFont(new Font("Arial", Font.PLAIN, 25));
		btnRegistro.setBounds(10, 429, 223, 57);
		contentPane.add(btnRegistro);
		
		btnLogin = new JButton("Login");
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setForeground(new Color(30, 144, 255));
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 25));
		btnLogin.setBounds(351, 429, 223, 57);
		contentPane.add(btnLogin);
		
		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 584, 497);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoInicio.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel, BorderLayout.CENTER);
		
		ponerEscuchador();
	}
}
