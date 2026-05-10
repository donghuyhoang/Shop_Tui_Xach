package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class HoaDonDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tblChiTiet;
	private JLabel lblKhachHang;
	private JLabel lblTongTien;
	private boolean isConfirmed = false;

	// Constructor giờ chỉ nhận maKH
	public HoaDonDialog(DefaultTableModel cartModel, String maKH, String tongTienText) {
		setTitle("Xác Nhận Hóa Đơn");
		setBounds(100, 100, 500, 450);
		setModal(true); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));
		
		JPanel pnlHeader = new JPanel(new BorderLayout());
		JLabel lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN", JLabel.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		pnlHeader.add(lblTitle, BorderLayout.NORTH);
		
		// Hiển thị Mã KH lên Bill
		String thongTinKhach = "Mã Khách Hàng: " + (maKH.isEmpty() ? "Khách vãng lai" : maKH);
		lblKhachHang = new JLabel(thongTinKhach);
		lblKhachHang.setFont(new Font("Tahoma", Font.ITALIC, 13));
		pnlHeader.add(lblKhachHang, BorderLayout.SOUTH);
		contentPanel.add(pnlHeader, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		tblChiTiet = new JTable();
		DefaultTableModel model = new DefaultTableModel(new String[] {"Tên SP", "Đơn giá", "SL", "Thành tiền"}, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			model.addRow(new Object[] {
				cartModel.getValueAt(i, 1), 
				cartModel.getValueAt(i, 2), 
				cartModel.getValueAt(i, 3), 
				cartModel.getValueAt(i, 4)  
			});
		}
		tblChiTiet.setModel(model);
		scrollPane.setViewportView(tblChiTiet);
		
		JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblText = new JLabel("TỔNG THANH TOÁN: ");
		lblText.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlFooter.add(lblText);
		
		lblTongTien = new JLabel(tongTienText);
		lblTongTien.setForeground(Color.RED);
		lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 16));
		pnlFooter.add(lblTongTien);
		contentPanel.add(pnlFooter, BorderLayout.SOUTH);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("Xác nhận Bán");
		btnOk.setBackground(new Color(50, 205, 50));
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOk.addActionListener(e -> {
			isConfirmed = true;
			dispose(); 
		});
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);
		
		JButton btnCancel = new JButton("Quay lại");
		btnCancel.addActionListener(e -> dispose());
		buttonPane.add(btnCancel);
	}
	
	public boolean isConfirmed() { return isConfirmed; }
}