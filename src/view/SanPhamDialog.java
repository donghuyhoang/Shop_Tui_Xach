package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SanPhamDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaSP, txtTenSP, txtGia, txtSoLuong, txtChatLieu;
	private JComboBox<String> cbbLoai, cbbThuongHieu;
	
	private String cheDo = "THEM"; 
	private controller.SanPhamController spController = new controller.SanPhamController();

	public SanPhamDialog() {
		setBounds(100, 100, 400, 350);
		setModal(true);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(24, 24, 24));
		
		contentPanel.setBackground(new Color(24, 24, 24));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		// Tiêu đề form
		JLabel lblTitle = new JLabel("THÔNG TIN SẢN PHẨM", SwingConstants.CENTER);
		lblTitle.setForeground(new Color(255, 191, 0));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(0, 10, 400, 25);
		contentPanel.add(lblTitle);

		addLabel("Mã sản phẩm:", 40, 50, 100, 20);
		txtMaSP = new JTextField();
		txtMaSP.setBounds(150, 50, 180, 22);
		contentPanel.add(txtMaSP);
		addLabel("Tên sản phẩm:", 40, 80, 100, 20);
		txtTenSP = new JTextField();
		txtTenSP.setBounds(150, 80, 180, 22);
		contentPanel.add(txtTenSP);
		addLabel("Loại:", 40, 110, 100, 20);
		cbbLoai = new JComboBox<>(); 
		cbbLoai.setBounds(150, 110, 180, 22);
		contentPanel.add(cbbLoai);
		addLabel("Chất liệu:", 40, 140, 100, 20); 
		txtChatLieu = new JTextField();
		txtChatLieu.setBounds(150, 140, 180, 22);
		contentPanel.add(txtChatLieu);
		addLabel("Thương hiệu:", 40, 170, 100, 20);
		cbbThuongHieu = new JComboBox<>();
		cbbThuongHieu.setBounds(150, 170, 180, 22);
		contentPanel.add(cbbThuongHieu);
		addLabel("Số lượng:", 40, 200, 100, 20);
		txtSoLuong = new JTextField();
		txtSoLuong.setBounds(150, 200, 180, 22);
		contentPanel.add(txtSoLuong);
		addLabel("Giá (VNĐ):", 40, 230, 100, 20);
		txtGia = new JTextField();
		txtGia.setBounds(150, 230, 180, 22);
		contentPanel.add(txtGia);
		
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPane.setBackground(new Color(24, 24, 24));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("LƯU THÔNG TIN");
		btnOk.setBackground(new Color(50, 205, 50));
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnOk.addActionListener(e -> xulyLuuDuLieu());
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);
		
		JButton btnCancel = new JButton("HỦY");
		btnCancel.setBackground(Color.GRAY);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancel.addActionListener(e -> dispose());
		buttonPane.add(btnCancel);
		
		loadDataToComboBox();
	}

	private void addLabel(String text, int x, int y, int w, int h) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(Color.WHITE);
		lbl.setBounds(x, y, w, h);
		contentPanel.add(lbl);
	}

	private void loadDataToComboBox() {
		cbbLoai.removeAllItems();
		cbbThuongHieu.removeAllItems();
		for(String t : spController.layDanhSachTenLoai()) 
			cbbLoai.addItem(t);
		for(String t : spController.layDanhSachTenTH()) 
			cbbThuongHieu.addItem(t);
	}

	public void setDuLieuSua(dto.SanPhamDTO sp) {
		this.cheDo = "SUA"; 
		txtMaSP.setText(sp.getMaSP()); 
		txtMaSP.setEditable(false); 
		txtTenSP.setText(sp.getTenSP());
		txtSoLuong.setText(String.valueOf(sp.getSoLuongConLai()));
		txtGia.setText(String.valueOf(sp.getGia())); 
		txtChatLieu.setText(sp.getChatLieu());
		cbbLoai.setSelectedItem(sp.getTenLoai());
		cbbThuongHieu.setSelectedItem(sp.getTenThuongHieu());
	}

	private void xulyLuuDuLieu() {
		try {
			if(txtMaSP.getText().trim().isEmpty() || txtTenSP.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Mã và Tên SP không để trống!"); 
				return; 
				}
			String maLoai = spController.layMaLoaiTheoTen(cbbLoai.getSelectedItem().toString());
			String maTH = spController.layMaTHTheoTen(cbbThuongHieu.getSelectedItem().toString());

			entity.SanPhamEntity sp = new entity.SanPhamEntity();
			sp.setMaSP(txtMaSP.getText().trim());
			sp.setTenSP(txtTenSP.getText().trim());
			sp.setMaLoai(maLoai);
			sp.setMaTH(maTH);
			sp.setChatLieu(txtChatLieu.getText().trim());
			sp.setSoLuongConLai(Integer.parseInt(txtSoLuong.getText().trim()));
			sp.setGia(Double.parseDouble(txtGia.getText().trim()));

			boolean ok = cheDo.equals("THEM") ? spController.themSanPham(sp) : spController.suaSanPham(sp);
			if (ok) { 
				JOptionPane.showMessageDialog(this, "Thành công!");
				dispose();
			} else { 
				JOptionPane.showMessageDialog(this, "Thất bại (Trùng mã)!"); 
				}
		} catch (NumberFormatException ex) { 
			JOptionPane.showMessageDialog(this, "Giá và Số lượng phải là số!"); 
			}
	}
}