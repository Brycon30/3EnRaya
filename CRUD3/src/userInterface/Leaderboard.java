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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import conexion.Conexion;

public class Leaderboard extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel tablaModel;
	private JTable table_1;
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Leaderboard frame = new Leaderboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void ponerDatosTabla() {
		//metodo que ejecuta el proceso desde sql para obtener los datos
		//de los jugadores para ponerlos en la tabla leaderboard
		//esto se hace mediante un procedimiento almacenado
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "exec mostrarLeaderboard";
		
		try {
			//se Prepara el sql
			ps = Conexion.getConnection().prepareStatement(sql);
			//Se ejecuta el sql
			rs = ps.executeQuery();
			//en este caso como no se necesita meterle datos, no se los ponemos
			//aqui se ponen los datos en la tabla de java
			while(rs.next()) {
				Object dato[]=new  Object[2];
			       for (int i=0; i<2; i++){
			           dato[i]=rs.getString(i+1);
			       }
			       tablaModel.addRow(dato);
			       
			}
			//table.setModel(tablaModel);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Leaderboard() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 536);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLeaderboard = new JLabel("Leaderboard");
		lblLeaderboard.setForeground(Color.WHITE);
		lblLeaderboard.setFont(new Font("Arial", Font.BOLD, 35));
		lblLeaderboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblLeaderboard.setBounds(176, 11, 222, 34);
		contentPane.add(lblLeaderboard);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Arial", Font.PLAIN, 11));
		tablaModel = new DefaultTableModel();
		tablaModel.addColumn("Nombre");
		tablaModel.addColumn("Partidas ganadas");
		table.setModel(tablaModel);
		table.setBounds(41, 56, 507, 351);
		contentPane.add(table);
		
		//es necesario para que la tabla se muestre bien aunque no se necesite
		JScrollPane scroll = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(table.getBounds());
		contentPane.add(scroll);
		
		btnVolver = new JButton("Volver");
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
		btnVolver.setBounds(176, 435, 234, 51);
		contentPane.add(btnVolver);

		JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 584, 497);
        ImageIcon backgroundIcon = new ImageIcon("src\\driver\\fondoGeneral.png");
        Image backgroundImg = backgroundIcon.getImage();
        Image scaledImg = backgroundImg.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImg));

        contentPane.add(backgroundLabel, BorderLayout.CENTER);
		
		ponerDatosTabla();
	}
}
