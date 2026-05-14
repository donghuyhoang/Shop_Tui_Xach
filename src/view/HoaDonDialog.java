package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

	public HoaDonDialog(DefaultTableModel cartModel, String maKH, String tongTienText) {
		setTitle("Xác Nhận Đặt Hàng");
		setBounds(100, 100, 550, 500);
		setModal(true); 
		setLocationRelativeTo(null);
		
		getContentPane().setBackground(new Color(24, 24, 24));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(24, 24, 24));
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));
		
		JPanel pnlHeader = new JPanel(new BorderLayout());
		pnlHeader.setBackground(new Color(24, 24, 24));
		
		JLabel lblTitle = new JLabel("CHI TIẾT ĐƠN HÀNG", JLabel.CENTER);
		lblTitle.setForeground(new Color(255, 191, 0));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		pnlHeader.add(lblTitle, BorderLayout.NORTH);
		
		String thongTinKhach = "Mã Khách Hàng: " + (maKH.isEmpty() ? "Khách vãng lai" : maKH);
		lblKhachHang = new JLabel(thongTinKhach);
		lblKhachHang.setForeground(Color.WHITE);
		lblKhachHang.setFont(new Font("Tahoma", Font.ITALIC, 13));
		pnlHeader.add(lblKhachHang, BorderLayout.SOUTH);
		contentPanel.add(pnlHeader, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		tblChiTiet = new JTable();
		DefaultTableModel model = new DefaultTableModel(new String[] {"Tên SP", "Đơn giá", "SL", "Thành tiền"}, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			double donGia = Double.parseDouble(cartModel.getValueAt(i, 2).toString());
			double thanhTien = Double.parseDouble(cartModel.getValueAt(i, 4).toString());
			model.addRow(new Object[] {
				cartModel.getValueAt(i, 1), df.format(donGia), cartModel.getValueAt(i, 3), df.format(thanhTien)  
			});
		}
		tblChiTiet.setModel(model);
		
		tblChiTiet.setBackground(new Color(40, 40, 40));
		tblChiTiet.setForeground(Color.WHITE);
		tblChiTiet.setGridColor(Color.GRAY);
		tblChiTiet.getTableHeader().setBackground(new Color(255, 191, 0));
		tblChiTiet.getTableHeader().setForeground(Color.BLACK);
		tblChiTiet.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		
		scrollPane.setViewportView(tblChiTiet);
		
		JPanel pnlFooter = new JPanel(new java.awt.GridLayout(2, 1, 0, 5));
		pnlFooter.setBackground(new Color(24, 24, 24));
		
		JPanel pnlPayment = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlPayment.setBackground(new Color(24, 24, 24));
		JLabel lblPTTT = new JLabel("Phương thức thanh toán: ");
		lblPTTT.setForeground(Color.WHITE);
		pnlPayment.add(lblPTTT);
		
		JComboBox<String> cbbThanhToan = new JComboBox<>(new String[]{"Thanh toán khi nhận hàng (COD)", "Chuyển khoản VNPay", "Ví MoMo"});
		pnlPayment.add(cbbThanhToan);
		pnlFooter.add(pnlPayment);
		
		JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlTotal.setBackground(new Color(24, 24, 24));
		JLabel lblText = new JLabel("TỔNG THANH TOÁN: ");
		lblText.setForeground(Color.WHITE);
		lblText.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTotal.add(lblText);
		
		lblTongTien = new JLabel(tongTienText);
		lblTongTien.setForeground(new Color(255, 191, 0));
		lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 18));
		pnlTotal.add(lblTongTien);
		
		pnlFooter.add(pnlTotal);
		contentPanel.add(pnlFooter, BorderLayout.SOUTH);
		
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setBackground(new Color(24, 24, 24));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("Xác nhận Đặt hàng");
		btnOk.setBackground(new Color(50, 205, 50));
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOk.addActionListener(e -> {
			isConfirmed = true;
			dispose(); 
		});
		buttonPane.add(btnOk);
		
		JButton btnCancel = new JButton("Quay lại");
		btnCancel.setBackground(Color.GRAY);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.addActionListener(e -> dispose());
		buttonPane.add(btnCancel);
	}
	
	public boolean isConfirmed() { return isConfirmed; }
}