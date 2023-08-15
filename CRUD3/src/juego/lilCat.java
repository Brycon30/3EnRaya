package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import userInterface.ResultadoPartida;
import userInterface.elegirIdioma;

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
	private JButton btnRendirse;
	public char ficha ='X';
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);

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
		setTitle(messages.getString("gameName"));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 426);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldResultado = new JTextField();
		textFieldResultado.setEditable(false);
		textFieldResultado.setForeground(new Color(30, 144, 255));
		textFieldResultado.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldResultado.setFont(new Font("Arial", Font.PLAIN, 22));
		textFieldResultado.setBounds(10, 11, 287, 31);
		contentPane.add(textFieldResultado);
		textFieldResultado.setColumns(10);

		btn00 = new JButton(" ");
		btn00.setBackground(Color.WHITE);
		btn00.setForeground(new Color(30, 144, 255));
		btn00.setFont(new Font("Arial", Font.BOLD, 25));
		btn00.setBounds(10, 53, 89, 89);
		contentPane.add(btn00);

		btn01 = new JButton(" ");
		btn01.setBackground(Color.WHITE);
		btn01.setForeground(new Color(30, 144, 255));
		btn01.setFont(new Font("Arial", Font.BOLD, 25));
		btn01.setBounds(109, 53, 89, 89);
		contentPane.add(btn01);

		btn02 = new JButton(" ");
		btn02.setBackground(Color.WHITE);
		btn02.setForeground(new Color(30, 144, 255));
		btn02.setFont(new Font("Arial", Font.BOLD, 25));
		btn02.setBounds(208, 53, 89, 89);
		contentPane.add(btn02);

		btn10 = new JButton(" ");
		btn10.setBackground(Color.WHITE);
		btn10.setForeground(new Color(30, 144, 255));
		btn10.setFont(new Font("Arial", Font.BOLD, 25));
		btn10.setBounds(10, 153, 89, 89);
		contentPane.add(btn10);

		btn11 = new JButton(" ");
		btn11.setBackground(Color.WHITE);
		btn11.setForeground(new Color(30, 144, 255));
		btn11.setFont(new Font("Arial", Font.BOLD, 25));
		btn11.setBounds(109, 153, 89, 89);
		contentPane.add(btn11);

		btn12 = new JButton(" ");
		btn12.setBackground(Color.WHITE);
		btn12.setForeground(new Color(30, 144, 255));
		btn12.setFont(new Font("Arial", Font.BOLD, 25));
		btn12.setBounds(208, 153, 89, 89);
		contentPane.add(btn12);

		btn20 = new JButton(" ");
		btn20.setBackground(Color.WHITE);
		btn20.setForeground(new Color(30, 144, 255));
		btn20.setFont(new Font("Arial", Font.BOLD, 25));
		btn20.setBounds(10, 253, 89, 89);
		contentPane.add(btn20);

		btn21 = new JButton(" ");
		btn21.setBackground(Color.WHITE);
		btn21.setForeground(new Color(30, 144, 255));
		btn21.setFont(new Font("Arial", Font.BOLD, 25));
		btn21.setBounds(109, 253, 89, 89);
		contentPane.add(btn21);

		btn22 = new JButton(" ");
		btn22.setBackground(Color.WHITE);
		btn22.setForeground(new Color(30, 144, 255));
		btn22.setFont(new Font("Arial", Font.BOLD, 25));
		btn22.setBounds(208, 253, 89, 89);
		contentPane.add(btn22);

		btnRendirse = new JButton(messages.getString("giveUp"));
		btnRendirse.setForeground(new Color(30, 144, 255));
		btnRendirse.setFont(new Font("Arial", Font.PLAIN, 20));
		btnRendirse.setBackground(Color.WHITE);
		btnRendirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRendirse.setBounds(80, 353, 150, 23);
		contentPane.add(btnRendirse);
		//Bloqueamos botones para impedir que se pueda jugar sin nadie en linea
		
		bloquearBotones();
		textFieldResultado.setText(messages.getString("Waiting"));

		// Comenzar a recivir mensajes
		Thread recibirMensajesThread = new Thread(new RecibirMensajes());
		recibirMensajesThread.start();

		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 310, 387);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel, BorderLayout.CENTER);

		Manejador escuchador = new Manejador();

		btnRendirse.addActionListener(escuchador);
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

	
	//detecta que boton ha sido pulsado
	public class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource().equals(btnRendirse)) {
				enviarMensaje("me rindo");
				//se utiliza para salir en medio de una partida y darle el punto al otro
			}

			if (e.getSource().equals(btn00)) {
				if (btn00.getText().equals(" ")) {
					PressButton("btn00",ficha);//se manda el mensaje de la posicion clikeada y la ficha
					btn00.setText(String.valueOf(ficha));//se pone el clikeado en el tablero
				}
			}
			if (e.getSource().equals(btn01)) {
				if (btn01.getText().equals(" ")) {
					PressButton("btn01",ficha);
					btn01.setText(String.valueOf(ficha));
				}

			}
			if (e.getSource().equals(btn02)) {
				if (btn02.getText().equals(" ")) {
					PressButton("btn02",ficha);
					btn02.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn10)) {
				if (btn10.getText().equals(" ")) {
					PressButton("btn10",ficha);
					btn10.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn11)) {
				if (btn11.getText().equals(" ")) {
					PressButton("btn11",ficha);
					btn11.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn12)) {
				if (btn12.getText().equals(" ")) {
					PressButton("btn12",ficha);
					btn12.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn20)) {
				if (btn20.getText().equals(" ")) {
					PressButton("btn20",ficha);
					btn20.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn21)) {
				if (btn21.getText().equals(" ")) {
					PressButton("btn21",ficha);
					btn21.setText(String.valueOf(ficha));
				}
			}
			if (e.getSource().equals(btn22)) {
				if (btn22.getText().equals(" ")) {
					PressButton("btn22",ficha);
					btn22.setText(String.valueOf(ficha));
				}
			}

			comprobar();

		}
	}

	private void comprobar() {
		if (btn00.getText().charAt(0)==ficha && btn01.getText().charAt(0)==ficha && btn02.getText().charAt(0)==ficha) {
			 enviarMensaje("Gane");
			 ResultPartida("Ganaste");
		} else if (btn10.getText().charAt(0)==ficha && btn11.getText().charAt(0)==ficha && btn12.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn20.getText().charAt(0)==ficha && btn21.getText().charAt(0)==ficha && btn22.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn00.getText().charAt(0)==ficha && btn10.getText().charAt(0)==ficha && btn20.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn01.getText().charAt(0)==ficha && btn11.getText().charAt(0)==ficha && btn21.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn02.getText().charAt(0)==ficha && btn12.getText().charAt(0)==ficha && btn22.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn02.getText().charAt(0)==ficha && btn12.getText().charAt(0)==ficha && btn22.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn00.getText().charAt(0)==ficha && btn11.getText().charAt(0)==ficha && btn22.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (btn02.getText().charAt(0)==ficha && btn11.getText().charAt(0)==ficha && btn20.getText().charAt(0)==ficha) {
			enviarMensaje("Gane");
			ResultPartida("Ganaste");
		} else if (!btn00.getText().equals(" ") && !btn01.getText().equals(" ") && !btn02.getText().equals(" ")
				&& !btn10.getText().equals(" ") && !btn11.getText().equals(" ") && !btn12.getText().equals(" ")
				&& !btn20.getText().equals(" ") && !btn21.getText().equals(" ") && !btn22.getText().equals(" ")) {
			ResultPartida("Empate");
			enviarMensaje("Empate");
		} 
		

	}

	//se utiliza en el turno del jugador para que pueda jugar en su turno
	private void desbloquearBotones() {
		btn00.setEnabled(true);
		btn01.setEnabled(true);
		btn02.setEnabled(true);
		btn10.setEnabled(true);
		btn11.setEnabled(true);
		btn12.setEnabled(true);
		btn20.setEnabled(true);
		btn21.setEnabled(true);
		btn22.setEnabled(true);
	}

	
	//bloquea los botones para que el jugador espere su turno
	public void bloquearBotones() {
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

	//envia un mensaje al otro jugador
	private void enviarMensaje(String mensaje) {
		try {
			ConexionJuego.outputStream.write(mensaje.getBytes());
			ConexionJuego.outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class RecibirMensajes implements Runnable {
		@Override
		public void run() {
			try {
				InputStream inputStream = ConexionJuego.cliente.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            String mensaje;

				while ((mensaje = reader.readLine()) != null) {
					System.out.println(mensaje);
						
						
						//se usa cuando empiezas en el juego
						if (mensaje.equals("tu turno")) {
							((JFrame) SwingUtilities.getWindowAncestor(contentPane)).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
							
							desbloquearBotones();
							textFieldResultado.setText("tu turno");
							ficha='X';
						} else if (mensaje.equals("espera turno")) {
							//se usa cuando eres el jugador 2
							((JFrame) SwingUtilities.getWindowAncestor(contentPane)).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
							textFieldResultado.setText("Turno de tu contrincante");
							ficha='O';
						}
						if(mensaje.equals("Gane")) {
							//si el otro jugador te manda un Gane significa que perdiste
							ResultPartida("Perdiste");
							
						}
						if(mensaje.equals("me rindo")) {
							//es cuando tu contrincante se rinde entonces tu obtienes el punto
							enviarMensaje("Gane");
							ResultPartida("Ganaste");
							
						}
						if(mensaje.equals("Empate")) {
							//si el otro jugador te manda un Gane significa que perdiste
							ResultPartida("Empate");
							
						}
						
						//es para manejar las posiciones pulsadas por tu contrincante
						if (mensaje.substring(0,1).equals("b")) {
							//Mensaje btn01X
							if(mensaje.substring(0,5).equals("btn00")) {
								btn00.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn01")) {
								btn01.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn02")) {
								btn02.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn10")) {
								btn10.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn11")) {
								btn11.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn12")) {
								btn12.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn20")) {
								btn20.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn21")) {
								btn21.setText(mensaje.substring(5,6));
								MiTurno();
							}else if(mensaje.substring(0,5).equals("btn22")) {
								btn22.setText(mensaje.substring(5,6));
								MiTurno();
							}
							
						}
						
						
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//cuando te toca empezar el juego
	private void MiTurno() {
		desbloquearBotones();
		textFieldResultado.setText("tu turno");
	}
	
	
	//cuando presionas un boton aqui se manda el boton pulsado al jugador
	private void PressButton(String btnName, char ficha) {
		enviarMensaje(btnName+ficha);
		bloquearBotones();
		textFieldResultado.setText("Turno de tu contrincante");
		
	}
	
	//con este metodo se muestra si ganaste o perdiste o empate
	private void ResultPartida(String resultado) {
		ResultadoPartida r = new ResultadoPartida(resultado);
		r.setVisible(true);
		((JFrame) SwingUtilities.getWindowAncestor(contentPane)).dispose();// cerramos la pantalla actual
	}


}







//cuando uno se sale se le da el punto al otro :D
