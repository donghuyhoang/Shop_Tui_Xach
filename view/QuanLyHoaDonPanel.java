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
import javax.swing.table.DefaultTableModel;

import controller.HoaDonController;
import entity.HoaDonEntity;

public class QuanLyHoaDonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtTimMaHD;
	private JTextField txtTimMaND;
	private JComboBox<String> cbbVaiTro;
	private JComboBox<String> cbbTrangThai; // Khai báo thêm ComboBox Trạng Thái
	private JTable tblHoaDon;
	
	private JButton btnChapNhan;
	private JButton btnTuChoi;
	
	private HoaDonController hdController = new HoaDonController();

	public QuanLyHoaDonPanel() {
		setLayout(null);
		
		// 1. KHU VỰC TÌM KIẾM (Đã nới rộng chiều cao lên 110 để chứa 2 dòng)
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm và Lọc Hóa Đơn"));
		pnlTimKiem.setBounds(10, 10, 770, 110);
		pnlTimKiem.setLayout(null);
		add(pnlTimKiem);
		
		// ---- DÒNG 1 ----
		JLabel lblMaHD = new JLabel("Mã HD:");
		lblMaHD.setBounds(20, 30, 50, 20);
		pnlTimKiem.add(lblMaHD);
		
		txtTimMaHD = new JTextField();
		txtTimMaHD.setBounds(70, 30, 100, 22);
		pnlTimKiem.add(txtTimMaHD);
		
		JLabel lblMaND = new JLabel("Mã KH:");
		lblMaND.setBounds(190, 30, 50, 20);
		pnlTimKiem.add(lblMaND);
		
		txtTimMaND = new JTextField();
		txtTimMaND.setBounds(240, 30, 100, 22);
		pnlTimKiem.add(txtTimMaND);
		
		// ---- DÒNG 2 ----
		JLabel lblVaiTro = new JLabel("Loại đơn:");
		lblVaiTro.setBounds(20, 70, 60, 20);
		pnlTimKiem.add(lblVaiTro);
		
		cbbVaiTro = new JComboBox<String>();
		cbbVaiTro.addItem("Tất cả");
		cbbVaiTro.addItem("Mua tại quầy");
		cbbVaiTro.addItem("Đặt online");
		cbbVaiTro.setBounds(80, 70, 120, 22);
		pnlTimKiem.add(cbbVaiTro);

		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setBounds(220, 70, 70, 20);
		pnlTimKiem.add(lblTrangThai);
		
		cbbTrangThai = new JComboBox<String>();
		cbbTrangThai.addItem("Tất cả");
		cbbTrangThai.addItem("Chờ duyệt");
		cbbTrangThai.addItem("Đã thanh toán");
		cbbTrangThai.addItem("Đã hủy");
		cbbTrangThai.setBounds(290, 70, 130, 22);
		pnlTimKiem.add(cbbTrangThai);
		
		// ---- NÚT BẤM (Đặt sang góc phải) ----
		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(500, 45, 100, 35);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlTimKiem.add(btnTimKiem);
		
		JButton btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBounds(620, 45, 100, 35);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlTimKiem.add(btnLamMoi);

		// 2. BẢNG DANH SÁCH HÓA ĐƠN (Đẩy Y xuống 130, nén chiều cao còn 270)
		JPanel pnlDS = new JPanel();
		pnlDS.setBorder(BorderFactory.createTitledBorder("Danh sách Hóa Đơn (Nhấn đúp chuột vào 1 dòng để xem chi tiết)"));
		pnlDS.setBounds(10, 130, 770, 270);
		pnlDS.setLayout(null);
		add(pnlDS);
		
		JScrollPane scrollHoaDon = new JScrollPane();
		scrollHoaDon.setBounds(10, 20, 750, 240);
		pnlDS.add(scrollHoaDon);
		
		tblHoaDon = new JTable();
		tblHoaDon.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Mã HD", "Ngày Lập", "Mã KH (ND)", "Tổng Tiền", "Loại đơn", "Trạng thái"}
		) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollHoaDon.setViewportView(tblHoaDon);

		// 3. KHU VỰC XỬ LÝ ĐƠN ONLINE (Vị trí Y giữ nguyên 410 là đẹp)
		JPanel pnlXuLy = new JPanel();
		pnlXuLy.setBorder(BorderFactory.createTitledBorder("Xử lý Đơn Hàng Online"));
		pnlXuLy.setBounds(10, 410, 770, 90);
		pnlXuLy.setLayout(null);
		add(pnlXuLy);

		btnChapNhan = new JButton("Chấp nhận đơn");
		btnChapNhan.setBounds(200, 25, 150, 40);
		btnChapNhan.setBackground(new Color(50, 205, 50));
		btnChapNhan.setForeground(Color.WHITE);
		btnChapNhan.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnChapNhan.setEnabled(false); 
		pnlXuLy.add(btnChapNhan);

		btnTuChoi = new JButton("Từ chối / Hủy đơn");
		btnTuChoi.setBounds(400, 25, 150, 40);
		btnTuChoi.setBackground(Color.RED);
		btnTuChoi.setForeground(Color.WHITE);
		btnTuChoi.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnTuChoi.setEnabled(false);
		pnlXuLy.add(btnTuChoi);
		
		// ================= XỬ LÝ SỰ KIỆN =================
		loadDataToTableHoaDon();

		btnTimKiem.addActionListener(e -> {
			try {
				String maHD = txtTimMaHD.getText().trim();
				String maND = txtTimMaND.getText().trim();
				String vaiTro = cbbVaiTro.getSelectedItem().toString();
				String trangThai = cbbTrangThai.getSelectedItem().toString(); // Lấy giá trị trạng thái
				
				// Truyền 4 tham số vào Controller
				List<HoaDonEntity> ds = hdController.timKiemHoaDon(maHD, maND, vaiTro, trangThai);
				fillTableHoaDon(ds);
				
				// Khóa lại nút sau khi tìm kiếm để tránh lỗi thao tác
				btnChapNhan.setEnabled(false);
				btnTuChoi.setEnabled(false);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Mã Hóa Đơn phải là số hợp lệ!");
			}
		});

		btnLamMoi.addActionListener(e -> refreshData());

		tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) { 
					int row = tblHoaDon.getSelectedRow();
					if (row >= 0) {
						int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
						ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(maHD);
						dialog.setVisible(true);
					}
				}
			}
		});

		tblHoaDon.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int row = tblHoaDon.getSelectedRow();
				if (row >= 0) {
					String loaiDon = tblHoaDon.getValueAt(row, 4).toString();
					String trangThai = tblHoaDon.getValueAt(row, 5).toString();
					
					if ((loaiDon.equals("Đặt online") || loaiDon.equals("Online")) && trangThai.equals("Chờ duyệt")) {
						btnChapNhan.setEnabled(true);
						btnTuChoi.setEnabled(true);
					} else {
						btnChapNhan.setEnabled(false);
						btnTuChoi.setEnabled(false);
					}
				}
			}
		});

		btnChapNhan.addActionListener(e -> {
			int row = tblHoaDon.getSelectedRow();
			int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
			if (hdController.duyetDonHang(maHD)) {
				JOptionPane.showMessageDialog(this, "Đã duyệt đơn hàng thành công!");
				
				// Tìm kiếm lại với bộ lọc hiện tại thay vì load toàn bộ
				btnTimKiem.doClick(); 
			}
		});

		btnTuChoi.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đơn hàng này không?\n(Sản phẩm sẽ được cộng lại vào kho)", "Xác nhận Hủy", JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.YES_OPTION) {
				int row = tblHoaDon.getSelectedRow();
				int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
				
				if (hdController.huyDonHang(maHD)) {
					JOptionPane.showMessageDialog(this, "Đã hủy đơn hàng và hoàn trả tồn kho thành công!");
					btnTimKiem.doClick(); 
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
				hd.getVaiTro(),
				hd.getTrangThai()
			});
		}
	}

	public void refreshData() {
		txtTimMaHD.setText("");
		txtTimMaND.setText("");
		cbbVaiTro.setSelectedIndex(0);
		cbbTrangThai.setSelectedIndex(0); // Reset cả ComboBox Trạng Thái
		loadDataToTableHoaDon(); 
		btnChapNhan.setEnabled(false);
		btnTuChoi.setEnabled(false);
	}
}