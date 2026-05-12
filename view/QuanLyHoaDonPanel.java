package view;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.HoaDonController;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;

public class QuanLyHoaDonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtTimMaHD;
	private JTextField txtTimMaND;
	private JComboBox<String> cbbVaiTro;
	private JTable tblHoaDon;
	private JTable tblChiTietHD;
	
	private HoaDonController hdController = new HoaDonController();

	public QuanLyHoaDonPanel() {
		setLayout(null);
		
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm Hóa Đơn"));
		pnlTimKiem.setBounds(10, 10, 770, 70);
		pnlTimKiem.setLayout(null);
		add(pnlTimKiem);
		
		JLabel lblMaHD = new JLabel("Mã HD:");
		lblMaHD.setBounds(20, 25, 50, 20);
		pnlTimKiem.add(lblMaHD);
		
		txtTimMaHD = new JTextField();
		txtTimMaHD.setBounds(70, 25, 100, 22);
		pnlTimKiem.add(txtTimMaHD);
		
		JLabel lblMaND = new JLabel("Mã KH:");
		lblMaND.setBounds(190, 25, 50, 20);
		pnlTimKiem.add(lblMaND);
		
		txtTimMaND = new JTextField();
		txtTimMaND.setBounds(240, 25, 100, 22);
		pnlTimKiem.add(txtTimMaND);
		
		JLabel lblVaiTro = new JLabel("Loại đơn:");
		lblVaiTro.setBounds(360, 25, 60, 20);
		pnlTimKiem.add(lblVaiTro);
		
		cbbVaiTro = new JComboBox<String>();
		cbbVaiTro.addItem("Tất cả");
		cbbVaiTro.addItem("Mua tại quầy");
		cbbVaiTro.addItem("Đặt online");
		cbbVaiTro.setBounds(420, 25, 110, 22);
		pnlTimKiem.add(cbbVaiTro);
		
		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(550, 24, 100, 25);
		pnlTimKiem.add(btnTimKiem);
		
		JButton btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBounds(660, 24, 90, 25);
		pnlTimKiem.add(btnLamMoi);

		JPanel pnlDS = new JPanel();
		pnlDS.setBorder(BorderFactory.createTitledBorder("Danh sách Hóa Đơn"));
		pnlDS.setBounds(10, 90, 770, 200);
		pnlDS.setLayout(null);
		add(pnlDS);
		
		JScrollPane scrollHoaDon = new JScrollPane();
		scrollHoaDon.setBounds(10, 20, 750, 170);
		pnlDS.add(scrollHoaDon);
		
		tblHoaDon = new JTable();
		tblHoaDon.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã HD", "Ngày Lập", "Mã KH (ND)", "Tổng Tiền", "Loại đơn"}
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollHoaDon.setViewportView(tblHoaDon);

		JPanel pnlChiTiet = new JPanel();
		pnlChiTiet.setBorder(BorderFactory.createTitledBorder("Chi Tiết Hóa Đơn (Click chọn Hóa Đơn ở trên)"));
		pnlChiTiet.setBounds(10, 300, 770, 200);
		pnlChiTiet.setLayout(null);
		add(pnlChiTiet);
		
		JScrollPane scrollChiTiet = new JScrollPane();
		scrollChiTiet.setBounds(10, 20, 750, 170);
		pnlChiTiet.add(scrollChiTiet);
		
		tblChiTietHD = new JTable();
		tblChiTietHD.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP", "Đơn Giá", "Số Lượng", "Thành Tiền"}
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		tblChiTietHD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollChiTiet.setViewportView(tblChiTietHD);
		
		loadDataToTableHoaDon();

		btnTimKiem.addActionListener(e -> {
			try {
				String maHD = txtTimMaHD.getText().trim();
				String maND = txtTimMaND.getText().trim();
				String vaiTro = cbbVaiTro.getSelectedItem().toString();
				
				List<HoaDonEntity> ds = hdController.timKiemHoaDon(maHD, maND, vaiTro);
				fillTableHoaDon(ds);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Mã Hóa Đơn phải là số hợp lệ!");
			}
		});

		btnLamMoi.addActionListener(e -> {
			txtTimMaHD.setText("");
			txtTimMaND.setText("");
			cbbVaiTro.setSelectedIndex(0);
			loadDataToTableHoaDon();
			((DefaultTableModel) tblChiTietHD.getModel()).setRowCount(0); 
		});

		tblHoaDon.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int row = tblHoaDon.getSelectedRow();
					if (row >= 0) {
						// Đã sửa thành int
						int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
						loadDataToTableChiTiet(maHD);
					}
				}
			}
		});
	}

	private void loadDataToTableHoaDon() {
		List<HoaDonEntity> list = hdController.layDanhSachHoaDon();
		fillTableHoaDon(list);
	}

	private void fillTableHoaDon(List<HoaDonEntity> list) {
		DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
		model.setRowCount(0);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for (HoaDonEntity hd : list) {
			String maND = (hd.getMaND() == null || hd.getMaND().isEmpty()) ? "Khách vãng lai" : hd.getMaND();
			String ngayLapStr = (hd.getNgayLap() != null) ? sdf.format(hd.getNgayLap()) : "";

			model.addRow(new Object[] {
				hd.getMaHD(),
				ngayLapStr, 
				maND,
				df.format(hd.getTongTien()) + " VNĐ",
				hd.getVaiTro()
			});
		}
	}

	// Đã sửa thành int maHD
	private void loadDataToTableChiTiet(int maHD) {
		List<ChiTietHoaDonEntity> list = hdController.layChiTietHoaDon(maHD);
		DefaultTableModel model = (DefaultTableModel) tblChiTietHD.getModel();
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
	public void refreshData() {
		loadDataToTableHoaDon(); // Tải lại danh sách hóa đơn từ DB
		((DefaultTableModel) tblChiTietHD.getModel()).setRowCount(0); // Xóa trắng bảng chi tiết
	}
}