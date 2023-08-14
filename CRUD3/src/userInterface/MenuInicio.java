package userInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;
import juego.ConexionJuego;
import juego.lilCat;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class MenuInicio extends JFrame {

	private JPanel contentPane;
	private ManejadorBoton manejadorB = new ManejadorBoton();
	private JButton btnJugar;
	private JButton btnPerfil;
	private JButton btnLeaderboard;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuInicio frame = new MenuInicio();
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

	class ManejadorBoton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// aqui se controlaran los clicks de botones ouyeah

			
			//abre la ventana de juego ademas de iniciar la conexion al servidor del juego
			if (e.getSource().equals(btnJugar)) {
				ConexionJuego.conectarAlServidor(Conexion.nombre);
				lilCat l = new lilCat();
				l.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la ventana actual
			}
			
			//abre la ventana de perfil
			if (e.getSource().equals(btnPerfil)) {
				Perfil p = new Perfil();// creamos la nueva ventana
				p.setVisible(true);// hacemos visible la nueva ventana
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la ventana actual
			}
			
			//abre la ventana de leaderboard
			if (e.getSource().equals(btnLeaderboard)) {
				Leaderboard l = new Leaderboard();// crea la nueva ventana
				l.setVisible(true);// la hacemos visible
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual
			}
			
			//te lleva al inicio de la aplicacion
			if (e.getSource().equals(btnSalir)) {
				Inicio i = new Inicio();// crea la nueva ventana
				i.setVisible(true);// la hacemos visible
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual
			}

		}
	}

	//ponen los escuchadores
	public void ponerEscuchadores() {
		btnJugar.addActionListener(manejadorB);
		btnPerfil.addActionListener(manejadorB);
		btnLeaderboard.addActionListener(manejadorB);
		btnSalir.addActionListener(manejadorB);
	}

	public MenuInicio() {
		setTitle(conexion.Conexion.nombre + " - Menu de inicio");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnJugar = new JButton("Jugar");
		btnJugar.setBackground(Color.WHITE);
		btnJugar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnJugar.setForeground(new Color(30, 144, 255));
		btnJugar.setBounds(10, 216, 223, 51);
		contentPane.add(btnJugar);

		btnPerfil = new JButton("Perfil");
		btnPerfil.setBackground(Color.WHITE);
		btnPerfil.setFont(new Font("Arial", Font.PLAIN, 25));
		btnPerfil.setForeground(new Color(30, 144, 255));
		btnPerfil.setBounds(10, 278, 223, 51);
		contentPane.add(btnPerfil);

		btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.setBackground(Color.WHITE);
		btnLeaderboard.setFont(new Font("Arial", Font.PLAIN, 25));
		btnLeaderboard.setForeground(new Color(30, 144, 255));
		btnLeaderboard.setBounds(10, 340, 223, 51);
		contentPane.add(btnLeaderboard);

		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 25));
		btnSalir.setForeground(new Color(30, 144, 255));
		btnSalir.setBounds(10, 402, 223, 51);
		contentPane.add(btnSalir);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 584, 497);
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(MenuInicio.class.getResource("/driver/menuInicio.png")));
        
                contentPane.add(backgroundLabel, BorderLayout.CENTER);
                
                JLabel lblGif = new JLabel("");
                lblGif.setIcon(new ImageIcon(MenuInicio.class.getResource("/driver/X2.gif")));
                lblGif.setBounds(249, 223, 330, 220);
                contentPane.add(lblGif);

		ponerEscuchadores();
	}
}
