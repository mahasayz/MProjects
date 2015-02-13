package com.maha.weather.starter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class WeatherApp {

	public JFrame frame;
	public JTable table;
	public JLabel tempLabelityOfLondon;
	public JLabel iconLabel;
	public JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeatherApp window = new WeatherApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeatherApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1009, 594);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 13, 967, 521);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		table.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		table.setBackground(UIManager.getColor("Button.background"));
		/*table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Wind", null},
				{"Cloudiness", null},
				{"Pressure", null},
				{"Humidity", null},
				{"Sunrise", null},
				{"Sunset", null},
				{"Geo coords", null},
			},
			new String[] {
				"New column", "New column"
			}
		));*/
		table.setBounds(12, 104, 237, 112);
		panel.add(table);
		
		tempLabelityOfLondon = new JLabel("Weather Reporter");
		tempLabelityOfLondon.setHorizontalAlignment(SwingConstants.CENTER);
		tempLabelityOfLondon.setFont(new Font("Tahoma", Font.BOLD, 15));
		tempLabelityOfLondon.setBounds(12, 13, 237, 24);
		panel.add(tempLabelityOfLondon);
		
		iconLabel = new JLabel();
		iconLabel.setIcon(null);
		iconLabel.setBounds(12, 50, 66, 30);
		panel.add(iconLabel);
	}
}
