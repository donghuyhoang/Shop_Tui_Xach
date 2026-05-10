package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class SanPhamDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaSP;
	private JTextField txtTenSP;
	private JTextField txtGia;
	private JTextField txtSoLuong;
	
	// Đưa JComboBox lên làm biến toàn cục để các hàm khác có thể gọi được
	private JComboBox<String> cbbLoai;
	private JComboBox<String> cbbThuongHieu;

	// Biến toàn cục quản lý trạng thái và kết nối Controller
	private String cheDo = "THEM"; 
	private controller.SanPhamController spController = new controller.SanPhamController();
	private JTextField txtChatLieu;

	public static void main(String[] args) {
		try {
			SanPhamDialog dialog = new SanPhamDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SanPhamDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Mã sản phẩm:");
		lblNewLabel_1.setBounds(47, 42, 87, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtMaSP = new JTextField();
		txtMaSP.setBounds(157, 39, 150, 20);
		contentPanel.add(txtMaSP);
		
		JLabel lblNewLabel = new JLabel("Tên sản phẩm:");
		lblNewLabel.setBounds(47, 67, 87, 14);
		contentPanel.add(lblNewLabel);
		
		txtTenSP = new JTextField();
		txtTenSP.setBounds(157, 64, 150, 20);
		contentPanel.add(txtTenSP);
		
		JLabel lblLoi = new JLabel("Loại:");
		lblLoi.setBounds(47, 92, 48, 14);
		contentPanel.add(lblLoi);
		
		// Khởi tạo JComboBox Loại
		cbbLoai = new JComboBox<String>();
		cbbLoai.setBounds(157, 88, 150, 22);
		contentPanel.add(cbbLoai);
		
		JLabel lblThngHiu = new JLabel("Thương hiệu:");
		lblThngHiu.setBounds(47, 145, 75, 14);
		contentPanel.add(lblThngHiu);
		
		// Khởi tạo JComboBox Thương hiệu
		cbbThuongHieu = new JComboBox<String>();
		cbbThuongHieu.setBounds(157, 141, 150, 22);
		contentPanel.add(cbbThuongHieu);
		
		JLabel lblSLngCn = new JLabel("Số lượng còn lại:");
		lblSLngCn.setBounds(47, 174, 96, 14);
		contentPanel.add(lblSLngCn);
		
		txtSoLuong = new JTextField();
		txtSoLuong.setBounds(157, 171, 150, 20);
		contentPanel.add(txtSoLuong);
		
		JLabel lblGi = new JLabel("Giá:");
		lblGi.setBounds(47, 202, 48, 14);
		contentPanel.add(lblGi);
		
		txtGia = new JTextField();
		txtGia.setBounds(157, 199, 150, 20);
		contentPanel.add(txtGia);
		
		JLabel lblNewLabel_2 = new JLabel("Chất liệu:");
		lblNewLabel_2.setBounds(47, 120, 48, 14);
		contentPanel.add(lblNewLabel_2);
		
		txtChatLieu = new JTextField();
		txtChatLieu.setBounds(157, 117, 150, 20);
		contentPanel.add(txtChatLieu);
		txtChatLieu.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				xulyLuuDuLieu();
			}
		});
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancel);
		
		// GỌI HÀM ĐỔ DỮ LIỆU LÊN COMBOBOX NGAY KHI MỞ DIALOG
		loadDataToComboBox();
	}

	// Hàm tự động load danh sách Loại và Thương hiệu từ DB lên ComboBox
	private void loadDataToComboBox() {
		cbbLoai.removeAllItems();
		cbbThuongHieu.removeAllItems();
		
		// Lấy danh sách tên và add vào
		for(String tenLoai : spController.layDanhSachTenLoai()) {
			cbbLoai.addItem(tenLoai);
		}
		for(String tenTH : spController.layDanhSachTenTH()) {
			cbbThuongHieu.addItem(tenTH);
		}
	}

	// Hàm set dữ liệu khi bấm nút "Chỉnh sửa" từ MainStaffView
	public void setDuLieuSua(dto.SanPhamDTO sp) {
		this.cheDo = "SUA"; 
		
		txtMaSP.setText(sp.getMaSP());
		txtMaSP.setEditable(false); // Khóa Mã SP không cho người dùng sửa
		
		txtTenSP.setText(sp.getTenSP());
		txtSoLuong.setText(String.valueOf(sp.getSoLuongConLai()));
		txtGia.setText(String.valueOf(sp.getGia()));
		txtChatLieu.setText(sp.getChatLieu());
		
		// Ép ComboBox chọn đúng mục tương ứng với dữ liệu cũ
		cbbLoai.setSelectedItem(sp.getTenLoai()); 
		cbbThuongHieu.setSelectedItem(sp.getTenThuongHieu());
	}

	// Hàm xử lý lưu xuống DB khi bấm OK
	private void xulyLuuDuLieu() {
		try {
			if(txtMaSP.getText().trim().isEmpty() || txtTenSP.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Mã và Tên sản phẩm không được để trống!");
				return;
			}

			// Lấy Tên Loại và Thương hiệu từ JComboBox
			String tenLoai = cbbLoai.getSelectedItem() != null ? cbbLoai.getSelectedItem().toString() : "";
			String tenTH = cbbThuongHieu.getSelectedItem() != null ? cbbThuongHieu.getSelectedItem().toString() : "";
			
			// XỬ LÝ NGHIỆP VỤ: Truy vấn ngược ra Mã từ Tên
			String maLoai = spController.layMaLoaiTheoTen(tenLoai);
			String maTH = spController.layMaTHTheoTen(tenTH);

			if (maLoai == null || maTH == null) {
				JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy Mã cho Loại hoặc Thương hiệu này trong DB!");
				return;
			}

			// Gom dữ liệu vào Entity
			entity.SanPhamEntity sp = new entity.SanPhamEntity();
			sp.setMaSP(txtMaSP.getText().trim()); 
			sp.setTenSP(txtTenSP.getText().trim());
			sp.setMaLoai(maLoai); // Set Mã để lưu DB
			sp.setMaTH(maTH);     // Set Mã để lưu DB
			sp.setSoLuongConLai(Integer.parseInt(txtSoLuong.getText().trim()));
			sp.setGia(Double.parseDouble(txtGia.getText().trim()));

			boolean thanhCong = false;

			// Gọi đúng hàm dựa trên trạng thái
			if (cheDo.equals("THEM")) {
				thanhCong = spController.themSanPham(sp);
			} else if (cheDo.equals("SUA")) {
				thanhCong = spController.suaSanPham(sp);
			}

			// Thông báo kết quả
			if (thanhCong) {
				JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!");
				this.dispose(); // Đóng Dialog
			} else {
				JOptionPane.showMessageDialog(this, "Lưu thất bại! Hãy kiểm tra Mã SP có bị trùng không.");
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi: Giá và Số lượng phải là các con số hợp lệ!");
		}
	}
}