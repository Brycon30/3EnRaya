package userInterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class OpcionesRecuperacion extends JFrame {

	private JPanel contentPane;

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
		
		JLabel lblPerfil = new JLabel("Recuperacion");
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setFont(new Font("Arial", Font.BOLD, 70));
		lblPerfil.setBounds(0, 11, 584, 81);
		contentPane.add(lblPerfil);
		
		JLabel lblPregunta = new JLabel("Pregunta de recuperacion");
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta.setBounds(10, 99, 584, 30);
		contentPane.add(lblPregunta);
		lblPregunta.setForeground(Color.WHITE);
		lblPregunta.setFont(new Font("Arial", Font.BOLD, 25));
		
		JLabel lblRespuesta = new JLabel("Respuesta");
		lblRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRespuesta.setBounds(187, 243, 210, 30);
		contentPane.add(lblRespuesta);
		lblRespuesta.setForeground(Color.WHITE);
		lblRespuesta.setFont(new Font("Arial", Font.BOLD, 25));
		
		JTextField tfPregunta = new JTextField();
		tfPregunta.setText("Ingrese pregunta");
		tfPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		tfPregunta.setForeground(Color.GRAY);
		tfPregunta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfPregunta.setColumns(10);
		tfPregunta.setBounds(137, 164, 311, 51);
		contentPane.add(tfPregunta);
		
		JTextField tfRespuesta = new JTextField();
		tfRespuesta.setText("Ingrese respuesta");
		tfRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		tfRespuesta.setForeground(Color.GRAY);
		tfRespuesta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfRespuesta.setColumns(10);
		tfRespuesta.setBounds(137, 317, 311, 51);
		contentPane.add(tfRespuesta);
		
		
		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 11, 584, 497);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        contentPane.setLayout(null);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel);
	}

}
