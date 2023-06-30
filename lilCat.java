package servidor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class lilCat extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldResultado;
	private JButton btn00;
	private JButton btn01;
	private JButton btn02;
	private JButton btn10;
	private JButton btn11;
	private JButton btn12;
	private JButton btn20;
	private JButton btn21;
	private JButton btn22;
	public int turn = 1;
	public boolean flag = false;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lilCat frame = new lilCat();
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
	public lilCat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldResultado = new JTextField();
		textFieldResultado.setToolTipText("Se muestra ganador.");
		textFieldResultado.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldResultado.setFont(new Font("Arial", Font.PLAIN, 22));
		textFieldResultado.setBounds(10, 11, 287, 31);
		contentPane.add(textFieldResultado);
		textFieldResultado.setColumns(10);

		btn00 = new JButton("");
		btn00.setFont(new Font("Arial", Font.PLAIN, 22));
		btn00.setBounds(10, 53, 89, 89);
		contentPane.add(btn00);

		btn01 = new JButton("");
		btn01.setFont(new Font("Arial", Font.PLAIN, 22));
		btn01.setBounds(109, 53, 89, 89);
		contentPane.add(btn01);

		btn02 = new JButton("");
		btn02.setFont(new Font("Arial", Font.PLAIN, 22));
		btn02.setBounds(208, 53, 89, 89);
		contentPane.add(btn02);

		btn10 = new JButton("");
		btn10.setFont(new Font("Arial", Font.PLAIN, 22));
		btn10.setBounds(10, 153, 89, 89);
		contentPane.add(btn10);

		btn11 = new JButton("");
		btn11.setFont(new Font("Arial", Font.PLAIN, 22));
		btn11.setBounds(109, 153, 89, 89);
		contentPane.add(btn11);

		btn12 = new JButton("");
		btn12.setFont(new Font("Arial", Font.PLAIN, 22));
		btn12.setBounds(208, 153, 89, 89);
		contentPane.add(btn12);

		btn20 = new JButton("");
		btn20.setFont(new Font("Arial", Font.PLAIN, 22));
		btn20.setBounds(10, 253, 89, 89);
		contentPane.add(btn20);

		btn21 = new JButton("");
		btn21.setFont(new Font("Arial", Font.PLAIN, 22));
		btn21.setBounds(109, 253, 89, 89);
		contentPane.add(btn21);

		btn22 = new JButton("");
		btn22.setFont(new Font("Arial", Font.PLAIN, 22));
		btn22.setBounds(208, 253, 89, 89);
		contentPane.add(btn22);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(109, 353, 89, 23);
		contentPane.add(btnSalir);

		Manejador escuchador = new Manejador();

		btnSalir.addActionListener(escuchador);
		btn00.addActionListener(escuchador);
		btn01.addActionListener(escuchador);
		btn02.addActionListener(escuchador);
		btn10.addActionListener(escuchador);
		btn11.addActionListener(escuchador);
		btn12.addActionListener(escuchador);
		btn20.addActionListener(escuchador);
		btn21.addActionListener(escuchador);
		btn22.addActionListener(escuchador);

	}

	public class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getSource().equals(btnSalir)) {
				System.exit(0);
			}
			
			if (e.getSource().equals(btn00)) {
				if (btn00.getText().equals("")) {
					if (turn % 2 == 1) {
						btn00.setText("X");
					} else {
						btn00.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn01)) {
				if (btn01.getText().equals("")) {
					if (turn % 2 == 1) {
						btn01.setText("X");
					} else {
						btn01.setText("O");
					}
					turn++;
				}

			}
			if (e.getSource().equals(btn02)) {
				if (btn02.getText().equals("")) {
					if (turn % 2 == 1) {
						btn02.setText("X");
					} else {
						btn02.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn10)) {
				if (btn10.getText().equals("")) {
					if (turn % 2 == 1) {
						btn10.setText("X");
					} else {
						btn10.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn11)) {
				if (btn11.getText().equals("")) {
					if (turn % 2 == 1) {
						btn11.setText("X");
					} else {
						btn11.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn12)) {
				if (btn12.getText().equals("")) {
					if (turn % 2 == 1) {
						btn12.setText("X");
					} else {
						btn12.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn20)) {
				if (btn20.getText().equals("")) {
					if (turn % 2 == 1) {
						btn20.setText("X");
					} else {
						btn20.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn21)) {
				if (btn21.getText().equals("")) {
					if (turn % 2 == 1) {
						btn21.setText("X");
					} else {
						btn21.setText("O");
					}
					turn++;
				}
			}
			if (e.getSource().equals(btn22)) {
				if (btn22.getText().equals("")) {
					if (turn % 2 == 1) {
						btn22.setText("X");
					} else {
						btn22.setText("O");
					}
					turn++;
				}
			}

			comprobar();

		}

		private void comprobar() {
			// TODO Auto-generated method stub
			if (btn00.getText().equals("X") && btn01.getText().equals("X") && btn02.getText().equals("X")
					|| (btn00.getText().equals("O") && btn01.getText().equals("O") && btn02.getText().equals("O"))) {
				if (btn00.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn00.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn10.getText().equals("X") && btn11.getText().equals("X") && btn12.getText().equals("X")
					|| (btn10.getText().equals("O") && btn11.getText().equals("O") && btn12.getText().equals("O"))) {
				if (btn10.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn10.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn20.getText().equals("X") && btn21.getText().equals("X") && btn22.getText().equals("X")
					|| (btn20.getText().equals("O") && btn21.getText().equals("O") && btn22.getText().equals("O"))) {
				if (btn20.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn20.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn00.getText().equals("X") && btn10.getText().equals("X") && btn20.getText().equals("X")
					|| (btn00.getText().equals("O") && btn10.getText().equals("O") && btn20.getText().equals("O"))) {
				if (btn00.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn00.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn01.getText().equals("X") && btn11.getText().equals("X") && btn21.getText().equals("X")
					|| (btn01.getText().equals("O") && btn11.getText().equals("O") && btn21.getText().equals("O"))) {
				if (btn01.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn01.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn02.getText().equals("X") && btn12.getText().equals("X") && btn22.getText().equals("X")
					|| (btn02.getText().equals("O") && btn12.getText().equals("O") && btn22.getText().equals("O"))) {
				if (btn02.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn02.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn02.getText().equals("X") && btn12.getText().equals("X") && btn22.getText().equals("X")
					|| (btn02.getText().equals("O") && btn12.getText().equals("O") && btn22.getText().equals("O"))) {
				if (btn02.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn02.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn00.getText().equals("X") && btn11.getText().equals("X") && btn22.getText().equals("X")
					|| (btn00.getText().equals("O") && btn11.getText().equals("O") && btn22.getText().equals("O"))) {
				if (btn00.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn00.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			} else if (btn02.getText().equals("X") && btn11.getText().equals("X") && btn20.getText().equals("X")
					|| (btn02.getText().equals("O") && btn11.getText().equals("O") && btn20.getText().equals("O"))) {
				if (btn02.getText().equals("X")) {
					textFieldResultado.setText("Jugador X ha ganado");
					bloquearBotones();
				} else if (btn02.getText().equals("O")) {
					textFieldResultado.setText("Jugador O ha ganado");
					bloquearBotones();
				}
				flag = true;
			}

			if (btn00.getText().isEmpty() || btn01.getText().isEmpty() || btn02.getText().isEmpty()
					|| btn10.getText().isEmpty() || btn11.getText().isEmpty() || btn12.getText().isEmpty()
					|| btn20.getText().isEmpty() || btn21.getText().isEmpty() || btn22.getText().isEmpty()) {

			} else {
				if (flag == false) {
					textFieldResultado.setText("Empate");
				}
			}

		}

		private void bloquearBotones() {
			// TODO Auto-generated method stub
			
			btn00.setEnabled(false);
			btn01.setEnabled(false);
			btn02.setEnabled(false);
			btn10.setEnabled(false);
			btn11.setEnabled(false);
			btn12.setEnabled(false);
			btn20.setEnabled(false);
			btn21.setEnabled(false);
			btn22.setEnabled(false);
		}
	}
}
