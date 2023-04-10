package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Appointment;
import Model.Doctor;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourdata = null;
	private DefaultTableModel doctorAppointModel;
	private Object[] doctorAppointData = null;
	private Appointment appoint = new Appointment();
	private JTable table_doctorAppoint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {

		// WHOUR MODEL
		whourModel = new DefaultTableModel();
		Object[] colwhour = new Object[2];
		colwhour[0] = "ID";
		colwhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colwhour);
		whourdata = new Object[2];
		try {
			for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
				whourdata[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
				whourdata[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
				whourModel.addRow(whourdata);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// DOCTOR APPOİNT MODEL
		doctorAppointModel = new DefaultTableModel();
		Object[] colDoctorAppoint = new Object[3];
		colDoctorAppoint[0] = "ID";
		colDoctorAppoint[1] = "Hasta";
		colDoctorAppoint[2] = "Tarih";
		doctorAppointModel.setColumnIdentifiers(colDoctorAppoint);
		doctorAppointData = new Object[3];
		for (int i = 0; i < appoint.getDoctorList(doctor.getId()).size(); i++) {
			doctorAppointData[0] = appoint.getDoctorList(doctor.getId()).get(i).getId();
			doctorAppointData[1] = appoint.getDoctorList(doctor.getId()).get(i).getHastaName();
			doctorAppointData[2] = appoint.getDoctorList(doctor.getId()).get(i).getAppDate();
			doctorAppointModel.addRow(doctorAppointData);
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

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + DoctorGUI.doctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 253, 28);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btnNewButton.setBounds(603, 10, 123, 28);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 48, 716, 405);
		w_pane.add(w_tab);

		JPanel w_whour = new JPanel();
		w_tab.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		select_date.setBounds(10, 11, 130, 20);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"12:30", "13:30", "14:00", "14:30", "15:00", "15:30" }));
		select_time.setBounds(150, 10, 70, 20);
		w_whour.add(select_time);

		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if (date.length() == 0) {
					Helper.showMsg("Lutfen gecerli bir tarih girin");

				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
							updateDoctorAppointModel(doctor.getId());
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_addWhour.setBounds(225, 11, 70, 20);
		w_whour.add(btn_addWhour);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(0, 44, 711, 334);
		w_whour.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow > 0) {
					String selectRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e4) {
						e4.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen bir tarih seçiniz !");
				}
			}
		});
		btn_deleteWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_deleteWhour.setBounds(631, 11, 70, 20);
		w_whour.add(btn_deleteWhour);
		
		JPanel w_doctorAppoint = new JPanel();
		w_tab.addTab("Randevularım", null, w_doctorAppoint, null);
		w_doctorAppoint.setLayout(null);
		
		JScrollPane w_scrollDoctorAppoint = new JScrollPane();
		w_scrollDoctorAppoint.setBounds(10, 10, 691, 358);
		w_doctorAppoint.add(w_scrollDoctorAppoint);
		
		table_doctorAppoint = new JTable(doctorAppointModel);
		w_scrollDoctorAppoint.setViewportView(table_doctorAppoint);
	}

	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourdata[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourdata[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourdata);
		}
	}
	
	public void updateDoctorAppointModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctorAppoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getDoctorList(doctor_id).size(); i++) {
			doctorAppointData[0] = appoint.getDoctorList(doctor_id).get(i).getId();
			doctorAppointData[1] = appoint.getDoctorList(doctor_id).get(i).getHastaName();
			doctorAppointData[2] = appoint.getDoctorList(doctor_id).get(i).getAppDate();
			doctorAppointModel.addRow(doctorAppointData);
		}
	}
}
