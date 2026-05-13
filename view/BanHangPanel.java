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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.SanPhamController;
import dto.SanPhamDTO;

public class BanHangPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SanPhamController spController = new SanPhamController();

	private JTextField txtTenSP;
	private JComboBox<String> cbbGia;
	private JComboBox<String> cbbThuongHieu;
	private JComboBox<String> cbbSapXep;
	
	private JTable tblSanPham;
	private JTable tblGioHang;
	
	private JTextField txtMaND; 
	private JTextField txtDiaChi;
	private JLabel lblTongTien;
	private JButton btnThanhToan;

	public BanHangPanel(boolean isStaff) {
		setLayout(null);
		
		JPanel panelSanPham = new JPanel();
		panelSanPham.setBorder(BorderFactory.createTitledBorder("Danh sách Sản phẩm"));
		panelSanPham.setBounds(10, 10, 560, 260); 
		panelSanPham.setLayout(null);
		add(panelSanPham);
		
		JLabel lblTen = new JLabel("Tên:");
		lblTen.setBounds(10, 20, 35, 20);
		panelSanPham.add(lblTen);
		
		txtTenSP = new JTextField();
		txtTenSP.setBounds(45, 20, 100, 22);
		panelSanPham.add(txtTenSP);
		
		JLabel lblGia = new JLabel("Giá:");
		lblGia.setBounds(155, 20, 30, 20);
		panelSanPham.add(lblGia);
		
		cbbGia = new JComboBox<String>();
		cbbGia.addItem("Tất cả");
		cbbGia.addItem("Dưới 500k");
		cbbGia.addItem("Từ 500k - 1Tr");
		cbbGia.addItem("Trên 1Tr");
		cbbGia.setBounds(185, 20, 100, 22);
		panelSanPham.add(cbbGia);
		
		JLabel lblThuongHieu = new JLabel("Hiệu:");
		lblThuongHieu.setBounds(295, 20, 35, 20);
		panelSanPham.add(lblThuongHieu);
		
		cbbThuongHieu = new JComboBox<String>();
		cbbThuongHieu.setBounds(330, 20, 90, 22);
		panelSanPham.add(cbbThuongHieu);
		
		JLabel lblSapXep = new JLabel("Sắp xếp:");
		lblSapXep.setBounds(430, 20, 50, 20);
		panelSanPham.add(lblSapXep);
		
		cbbSapXep = new JComboBox<String>();
		cbbSapXep.addItem("Mặc định");
		cbbSapXep.addItem("Giá Tăng");
		cbbSapXep.addItem("Giá Giảm");
		cbbSapXep.setBounds(480, 20, 70, 22); 
		panelSanPham.add(cbbSapXep);
		
		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(230, 50, 100, 25);
		panelSanPham.add(btnTimKiem);

		JScrollPane scrollPaneSP = new JScrollPane();
		scrollPaneSP.setBounds(10, 85, 540, 160);
		panelSanPham.add(scrollPaneSP);
		
		tblSanPham = new JTable();
		tblSanPham.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP", "Tên SP", "Loại", "Thương hiệu", "Tồn kho", "Giá"}
		) {
			public boolean isCellEditable(int row, int column) { return false; } 
		});
		tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneSP.setViewportView(tblSanPham);

		JButton btnThemVaoGio = new JButton("Thêm vào Giỏ hàng (Double click)");
		btnThemVaoGio.setBounds(165, 280, 250, 30);
		add(btnThemVaoGio);

		JPanel panelGioHang = new JPanel();
		panelGioHang.setBorder(BorderFactory.createTitledBorder("Giỏ hàng của khách"));
		panelGioHang.setBounds(10, 320, 560, 210); 
		panelGioHang.setLayout(null);
		add(panelGioHang);
		
		JScrollPane scrollPaneGioHang = new JScrollPane();
		scrollPaneGioHang.setBounds(10, 25, 540, 140);
		panelGioHang.add(scrollPaneGioHang);
		
		tblGioHang = new JTable();
		tblGioHang.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền"}
		) {
			public boolean isCellEditable(int row, int column) { 
                return column == 3; 
            }
		});
		tblGioHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneGioHang.setViewportView(tblGioHang);
		
		JButton btnXoaKhoiGio = new JButton("Xóa khỏi giỏ");
		btnXoaKhoiGio.setBounds(440, 175, 110, 25);
		panelGioHang.add(btnXoaKhoiGio);

		JPanel panelThanhToan = new JPanel();
		panelThanhToan.setBorder(BorderFactory.createTitledBorder("Thông tin Thanh toán"));
		panelThanhToan.setBounds(580, 10, 200, 520);
		panelThanhToan.setLayout(null);
		add(panelThanhToan);
		
		int yPos = 30; 
		
		if (isStaff) {
			JLabel lblMaND = new JLabel("Mã ND:");
			lblMaND.setBounds(10, yPos, 50, 20);
			panelThanhToan.add(lblMaND);
			
			txtMaND = new JTextField();
			txtMaND.setBounds(60, yPos, 130, 22);
			panelThanhToan.add(txtMaND);
			yPos += 40;
		} else {
			JLabel lblDiaChi = new JLabel("Địa chỉ:");
			lblDiaChi.setBounds(10, yPos, 50, 20);
			panelThanhToan.add(lblDiaChi);
			
			txtDiaChi = new JTextField();
			txtDiaChi.setBounds(60, yPos, 130, 22);
			panelThanhToan.add(txtDiaChi);
			yPos += 40;
		}
		
		JPanel line = new JPanel();
		line.setBackground(Color.LIGHT_GRAY);
		line.setBounds(10, yPos, 180, 2);
		panelThanhToan.add(line);
		yPos += 20;

		JLabel lblTitleTong = new JLabel("TỔNG TIỀN:");
		lblTitleTong.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitleTong.setBounds(10, yPos, 100, 25);
		panelThanhToan.add(lblTitleTong);
		yPos += 30;
		
		lblTongTien = new JLabel("0 VNĐ");
		lblTongTien.setForeground(Color.RED);
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

		loadDataToComboBoxTimKiem();
		loadDataToTableSanPham();
		
		btnTimKiem.addActionListener(e -> thucHienTimKiem());
		
		btnThemVaoGio.addActionListener(e -> themVaoGioHang());
		tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) { 
					themVaoGioHang();
				}
			}
		});

		btnXoaKhoiGio.addActionListener(e -> {
			int row = tblGioHang.getSelectedRow();
			if (row >= 0) {
				((DefaultTableModel) tblGioHang.getModel()).removeRow(row);
				tinhTongTien(); 
			} else {
				javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm trong giỏ để xóa!");
			}
		});

		tblGioHang.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
			public void tableChanged(javax.swing.event.TableModelEvent e) {
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
			}
		});

		btnThanhToan.addActionListener(e -> {
			DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
			if (cartModel.getRowCount() == 0) {
				javax.swing.JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống, không thể thanh toán!");
				return;
			}
			
			String maND = txtMaND != null ? txtMaND.getText().trim() : "";
			String tongTienStr = lblTongTien.getText().replace(",", "").replace(" VNĐ", "");
			double tongTien = Double.parseDouble(tongTienStr);
			
			HoaDonDialog dialog = new HoaDonDialog(cartModel, maND, lblTongTien.getText());
			dialog.setVisible(true);
			
			if (dialog.isConfirmed()) {
				entity.HoaDonEntity hd = new entity.HoaDonEntity();
				hd.setMaND(maND); 
				hd.setTongTien(tongTien);
				
				// ==========================================
				// ĐÃ CẬP NHẬT TRẠNG THÁI VÀO ĐÂY
				// ==========================================
				if (isStaff) {
					hd.setVaiTro("Mua tại quầy");
					hd.setTrangThai("Đã thanh toán"); 
				} else {
					hd.setVaiTro("Đặt online");
					hd.setTrangThai("Chờ duyệt"); 
				}
				// ==========================================
				
				java.util.List<entity.ChiTietHoaDonEntity> dsChiTiet = new java.util.ArrayList<>();
				for (int i = 0; i < cartModel.getRowCount(); i++) {
					entity.ChiTietHoaDonEntity ct = new entity.ChiTietHoaDonEntity();
					ct.setMaSP(cartModel.getValueAt(i, 0).toString());
					ct.setDonGia(Double.parseDouble(cartModel.getValueAt(i, 2).toString()));
					ct.setSoLuong(Integer.parseInt(cartModel.getValueAt(i, 3).toString()));
					ct.setThanhTien(Double.parseDouble(cartModel.getValueAt(i, 4).toString()));
					dsChiTiet.add(ct);
				}
				
				controller.HoaDonController hdController = new controller.HoaDonController();
				boolean ketQua = hdController.thanhToan(hd, dsChiTiet);
				
				if (ketQua) {
					javax.swing.JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
					cartModel.setRowCount(0); 
					lblTongTien.setText("0 VNĐ");
					if(isStaff) {
						txtMaND.setText("");
					}
					loadDataToTableSanPham(); 
				} else {
					javax.swing.JOptionPane.showMessageDialog(this, "Lỗi hệ thống! Thanh toán thất bại.");
				}
			}
		});
	}

	private void themVaoGioHang() {
		int row = tblSanPham.getSelectedRow();
		if (row < 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm từ danh sách phía trên!");
			return;
		}

		String maSP = tblSanPham.getValueAt(row, 0).toString();
		String tenSP = tblSanPham.getValueAt(row, 1).toString();
		double gia = Double.parseDouble(tblSanPham.getValueAt(row, 5).toString());
		int tonKho = Integer.parseInt(tblSanPham.getValueAt(row, 4).toString());

		if (tonKho <= 0) {
			javax.swing.JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng!");
			return;
		}

		DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();

		for (int i = 0; i < cartModel.getRowCount(); i++) {
			if (cartModel.getValueAt(i, 0).toString().equals(maSP)) {
				int slHienTai = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
				if (slHienTai < tonKho) {
					cartModel.setValueAt(slHienTai + 1, i, 3);
				} else {
					javax.swing.JOptionPane.showMessageDialog(this, "Không đủ số lượng tồn kho!");
				}
				return;
			}
		}

		cartModel.addRow(new Object[] { maSP, tenSP, gia, 1, gia });
		tinhTongTien();
	}

	private void tinhTongTien() {
		DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
		double tong = 0;
		for (int i = 0; i < cartModel.getRowCount(); i++) {
			tong += Double.parseDouble(cartModel.getValueAt(i, 4).toString());
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
		lblTongTien.setText(df.format(tong) + " VNĐ");
	}

	private void loadDataToComboBoxTimKiem() {
		cbbThuongHieu.removeAllItems();
		cbbThuongHieu.addItem("Tất cả");
		for (String th : spController.layDanhSachTenTH()) {
			cbbThuongHieu.addItem(th);
		}
	}

	private void loadDataToTableSanPham() {
		List<SanPhamDTO> list = spController.layDanhSachSanPham();
		fillTableSanPham(list);
	}

	private void thucHienTimKiem() {
		String ten = txtTenSP.getText().trim();
		String thuongHieu = cbbThuongHieu.getSelectedItem() != null ? cbbThuongHieu.getSelectedItem().toString() : "Tất cả";
		int giaIndex = cbbGia.getSelectedIndex();
		int sortIndex = cbbSapXep.getSelectedIndex();

		List<SanPhamDTO> list = spController.timKiemVaSapXep(ten, thuongHieu, giaIndex, sortIndex);
		fillTableSanPham(list);
	}

	private void fillTableSanPham(List<SanPhamDTO> list) {
		DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
		model.setRowCount(0); 
		for (SanPhamDTO sp : list) {
			model.addRow(new Object[] {
				sp.getMaSP(), 
				sp.getTenSP(), 
				sp.getTenLoai(), 
				sp.getTenThuongHieu(), 
				sp.getSoLuongConLai(), 
				sp.getGia()
			});
		}
	}
}