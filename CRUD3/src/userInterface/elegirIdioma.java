package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class elegirIdioma extends JFrame {

	private JPanel contentPane;
	public static String idioma = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					elegirIdioma frame = new elegirIdioma();
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
	public elegirIdioma() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEnglish = new JButton("English");
		btnEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idioma = "en";
				Inicio i = new Inicio();
				i.setVisible(true);
				if (i.isVisible()) {
					((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
				}
			}
		});
		btnEnglish.setForeground(new Color(30, 144, 255));
		btnEnglish.setFont(new Font("Arial", Font.PLAIN, 25));
		btnEnglish.setBackground(Color.WHITE);
		btnEnglish.setBounds(254, 87, 170, 57);
		contentPane.add(btnEnglish);
		
		JButton btnEspaufol = new JButton("Espa\u00f1ol");
		btnEspaufol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idioma = "es";
				Inicio i = new Inicio();
				i.setVisible(true);
				if (i.isVisible()) {
					((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();
				}
			}
		});
		btnEspaufol.setForeground(new Color(30, 144, 255));
		btnEspaufol.setFont(new Font("Arial", Font.PLAIN, 25));
		btnEspaufol.setBackground(Color.WHITE);
		btnEspaufol.setBounds(10, 87, 170, 57);
		contentPane.add(btnEspaufol);
		
		JLabel lblIdiomalanguage = new JLabel("Idioma/Language");
		lblIdiomalanguage.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdiomalanguage.setForeground(Color.WHITE);
		lblIdiomalanguage.setFont(new Font("Arial", Font.BOLD, 45));
		lblIdiomalanguage.setBounds(10, 0, 414, 86);
		contentPane.add(lblIdiomalanguage);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setBounds(-166, 0, 600, 536);
		backgroundLabel.setIcon(new ImageIcon(elegirIdioma.class.getResource("/driver/fondoGeneral.png")));
		contentPane.add(backgroundLabel);
	}
}
