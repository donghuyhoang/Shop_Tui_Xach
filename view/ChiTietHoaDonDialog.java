package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HoaDonController;
import entity.ChiTietHoaDonEntity;

public class ChiTietHoaDonDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tblChiTiet;
	private HoaDonController hdController = new HoaDonController();

	public ChiTietHoaDonDialog(int maHD) {
		setTitle("Chi Tiết Hóa Đơn - Mã HD: " + maHD);
		setBounds(100, 100, 600, 350);
		setModal(true); // Khóa màn hình chính khi popup này hiện lên
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));
		
		JLabel lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN SỐ " + maHD, JLabel.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPanel.add(lblTitle, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		tblChiTiet = new JTable();
		tblChiTiet.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP", "Đơn Giá", "Số Lượng", "Thành Tiền"}
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		scrollPane.setViewportView(tblChiTiet);
		
		// Đổ dữ liệu ngay khi khởi tạo
		loadData(maHD);
	}

	private void loadData(int maHD) {
		List<ChiTietHoaDonEntity> list = hdController.layChiTietHoaDon(maHD);
		DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
		model.setRowCount(0);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
		
		for (ChiTietHoaDonEntity ct : list) {
			model.addRow(new Object[] {
				ct.getMaSP(),
				df.format(ct.getDonGia()),
				ct.getSoLuong(),
				df.format(ct.getThanhTien())
			});
		}
	}
}