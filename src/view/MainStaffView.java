package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.SanPhamController;
import dto.SanPhamDTO;

public class MainStaffView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelCenter;
	private JTextField txtTenSP;
	private JTable tblSanPham;
	private JComboBox<String> cbbThuongHieu, cbbGia, cbbSapXep;
	private SanPhamController spController = new SanPhamController();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> { 
			try { 
				new MainStaffView().setVisible(true);
				} 
			catch (Exception e) {
				e.printStackTrace(); 
				} 
			});
	}

	public MainStaffView() {
		setTitle("Shop Túi Xách - Hệ Thống Quản Trị");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 650); 
		setLocationRelativeTo(null);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(24, 24, 24));
		setContentPane(contentPane);
		
		// Menu bên trái
		JPanel panelMenu = new JPanel(new GridLayout(7, 1, 0, 10));
		panelMenu.setBackground(new Color(40, 40, 40));
		panelMenu.setPreferredSize(new Dimension(220, 0));
		contentPane.add(panelMenu, BorderLayout.WEST);
		
		JLabel lblLogo = new JLabel(" QUẢN TRỊ VIÊN ", SwingConstants.CENTER);
		lblLogo.setForeground(new Color(255, 191, 0));
		lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panelMenu.add(lblLogo);

		JButton btnQLTaiQuay = createMenuBtn("Bán Hàng Tại Quầy");
		JButton btnQLSanPham = createMenuBtn("Quản Lý Sản Phẩm");
		JButton btnQLHoaDon = createMenuBtn("Quản Lý Hóa Đơn");
		JButton btnThongKe = createMenuBtn("Thống Kê Doanh Thu");
		JButton btnDangXuat = createMenuBtn("Đăng Xuất");

		panelMenu.add(btnQLTaiQuay); panelMenu.add(btnQLSanPham); 
		panelMenu.add(btnQLHoaDon); panelMenu.add(btnThongKe); 
		panelMenu.add(new JLabel());
		panelMenu.add(btnDangXuat);

		// Khu vực trung tâm
		panelCenter = new JPanel(new CardLayout());
		panelCenter.setBackground(new Color(24, 24, 24));
		contentPane.add(panelCenter, BorderLayout.CENTER);
		
		// Quản lý sản phẩm
		JPanel panelQLSanPham = new JPanel(null);
		panelQLSanPham.setBackground(new Color(24, 24, 24));
		panelCenter.add(panelQLSanPham, "CardSanPham");
		
		// Tìm Kiếm
		addLabel(panelQLSanPham, "Tên:", 20, 15, 40, 20);
		txtTenSP = new JTextField(); txtTenSP.setBounds(60, 15, 120, 22); panelQLSanPham.add(txtTenSP);
		
		addLabel(panelQLSanPham, "Giá:", 200, 15, 30, 20);
		cbbGia = new JComboBox<>(new String[]{"Tất cả", "Dưới 500k", "Từ 500k - 1Tr", "Trên 1Tr"}); cbbGia.setBounds(230, 15, 110, 22); panelQLSanPham.add(cbbGia);
		
		addLabel(panelQLSanPham, "Thương hiệu:", 360, 15, 80, 20);
		cbbThuongHieu = new JComboBox<>(); cbbThuongHieu.setBounds(440, 15, 100, 22); panelQLSanPham.add(cbbThuongHieu);
		
		addLabel(panelQLSanPham, "Sắp xếp theo:", 560, 15, 80, 20);
		cbbSapXep = new JComboBox<>(new String[]{"Mặc định", "Giá tăng dần", "Giá giảm dần"}); cbbSapXep.setBounds(640, 15, 110, 22); panelQLSanPham.add(cbbSapXep);
		
		JButton btnTimKiem = new JButton("Tìm kiếm"); btnTimKiem.setBackground(new Color(255, 191, 0)); btnTimKiem.setBounds(340, 50, 100, 25); panelQLSanPham.add(btnTimKiem);

		// Bảng
		JScrollPane scrollPane = new JScrollPane(); scrollPane.setBounds(10, 125, 785, 350); panelQLSanPham.add(scrollPane);
		tblSanPham = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Mã SP", "Tên sản phẩm", "Loại", "Chất liệu", "Thương hiệu", "Tồn kho", "Giá", "Đã bán"}){
			public boolean isCellEditable(int row, int column) { return false; }
		});
		tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		styleTable(tblSanPham);
		scrollPane.setViewportView(tblSanPham);

		// Nút chức năng
		int yBtn = 495;
		JButton btnThem = new JButton("Thêm"); btnThem.setBackground(new Color(50, 205, 50)); btnThem.setForeground(Color.WHITE); btnThem.setBounds(120, yBtn, 90, 30); panelQLSanPham.add(btnThem);
		JButton btnChinhSua = new JButton("Chỉnh sửa"); btnChinhSua.setBackground(new Color(255, 191, 0)); btnChinhSua.setBounds(250, yBtn, 100, 30); panelQLSanPham.add(btnChinhSua);
		JButton btnXoa = new JButton("Xóa"); btnXoa.setBackground(Color.RED); btnXoa.setForeground(Color.WHITE); btnXoa.setBounds(390, yBtn, 90, 30); panelQLSanPham.add(btnXoa);
		JButton btnLamMoi = new JButton("Làm mới"); btnLamMoi.setBackground(Color.GRAY); btnLamMoi.setForeground(Color.WHITE); btnLamMoi.setBounds(520, yBtn, 90, 30); panelQLSanPham.add(btnLamMoi);

		// Các cart khác
		BanHangPanel panelQLTaiQuay = new BanHangPanel(true, null); panelCenter.add(panelQLTaiQuay, "CardTaiQuay");
		QuanLyHoaDonPanel panelQLHoaDon = new QuanLyHoaDonPanel(); panelCenter.add(panelQLHoaDon, "CardHoaDon");
		ThongKePanel panelThongKe = new ThongKePanel(); panelCenter.add(panelThongKe, "CardThongKe");

		// Xử lý sự kiện
		btnQLTaiQuay.addActionListener(e -> ((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardTaiQuay"));
		btnQLSanPham.addActionListener(e -> { ((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardSanPham");
										loadDataToTable(); });
		btnQLHoaDon.addActionListener(e -> { ((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardHoaDon");
										panelQLHoaDon.refreshData(); });
		btnThongKe.addActionListener(e -> { ((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardThongKe");
										panelThongKe.refreshData(); });
		btnDangXuat.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",	
												JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose(); new MainCustomerView(null).setVisible(true); 
			}
		});

		btnTimKiem.addActionListener(e -> thucHienTimKiem());
		btnLamMoi.addActionListener(e -> { txtTenSP.setText(""); cbbThuongHieu.setSelectedIndex(0);
											cbbGia.setSelectedIndex(0);
											cbbSapXep.setSelectedIndex(0);
											loadDataToTable(); });
		btnThem.addActionListener(e -> { SanPhamDialog dialog = new SanPhamDialog();
											dialog.setTitle("Thêm Sản Phẩm Mới");
											dialog.setVisible(true);
											loadDataToTable(); });
		btnChinhSua.addActionListener(e -> {
			int row = tblSanPham.getSelectedRow();
			if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa!"); return; }
			SanPhamDTO spDTO = new SanPhamDTO();
			spDTO.setMaSP(tblSanPham.getValueAt(row, 0).toString());
			spDTO.setTenSP(tblSanPham.getValueAt(row, 1).toString());
			spDTO.setTenLoai(tblSanPham.getValueAt(row, 2).toString());
			spDTO.setChatLieu(tblSanPham.getValueAt(row, 3).toString());
			spDTO.setTenThuongHieu(tblSanPham.getValueAt(row, 4).toString());
			spDTO.setSoLuongConLai(Integer.parseInt(tblSanPham.getValueAt(row, 5).toString()));
			spDTO.setGia(Double.parseDouble(tblSanPham.getValueAt(row, 6).toString()));

			SanPhamDialog dialog = new SanPhamDialog();
			dialog.setTitle("Chỉnh Sửa Sản Phẩm");
			dialog.setDuLieuSua(spDTO);
			dialog.setVisible(true);
			loadDataToTable();
		});
		btnXoa.addActionListener(e -> {
			int row = tblSanPham.getSelectedRow();
			if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!"); return; }
			String maSP = tblSanPham.getValueAt(row, 0).toString();
			if (JOptionPane.showConfirmDialog(this, "Xóa sản phẩm: " + maSP + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (spController.xoaSanPham(maSP)) { 
					JOptionPane.showMessageDialog(this, "Thành công!");
					loadDataToTable(); } 
				else { 
					JOptionPane.showMessageDialog(this, "Thất bại!"); 
				}
			}
		});

		loadDataToTable(); loadDataToComboBoxTimKiem();
	}

	private JButton createMenuBtn(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(40, 40, 40));
		btn.setFocusPainted(false);
		return btn;
	}
	private void addLabel(JPanel pnl, String text, int x, int y, int w, int h) {
		JLabel lbl = new JLabel(text); lbl.setForeground(Color.WHITE); lbl.setBounds(x, y, w, h); pnl.add(lbl);
	}
	private void styleTable(JTable tbl) {
		tbl.setBackground(new Color(40, 40, 40)); tbl.setForeground(Color.WHITE); tbl.setGridColor(Color.GRAY);
		tbl.getTableHeader().setBackground(new Color(255, 191, 0));
		tbl.getTableHeader().setForeground(Color.BLACK);
		tbl.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
	}
	private void loadDataToTable() { 
		fillTable(spController.layDanhSachSanPham()); 
		}
	private void thucHienTimKiem() { fillTable(spController.timKiemVaSapXep(txtTenSP.getText().trim(), cbbThuongHieu.getSelectedItem() != null ?
									cbbThuongHieu.getSelectedItem().toString() : "Tất cả", cbbGia.getSelectedIndex(),
									cbbSapXep.getSelectedIndex())); }
	private void fillTable(List<SanPhamDTO> list) {
		DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
		model.setRowCount(0); 
		for (SanPhamDTO sp : list) {
			model.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), sp.getTenLoai(), sp.getChatLieu(), sp.getTenThuongHieu(),
							sp.getSoLuongConLai(), sp.getGia(), sp.getSoLuongDaBan() }); }
	}
	private void loadDataToComboBoxTimKiem() {
		cbbThuongHieu.removeAllItems();
		cbbThuongHieu.addItem("Tất cả");
		for (String th : spController.layDanhSachTenTH()) {
			cbbThuongHieu.addItem(th); 
			} 
		}
}