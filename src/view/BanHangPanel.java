package view;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

import controller.SanPhamController;
import dto.SanPhamDTO;
import entity.NguoiDungEntity;

public class BanHangPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private SanPhamController spController = new SanPhamController();

	private JTextField txtTenSP;
	private JComboBox<String> cbbGia, cbbThuongHieu, cbbSapXep;
	private JTable tblSanPham, tblGioHang;
	private JTextField txtMaND, txtDiaChi;
	private JLabel lblTongTien;
	private JButton btnThanhToan;
	private NguoiDungEntity khachHangDangNhap;
	private boolean isStaff;

	public BanHangPanel(boolean isStaff, NguoiDungEntity user) {
		this.isStaff = isStaff;
		this.khachHangDangNhap = user;
		setLayout(null);
		setBackground(new Color(24, 24, 24));

		// 1. Panel sản phẩm
		JPanel panelSanPham = new JPanel(null);
		panelSanPham.setBackground(new Color(33, 33, 33));
		panelSanPham.setBorder(BorderFactory.createTitledBorder(null, "Danh sách Sản phẩm", TitledBorder.DEFAULT_JUSTIFICATION,
																TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.BOLD, 12)
																, Color.WHITE));
		panelSanPham.setBounds(10, 10, 560, 260); 
		add(panelSanPham);
		
		addLabel(panelSanPham, "Tên:", 10, 20, 35, 20);
		txtTenSP = new JTextField(); txtTenSP.setBounds(45, 20, 100, 22);
		panelSanPham.add(txtTenSP);
		
		addLabel(panelSanPham, "Giá:", 155, 20, 30, 20);
		cbbGia = new JComboBox<>(new String[]{"Tất cả", "Dưới 500k", "Từ 500k - 1Tr", "Trên 1Tr"});
		cbbGia.setBounds(185, 20, 100, 22);
		panelSanPham.add(cbbGia);
		
		addLabel(panelSanPham, "Hiệu:", 295, 20, 35, 20);
		cbbThuongHieu = new JComboBox<>();
		cbbThuongHieu.setBounds(330, 20, 90, 22);
		panelSanPham.add(cbbThuongHieu);
		
		addLabel(panelSanPham, "Sắp xếp:", 430, 20, 50, 20);
		cbbSapXep = new JComboBox<>(new String[]{"Mặc định", "Giá Tăng", "Giá Giảm"});
		cbbSapXep.setBounds(480, 20, 70, 22);
		panelSanPham.add(cbbSapXep);
		
		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBackground(new Color(255, 191, 0));
		btnTimKiem.setBounds(230, 50, 100, 25);
		panelSanPham.add(btnTimKiem);

		JScrollPane scrollPaneSP = new JScrollPane();
		scrollPaneSP.setBounds(10, 85, 540, 160);
		panelSanPham.add(scrollPaneSP);
		tblSanPham = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Mã SP", "Tên SP", "Loại", "Thương hiệu", 
																						"Tồn kho", "Giá"}) {
			public boolean isCellEditable(int row, int column) { 
				return false; 
				} 
		});
		tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		styleTable(tblSanPham);
		scrollPaneSP.setViewportView(tblSanPham);

		JButton btnThemVaoGio = new JButton("Thêm vào Giỏ hàng (Double click)");
		btnThemVaoGio.setBackground(new Color(255, 191, 0));
		btnThemVaoGio.setBounds(165, 280, 250, 30); 
		add(btnThemVaoGio);

		// 2. Panel giỏ hàng
		JPanel panelGioHang = new JPanel(null);
		panelGioHang.setBackground(new Color(33, 33, 33));
		panelGioHang.setBorder(BorderFactory.createTitledBorder(null, "Giỏ hàng của khách", TitledBorder.DEFAULT_JUSTIFICATION,
																TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.BOLD, 12), 
																Color.WHITE));
		panelGioHang.setBounds(10, 320, 560, 210);
		add(panelGioHang);
		
		JScrollPane scrollPaneGioHang = new JScrollPane();
		scrollPaneGioHang.setBounds(10, 25, 540, 140);
		panelGioHang.add(scrollPaneGioHang);
		tblGioHang = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền"}) {
			public boolean isCellEditable(int row, int column) { 
				return column == 3; 
				}
		});
		tblGioHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		styleTable(tblGioHang);
		scrollPaneGioHang.setViewportView(tblGioHang);
		
		JButton btnXoaKhoiGio = new JButton("Xóa khỏi giỏ");
		btnXoaKhoiGio.setBackground(Color.RED);
		btnXoaKhoiGio.setForeground(Color.WHITE);
		btnXoaKhoiGio.setBounds(440, 175, 110, 25);
		panelGioHang.add(btnXoaKhoiGio);

		// 3. Panel thanh toán
		JPanel panelThanhToan = new JPanel(null);
		panelThanhToan.setBackground(new Color(33, 33, 33));
		panelThanhToan.setBorder(BorderFactory.createTitledBorder(null, "Thông tin Thanh toán", TitledBorder.DEFAULT_JUSTIFICATION, 
																	TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.BOLD, 12), 
																	Color.WHITE));
		panelThanhToan.setBounds(580, 10, 200, 520); 
		add(panelThanhToan);
		
		int yPos = 30; 
		if (isStaff) {
			addLabel(panelThanhToan, "Mã ND:", 10, yPos, 50, 20);
			txtMaND = new JTextField();
			txtMaND.setBounds(60, yPos, 130, 22);
			panelThanhToan.add(txtMaND);
			yPos += 40;
		} else {
			addLabel(panelThanhToan, "Địa chỉ:", 10, yPos, 50, 20);
			txtDiaChi = new JTextField();
			txtDiaChi.setBounds(60, yPos, 130, 22);
			panelThanhToan.add(txtDiaChi);
			yPos += 40;
		}
		
		JPanel line = new JPanel();
		line.setBackground(Color.GRAY);
		line.setBounds(10, yPos, 180, 2);
		panelThanhToan.add(line);
		yPos += 20;

		JLabel lblTitleTong = new JLabel("TỔNG TIỀN:");
		lblTitleTong.setForeground(Color.WHITE);
		lblTitleTong.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitleTong.setBounds(10, yPos, 100, 25);
		panelThanhToan.add(lblTitleTong);
		yPos += 30;
		
		lblTongTien = new JLabel("0 VNĐ");
		lblTongTien.setForeground(new Color(255, 191, 0));
		lblTongTien.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTongTien.setHorizontalAlignment(SwingConstants.CENTER);
		lblTongTien.setBounds(10, yPos, 180, 30);
		panelThanhToan.add(lblTongTien);
		yPos += 50;
		
		btnThanhToan = new JButton(isStaff ? "THANH TOÁN" : "ĐẶT HÀNG");
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnThanhToan.setBackground(new Color(50, 205, 50));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setBounds(10, yPos, 180, 45);
		panelThanhToan.add(btnThanhToan);

		// Gọi hàm & xử lý sự kiện
		loadDataToComboBoxTimKiem();
		loadDataToTableSanPham();
		
		btnTimKiem.addActionListener(e -> thucHienTimKiem());
		btnThemVaoGio.addActionListener(e -> themVaoGioHang());
		tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) { 
				if (e.getClickCount() == 2) themVaoGioHang(); 
				}
		});
		btnXoaKhoiGio.addActionListener(e -> {
			int row = tblGioHang.getSelectedRow();
			if (row >= 0) { ((DefaultTableModel) tblGioHang.getModel()).removeRow(row); tinhTongTien(); } 
			else { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm trong giỏ để xóa!"); }
		});
		tblGioHang.getModel().addTableModelListener(e -> {
			if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 3) {
				int row = e.getFirstRow();
				DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
				try {
					int soLuong = Integer.parseInt(model.getValueAt(row, 3).toString());
					double donGia = Double.parseDouble(model.getValueAt(row, 2).toString());
					model.setValueAt(soLuong * donGia, row, 4);
					tinhTongTien();
				} catch (Exception ex) {
					model.setValueAt(1, row, 3); 
					}
			}
		});

		btnThanhToan.addActionListener(e -> {
			if (!this.isStaff && khachHangDangNhap == null) {
				int choice = JOptionPane.showConfirmDialog(this, "Bạn cần Đăng nhập tài khoản để đặt hàng. Mở bảng Đăng nhập ngay?", "Yêu cầu",
															JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					Login loginDlg = new Login();
					loginDlg.setVisible(true); 
					NguoiDungEntity userLogin = loginDlg.getLoggedInUser();
					if (userLogin != null) {
						this.khachHangDangNhap = userLogin; 
						try { MainCustomerView parent = (MainCustomerView) javax.swing.SwingUtilities.getWindowAncestor(this);
						parent.refreshMenu(userLogin); }
						
						catch(Exception ex) {}
					} else { 
						return; 
						}
				} else { 
					return; 
				}						
			}
			DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
			if (cartModel.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống, không thể thanh toán!"); return; }
			
			String maND = this.isStaff ? txtMaND.getText().trim() : khachHangDangNhap.getMaND();
			String tongTienStr = lblTongTien.getText().replace(",", "").replace(" VNĐ", "");
			double tongTien = Double.parseDouble(tongTienStr);
			
			HoaDonDialog dialog = new HoaDonDialog(cartModel, maND, lblTongTien.getText());
			dialog.setVisible(true);
			
			if (dialog.isConfirmed()) {
				entity.HoaDonEntity hd = new entity.HoaDonEntity();
				hd.setMaND(maND);
				hd.setTongTien(tongTien);
				if (this.isStaff) { 
					hd.setVaiTro("Mua tại quầy");
					hd.setTrangThai("Đã thanh toán"); 
					} 
				else { 
					hd.setVaiTro("Đặt online");
					hd.setTrangThai("Chờ duyệt"); 
					}
				
				java.util.List<entity.ChiTietHoaDonEntity> dsChiTiet = new java.util.ArrayList<>();
				for (int i = 0; i < cartModel.getRowCount(); i++) {
					entity.ChiTietHoaDonEntity ct = new entity.ChiTietHoaDonEntity();
					ct.setMaSP(cartModel.getValueAt(i, 0).toString());
					ct.setDonGia(Double.parseDouble(cartModel.getValueAt(i, 2).toString()));
					ct.setSoLuong(Integer.parseInt(cartModel.getValueAt(i, 3).toString()));
					ct.setThanhTien(Double.parseDouble(cartModel.getValueAt(i, 4).toString()));
					dsChiTiet.add(ct);
				}
				
				if (new controller.HoaDonController().thanhToan(hd, dsChiTiet)) {
					JOptionPane.showMessageDialog(this, "Thành công!");
					cartModel.setRowCount(0); 
					lblTongTien.setText("0 VNĐ");
					if(this.isStaff) { 
						txtMaND.setText("");
						} 
					loadDataToTableSanPham(); 
				} else { 
					JOptionPane.showMessageDialog(this, "Lỗi hệ thống! Thất bại."); 
					}
			}
		});
	}

	private void addLabel(JPanel pnl, String text, int x, int y, int w, int h) {
		JLabel lbl = new JLabel(text); lbl.setForeground(Color.WHITE);
		lbl.setBounds(x, y, w, h); pnl.add(lbl);
	}

	private void styleTable(JTable tbl) {
		tbl.setBackground(new Color(40, 40, 40));
		tbl.setForeground(Color.WHITE);
		tbl.setGridColor(Color.GRAY);
		tbl.getTableHeader().setBackground(new Color(255, 191, 0));
		tbl.getTableHeader().setForeground(Color.BLACK);
		tbl.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
	}

	private void themVaoGioHang() {
		int row = tblSanPham.getSelectedRow();
		if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm!"); return; }
		String maSP = tblSanPham.getValueAt(row, 0).toString();
		String tenSP = tblSanPham.getValueAt(row, 1).toString();
		double gia = Double.parseDouble(tblSanPham.getValueAt(row, 5).toString());
		int tonKho = Integer.parseInt(tblSanPham.getValueAt(row, 4).toString());
		if (tonKho <= 0) { JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng!"); return; }
		DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			if (cartModel.getValueAt(i, 0).toString().equals(maSP)) {
				int slHienTai = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
				if (slHienTai < tonKho) { cartModel.setValueAt(slHienTai + 1, i, 3); } 
				else { JOptionPane.showMessageDialog(this, "Không đủ số lượng tồn kho!"); } return;
			}
		}
		cartModel.addRow(new Object[] { maSP, tenSP, gia, 1, gia });
		tinhTongTien();
	}
	private void tinhTongTien() {
		DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel(); double tong = 0;
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			tong += Double.parseDouble(cartModel.getValueAt(i, 4).toString()); 
			}
		lblTongTien.setText(new java.text.DecimalFormat("#,###").format(tong) + " VNĐ");
	}
	private void loadDataToComboBoxTimKiem() {
		cbbThuongHieu.removeAllItems();
		cbbThuongHieu.addItem("Tất cả");
		for (String th : spController.layDanhSachTenTH()) { 
			cbbThuongHieu.addItem(th); 
			}
	}
	private void loadDataToTableSanPham() { fillTableSanPham(spController.layDanhSachSanPham()); }
	private void thucHienTimKiem() {
		fillTableSanPham(spController.timKiemVaSapXep(txtTenSP.getText().trim(), cbbThuongHieu.getSelectedItem() != null ? 
														cbbThuongHieu.getSelectedItem().toString() : "Tất cả", cbbGia.getSelectedIndex(),
														cbbSapXep.getSelectedIndex()));
	}
	private void fillTableSanPham(List<SanPhamDTO> list) {
		DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
		model.setRowCount(0); 
		for (SanPhamDTO sp : list) { 
			model.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), sp.getTenLoai(), sp.getTenThuongHieu(), sp.getSoLuongConLai(), sp.getGia() }); }
	}
}