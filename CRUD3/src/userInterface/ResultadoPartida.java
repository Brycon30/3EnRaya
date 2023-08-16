package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class ResultadoPartida extends JFrame {

	private JPanel contentPane;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResultadoPartida frame = new ResultadoPartida("Resultado");
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
	
	//se modifico el constructor para que sea mas versatil esta pantalla
	// mediante el string del constructor se pone un mensaje en esta pantalla
	//asi se puede hacer la logica desde el juego mucho mas sencillo
	public ResultadoPartida(String resultado) {
		setTitle(messages.getString("Result"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(resultado);
		if(resultado.equals("Ganaste")) {
			lblNewLabel.setText(messages.getString("iWin"));
		}else if(resultado.equals("Empate")){
			lblNewLabel.setText(messages.getString("Draw"));
		}else {
			lblNewLabel.setText(messages.getString("lost"));
		}
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 54));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 47, 584, 67);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(messages.getString("startMenu"));
		btnNewButton.setForeground(new Color(30, 144, 255));
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuInicio m = new MenuInicio();
				m.setVisible(true);
				((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual
				
			}
		});
		btnNewButton.setBounds(171, 409, 234, 51);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ResultadoPartida.class.getResource("/driver/fondoGeneral.png")));
		lblNewLabel_1.setBounds(0, 0, 584, 497);
		contentPane.add(lblNewLabel_1);
	}
}
