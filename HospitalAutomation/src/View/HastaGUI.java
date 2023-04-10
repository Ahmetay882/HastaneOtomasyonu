package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.NonReadableChannelException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doktor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorId = 0;
	private String selectDoktorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HastaGUI(Hasta hasta) throws SQLException {

		// DOKTOR MODEL
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		// WHOUR MODEL
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		// HASTA APPOİNT MODEL
		appointModel = new DefaultTableModel();
		Object[] colappoint = new Object[3];
		colappoint[0] = "ID";
		colappoint[1] = "Doktor";
		colappoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colappoint);
		appointData = new Object[3];
		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + hasta.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 253, 28);
		w_pane.add(lblNewLabel);

		JButton btn_exist = new JButton("Çıkış Yap");
		btn_exist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_exist.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_exist.setBounds(603, 10, 123, 28);
		w_pane.add(btn_exist);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 48, 716, 405);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(new Color(255, 255, 255));
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoktor = new JScrollPane();
		w_scrollDoktor.setBounds(10, 30, 280, 338);
		w_appointment.add(w_scrollDoktor);

		table_doktor = new JTable(doctorModel);
		w_scrollDoktor.setViewportView(table_doktor);

		JLabel label_1 = new JLabel("Doktor Listesi");
		label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		label_1.setBounds(10, 10, 108, 13);
		w_appointment.add(label_1);

		JLabel label_2 = new JLabel("Poliklinik Adı");
		label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		label_2.setBounds(300, 13, 134, 20);
		w_appointment.add(label_2);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(300, 43, 150, 35);
		select_clinic.addItem("--Poliklinik Seç--");
		for (int i = 0; i < clinic.getList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		w_appointment.add(select_clinic);

		JLabel lblDoktorSe = new JLabel("Doktor Seç");
		lblDoktorSe.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblDoktorSe.setBounds(300, 104, 134, 20);
		w_appointment.add(lblDoktorSe);

		JButton btn_selDoktor = new JButton("SEÇ");
		btn_selDoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doktor.getSelectedRow();
				if (row >= 0) {
					String value = table_doktor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					selectDoctorId = id;
					selectDoktorName = table_doktor.getModel().getValueAt(row, 1).toString();
				} else {
					Helper.showMsg("Lütfen bir doktor seçiniz !");
				}
			}
		});
		btn_selDoktor.setForeground(Color.BLACK);
		btn_selDoktor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_selDoktor.setBackground(Color.WHITE);
		btn_selDoktor.setBounds(300, 129, 140, 35);
		w_appointment.add(btn_selDoktor);

		JLabel lblUygunSaatler = new JLabel("Uygun Saatler");
		lblUygunSaatler.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblUygunSaatler.setBounds(466, 10, 235, 13);
		w_appointment.add(lblUygunSaatler);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(466, 30, 235, 338);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);

		JLabel lblRandevu = new JLabel("Randevu ");
		lblRandevu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblRandevu.setBounds(300, 186, 134, 20);
		w_appointment.add(lblRandevu);

		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorId, hasta.getId(), selectDoktorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorId, date);
							updateWhourModel(selectDoctorId);
							updateAppointModel(hasta.getId());
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz !");
				}
			}
		});
		btn_addAppoint.setForeground(Color.BLACK);
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_addAppoint.setBackground(Color.WHITE);
		btn_addAppoint.setBounds(300, 211, 140, 35);
		w_appointment.add(btn_addAppoint);

		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevularım", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 10, 691, 358);
		w_appoint.add(w_scrollAppoint);

		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	
	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
	}
	
}
