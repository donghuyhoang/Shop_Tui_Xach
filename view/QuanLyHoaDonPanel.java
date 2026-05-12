package view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class QuanLyHoaDonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Các components tìm kiếm
	private JTextField txtTimMaHD;
	private JTextField txtTimMaND;
	private JComboBox<String> cbbVaiTro;
	
	// Bảng dữ liệu
	private JTable tblHoaDon;
	private JTable tblChiTietHD;
	
	// Controller để lấy dữ liệu từ DB
	private controller.HoaDonController hdController = new controller.HoaDonController();

	public QuanLyHoaDonPanel() {
		// Set layout null để dùng toạ độ tuyệt đối (Absolute Layout) 
		setLayout(null);
		
		// ================= 1. KHU VỰC TÌM KIẾM =================
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

		// ================= 2. BẢNG DANH SÁCH HÓA ĐƠN =================
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
			// Khóa không cho người dùng sửa trực tiếp trên bảng
			public boolean isCellEditable(int row, int column) { return false; }
		});
		tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollHoaDon.setViewportView(tblHoaDon);

		// ================= 3. BẢNG CHI TIẾT HÓA ĐƠN =================
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

		// ================= 4. XỬ LÝ SỰ KIỆN =================
		
		// Khi click chọn 1 dòng trên bảng Hóa Đơn -> Hiện Chi Tiết ở bảng dưới
		tblHoaDon.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int row = tblHoaDon.getSelectedRow();
				if (row >= 0) {
					String maHD = tblHoaDon.getValueAt(row, 0).toString();
					loadChiTietHoaDon(maHD);
				}
			}
		});

		// Nút tìm kiếm
		btnTimKiem.addActionListener(e -> thucHienTimKiem());

		// Nút làm mới
		btnLamMoi.addActionListener(e -> {
			txtTimMaHD.setText("");
			txtTimMaND.setText("");
			cbbVaiTro.setSelectedIndex(0);
			refreshData();
		});

		// Tự động load dữ liệu khi vừa mở Panel
		refreshData();
	}

	// Hàm làm mới và tải lại danh sách hóa đơn
	public void refreshData() {
		List<entity.HoaDonEntity> list = hdController.layDanhSachHoaDon();
		fillTableHoaDon(list);
		((DefaultTableModel) tblChiTietHD.getModel()).setRowCount(0); // Xóa bảng chi tiết
	}

	private void thucHienTimKiem() {
		String maHD = txtTimMaHD.getText().trim();
		String maND = txtTimMaND.getText().trim();
		String loaiDon = cbbVaiTro.getSelectedItem().toString();
		List<entity.HoaDonEntity> list = hdController.timKiemHoaDon(maHD, maND, loaiDon);
		fillTableHoaDon(list);
	}

	private void fillTableHoaDon(List<entity.HoaDonEntity> list) {
		DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
		model.setRowCount(0);
		for (entity.HoaDonEntity hd : list) {
			model.addRow(new Object[] {
				hd.getMaHD(), hd.getNgayLap(), hd.getMaND(), hd.getTongTien(), hd.getVaiTro()
			});
		}
	}

	private void loadChiTietHoaDon(String maHD) {
		List<entity.ChiTietHoaDonEntity> list = hdController.layChiTietHoaDon(maHD);
		DefaultTableModel model = (DefaultTableModel) tblChiTietHD.getModel();
		model.setRowCount(0);
		for (entity.ChiTietHoaDonEntity ct : list) {
			model.addRow(new Object[] { ct.getMaSP(), ct.getDonGia(), ct.getSoLuong(), ct.getThanhTien() });
		}
	}
}