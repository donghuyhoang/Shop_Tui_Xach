package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.SanPhamController;
import dto.SanPhamDTO;

public class MainStaffView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTenSP;
	private JTable tblSanPham;
	
	// BIẾN TOÀN CỤC CHO PHẦN TÌM KIẾM & CONTROLLER
	private JComboBox<String> cbbThuongHieu;
	private JComboBox<String> cbbGia;
	private JComboBox<String> cbbSapXep;
	private SanPhamController spController = new SanPhamController();
	
	// Panel chứa các trang (CardLayout)
	private JPanel panelCenter;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainStaffView frame = new MainStaffView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainStaffView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600); // Mở rộng size một chút cho thoáng
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// ================= MENU BÊN TRÁI =================
		JPanel panelMenu = new JPanel();
		contentPane.add(panelMenu, BorderLayout.WEST);
		panelMenu.setLayout(new GridLayout(6, 1, 0, 0));
		
		JButton btnQLTaiQuay = new JButton("Quản lý tại quầy");
		panelMenu.add(btnQLTaiQuay);
		
		JButton btnQLSanPham = new JButton("Quản lý sản phẩm");
		panelMenu.add(btnQLSanPham);
		
		JButton btnQLHoaDon = new JButton("Quản lý hóa đơn");
		panelMenu.add(btnQLHoaDon);
		
		JButton btnThongKe = new JButton("Thống kê doanh thu");
		panelMenu.add(btnThongKe);
		
		JButton btnDangXuat = new JButton("Đăng xuất");
		panelMenu.add(btnDangXuat);

		// ================= KHU VỰC TRUNG TÂM (CARD LAYOUT) =================
		panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new CardLayout(0, 0));
		
		// --- CARD 1: Quản lý Sản Phẩm ---
		JPanel panelQLSanPham = new JPanel();
		panelQLSanPham.setBackground(Color.YELLOW);
		panelQLSanPham.setLayout(null); // Dùng Absolute Layout cho dễ căn chỉnh
		panelCenter.add(panelQLSanPham, "CardSanPham");
		
		// [PHẦN TÌM KIẾM]
		JLabel lblTen = new JLabel("Tên:");
		lblTen.setBounds(20, 15, 40, 20);
		panelQLSanPham.add(lblTen);
		
		txtTenSP = new JTextField();
		txtTenSP.setBounds(60, 15, 120, 22);
		panelQLSanPham.add(txtTenSP);
		
		JLabel lblGia = new JLabel("Giá:");
		lblGia.setBounds(200, 15, 30, 20);
		panelQLSanPham.add(lblGia);
		
		cbbGia = new JComboBox<String>();
		cbbGia.addItem("Tất cả");
		cbbGia.addItem("Dưới 500k");
		cbbGia.addItem("Từ 500k - 1Tr");
		cbbGia.addItem("Trên 1Tr");
		cbbGia.setBounds(230, 15, 110, 22);
		panelQLSanPham.add(cbbGia);
		
		JLabel lblThuongHieu = new JLabel("Thương hiệu:");
		lblThuongHieu.setBounds(360, 15, 80, 20);
		panelQLSanPham.add(lblThuongHieu);
		
		cbbThuongHieu = new JComboBox<String>();
		cbbThuongHieu.setBounds(440, 15, 100, 22);
		panelQLSanPham.add(cbbThuongHieu);
		
		JLabel lblSapXep = new JLabel("Sắp xếp theo:");
		lblSapXep.setBounds(560, 15, 80, 20);
		panelQLSanPham.add(lblSapXep);
		
		cbbSapXep = new JComboBox<String>();
		cbbSapXep.addItem("Mặc định");
		cbbSapXep.addItem("Giá tăng dần");
		cbbSapXep.addItem("Giá giảm dần");
		cbbSapXep.setBounds(640, 15, 110, 22);
		panelQLSanPham.add(cbbSapXep);
		
		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(340, 50, 100, 25);
		panelQLSanPham.add(btnTimKiem);

		// [PHẦN BẢNG DỮ LIỆU]
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 125, 795, 350);
		panelQLSanPham.add(scrollPane);
		
		tblSanPham = new JTable();
		// KHÓA BẢNG: Không cho phép sửa trực tiếp trên ô
		tblSanPham.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 s\u1EA3n ph\u1EA9m", "T\u00EAn s\u1EA3n ph\u1EA9m", "Lo\u1EA1i", "Ch\u1EA5t li\u1EC7u", "Th\u01B0\u01A1ng hi\u1EC7u", "S\u1ED1 l\u01B0\u1EE3ng c\u00F2n l\u1EA1i", "Gi\u00E1", "S\u1ED1 l\u01B0\u1EE3ng \u0111\u00E3 b\u00E1n"
			}
		));
		tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
		tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(100);
		tblSanPham.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblSanPham.getColumnModel().getColumn(7).setPreferredWidth(100);
		// CẤU HÌNH CLICK CHỌN NGUYÊN DÒNG
		tblSanPham.setRowSelectionAllowed(true);
		tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Set kích thước cột
		tblSanPham.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblSanPham.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblSanPham);

		// [PHẦN NÚT CHỨC NĂNG]
		JButton btnThem = new JButton("Thêm");
		btnThem.setBounds(120, 495, 90, 30);
		panelQLSanPham.add(btnThem);
		
		JButton btnChinhSua = new JButton("Chỉnh sửa");
		btnChinhSua.setBounds(250, 495, 100, 30);
		panelQLSanPham.add(btnChinhSua);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBounds(390, 495, 90, 30);
		panelQLSanPham.add(btnXoa);
		
		JButton btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBounds(520, 495, 90, 30);
		panelQLSanPham.add(btnLamMoi);

		// --- CARD 2: Các panel khác (Giả lập) ---
		JPanel panelQLTaiQuay = new JPanel();
		panelQLTaiQuay.setBackground(Color.GREEN);
		panelCenter.add(panelQLTaiQuay, "CardTaiQuay");
		
		JPanel panelQLHoaDon = new JPanel();
		panelQLHoaDon.setBackground(Color.CYAN);
		panelCenter.add(panelQLHoaDon, "CardHoaDon");
		
		JPanel panelThongKe = new JPanel();
		panelThongKe.setBackground(Color.PINK);
		panelCenter.add(panelThongKe, "CardThongKe");


		// ================= XỬ LÝ SỰ KIỆN (ACTION LISTENERS) =================

		// 1. CHUYỂN TRANG
		btnQLTaiQuay.addActionListener(e -> {
			((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardTaiQuay");
		});
		
		btnQLSanPham.addActionListener(e -> {
			((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardSanPham");
			loadDataToTable(); // Load lại dữ liệu mỗi khi vào trang này
		});
		
		btnQLHoaDon.addActionListener(e -> {
			((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardHoaDon");
		});

		btnThongKe.addActionListener(e -> {
			((CardLayout) panelCenter.getLayout()).show(panelCenter, "CardThongKe");
		});

		// 2. TÌM KIẾM
		btnTimKiem.addActionListener(e -> thucHienTimKiem());

		// 3. LÀM MỚI
		btnLamMoi.addActionListener(e -> {
			txtTenSP.setText("");
			cbbThuongHieu.setSelectedIndex(0);
			cbbGia.setSelectedIndex(0);
			cbbSapXep.setSelectedIndex(0);
			loadDataToTable(); // Trả lại bảng gốc
		});

		// 4. THÊM SẢN PHẨM
		btnThem.addActionListener(e -> {
			SanPhamDialog dialog = new SanPhamDialog();
			dialog.setTitle("Thêm Sản Phẩm Mới");
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			loadDataToTable(); // Reload bảng sau khi tắt Dialog
		});

		// 5. SỬA SẢN PHẨM
		btnChinhSua.addActionListener(e -> {
			int row = tblSanPham.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng click chọn 1 dòng trên bảng để sửa!");
				return;
			}
			
			// Đổ dữ liệu từ bảng vào DTO
			SanPhamDTO spDTO = new SanPhamDTO();
			spDTO.setMaSP(tblSanPham.getValueAt(row, 0).toString());
			spDTO.setTenSP(tblSanPham.getValueAt(row, 1).toString());
			spDTO.setTenLoai(tblSanPham.getValueAt(row, 2).toString());
			spDTO.setChatLieu(tblSanPham.getValueAt(row, 3).toString());
			spDTO.setTenThuongHieu(tblSanPham.getValueAt(row, 4).toString());
			spDTO.setSoLuongConLai(Integer.parseInt(tblSanPham.getValueAt(row, 5).toString()));
			spDTO.setGia(Double.parseDouble(tblSanPham.getValueAt(row, 6).toString()));

			// Mở Dialog và truyền dữ liệu
			SanPhamDialog dialog = new SanPhamDialog();
			dialog.setTitle("Chỉnh Sửa Sản Phẩm");
			dialog.setDuLieuSua(spDTO);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			
			loadDataToTable(); // Reload bảng sau khi tắt Dialog
		});

		// 6. XÓA SẢN PHẨM
		btnXoa.addActionListener(e -> {
			int row = tblSanPham.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
				return;
			}
			String maSP = tblSanPham.getValueAt(row, 0).toString();
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm mã: " + maSP + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
			
			if (confirm == JOptionPane.YES_OPTION) {
				if (spController.xoaSanPham(maSP)) {
					JOptionPane.showMessageDialog(this, "Xóa thành công!");
					loadDataToTable();
				} else {
					JOptionPane.showMessageDialog(this, "Xóa thất bại! Vui lòng thử lại.");
				}
			}
		});

		// ================= KHỞI TẠO DỮ LIỆU BAN ĐẦU =================
		loadDataToTable();
		loadDataToComboBoxTimKiem();
	}

	// ================= CÁC HÀM BỔ TRỢ (HELPER METHODS) =================

	// Hàm gọi Controller lấy toàn bộ danh sách
	private void loadDataToTable() {
		List<SanPhamDTO> list = spController.layDanhSachSanPham();
		fillTable(list);
	}

	private void thucHienTimKiem() {
		String ten = txtTenSP.getText().trim();
		String thuongHieu = cbbThuongHieu.getSelectedItem() != null ? cbbThuongHieu.getSelectedItem().toString() : "Tất cả";
		int giaIndex = cbbGia.getSelectedIndex();
		int sortIndex = cbbSapXep.getSelectedIndex();

		List<SanPhamDTO> list = spController.timKiemVaSapXep(ten, thuongHieu, giaIndex, sortIndex);
		fillTable(list);
	}

	private void fillTable(List<SanPhamDTO> list) {
		DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
		model.setRowCount(0); // Xóa sạch bảng cũ
		
		for (SanPhamDTO sp : list) {
			model.addRow(new Object[] {
				sp.getMaSP(), 
				sp.getTenSP(), 
				sp.getTenLoai(), 
				sp.getChatLieu(),
				sp.getTenThuongHieu(), 
				sp.getSoLuongConLai(), 
				sp.getGia(), 
				sp.getSoLuongDaBan()
			});
		}
	}

	// Hàm load Tên Thương Hiệu từ CSDL lên ô ComboBox tìm kiếm
	private void loadDataToComboBoxTimKiem() {
		cbbThuongHieu.removeAllItems();
		cbbThuongHieu.addItem("Tất cả"); // Thêm mục All lên đỉnh
		
		List<String> listTH = spController.layDanhSachTenTH();
		for (String th : listTH) {
			cbbThuongHieu.addItem(th);
		}
	}
}