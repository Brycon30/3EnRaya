package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

public class Perfil extends JFrame {

	private JPanel contentPane;
	private JLabel lblShowNombre;
	private JLabel lblShowPartidasGanadas;
	private JLabel lblShowJuegosTotales;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Perfil frame = new Perfil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void ponerDatosPerfil() {
		//metodo que ejecuta el proceso desde sql para obtener los datos
		//de los jugadores para ponerlos en la tabla leaderboard
		//esto se hace mediante un procedimiento almacenado
		PreparedStatement ps = null;
		PreparedStatement pg = null;
		ResultSet rs = null;
		String nombre = "select nombre,partidasGanadas from jugadores where nombre = ?";
		
		try {
			//se Prepara el sql
			ps = Conexion.getConnection().prepareStatement(nombre);
			ps.setString(1, conexion.Conexion.nombre);
			//Se ejecuta el sql
			rs = ps.executeQuery();
			
			while (rs.next()) {
		        // Obtener los datos de cada columna
		        lblShowNombre.setText(rs.getString("nombre"));
		        lblShowPartidasGanadas.setText(String.valueOf(rs.getInt("partidasGanadas")));

		        // Hacer algo con los datos obtenidos
		        //System.out.println("columna1: " + columna1 + ", columna2: " + columna2 );
		    }
			//en este caso como no se necesita meterle datos, no se los ponemos
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 */
	public Perfil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setForeground(new Color(30, 144, 255));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 25));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuInicio m = new MenuInicio();
				m.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la ventana actual
			
			}
		});
		btnVolver.setBounds(10, 435, 234, 51);
		contentPane.add(btnVolver);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(124, 120, 343, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblShowJuegosTotales = new JLabel("25");
		lblShowJuegosTotales.setBounds(0, 140, 210, 43);
		panel.add(lblShowJuegosTotales);
		lblShowJuegosTotales.setForeground(new Color(30, 144, 255));
		lblShowJuegosTotales.setFont(new Font("Arial", Font.BOLD, 50));
		
		JLabel lblPartidasTotales = new JLabel("Juegos totales");
		lblPartidasTotales.setBounds(0, 113, 210, 30);
		panel.add(lblPartidasTotales);
		lblPartidasTotales.setForeground(new Color(30, 144, 255));
		lblPartidasTotales.setFont(new Font("Arial", Font.BOLD, 25));
		
		lblShowPartidasGanadas = new JLabel("15");
		lblShowPartidasGanadas.setBounds(0, 73, 210, 43);
		panel.add(lblShowPartidasGanadas);
		lblShowPartidasGanadas.setForeground(new Color(30, 144, 255));
		lblShowPartidasGanadas.setFont(new Font("Arial", Font.BOLD, 50));
		
		JLabel lblPartidasGanadas = new JLabel("Partidas ganadas");
		lblPartidasGanadas.setBounds(0, 47, 210, 30);
		panel.add(lblPartidasGanadas);
		lblPartidasGanadas.setForeground(new Color(30, 144, 255));
		lblPartidasGanadas.setFont(new Font("Arial", Font.BOLD, 25));
		
		lblShowNombre = new JLabel("baca777");
		lblShowNombre.setBounds(0, 0, 210, 52);
		panel.add(lblShowNombre);
		lblShowNombre.setFont(new Font("Arial", Font.BOLD, 50));
		lblShowNombre.setForeground(new Color(30, 144, 255));
		
		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setFont(new Font("Arial", Font.BOLD, 70));
		lblPerfil.setBounds(149, 11, 287, 81);
		contentPane.add(lblPerfil);
		
		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 584, 497);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel, BorderLayout.CENTER);
        
        ponerDatosPerfil();
		
	}
}
