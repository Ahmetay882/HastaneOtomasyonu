package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.*;
import Model.Hasta;
import Model.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel label = new JLabel("Ad Soyad");
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		label.setBounds(10, 10, 134, 20);
		w_pane.add(label);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBackground(Color.WHITE);
		fld_name.setBounds(10, 29, 266, 25);
		w_pane.add(fld_name);
		
		JLabel lblTcNumaras = new JLabel("T.C Numarası");
		lblTcNumaras.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblTcNumaras.setBounds(10, 64, 134, 20);
		w_pane.add(lblTcNumaras);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBackground(Color.WHITE);
		fld_tcno.setBounds(10, 83, 266, 25);
		w_pane.add(fld_tcno);
		
		JLabel lblifre = new JLabel("Şifre");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblifre.setBounds(10, 118, 134, 20);
		w_pane.add(lblifre);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 137, 266, 25);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length() == 0 || fld_pass.getText().length() == 0 || fld_name.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean control = hasta.register(fld_tcno.getText(), fld_pass.getText(), fld_name.getText());
					if(control) {
						Helper.showMsg("success");
						LoginGUI login = new LoginGUI();
						login.setVisible(true);
						dispose();
					} else {
						Helper.showMsg("error");
					}					
				}
			}
		});
		btn_register.setForeground(Color.BLACK);
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_register.setBackground(Color.WHITE);
		btn_register.setBounds(10, 180, 266, 35);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri Dön");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setForeground(Color.BLACK);
		btn_backto.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_backto.setBackground(Color.WHITE);
		btn_backto.setBounds(10, 225, 266, 35);
		w_pane.add(btn_backto);
	}
}
