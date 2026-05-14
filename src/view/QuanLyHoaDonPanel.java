package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import controller.HoaDonController;
import entity.HoaDonEntity;

public class QuanLyHoaDonPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField txtTimMaHD, txtTimMaND;
    private JComboBox<String> cbbVaiTro, cbbTrangThai;
    private JTable tblHoaDon;
    private JButton btnChapNhan, btnTuChoi;
    private HoaDonController hdController = new HoaDonController();

    public QuanLyHoaDonPanel() {
        setLayout(null);
        setBackground(new Color(24, 24, 24));

        // 1. Khu tìm kiếm
        JPanel pnlTimKiem = createDarkPanel("Tìm kiếm và Lọc Hóa Đơn");
        pnlTimKiem.setBounds(10, 10, 770, 110);
        add(pnlTimKiem);
        
        addLabel(pnlTimKiem, "Mã HD:", 20, 30, 50, 20);
        txtTimMaHD = new JTextField(); txtTimMaHD.setBounds(70, 30, 100, 22); pnlTimKiem.add(txtTimMaHD);
        addLabel(pnlTimKiem, "Mã KH:", 190, 30, 50, 20);
        txtTimMaND = new JTextField(); txtTimMaND.setBounds(240, 30, 100, 22); pnlTimKiem.add(txtTimMaND);
        addLabel(pnlTimKiem, "Loại đơn:", 20, 70, 60, 20);
        cbbVaiTro = new JComboBox<>(new String[]{"Tất cả", "Mua tại quầy", "Đặt online"});
        cbbVaiTro.setBounds(80, 70, 120, 22); pnlTimKiem.add(cbbVaiTro);
        addLabel(pnlTimKiem, "Trạng thái:", 220, 70, 70, 20);
        cbbTrangThai = new JComboBox<>(new String[]{"Tất cả", "Chờ duyệt", "Đã thanh toán", "Đã hủy"});
        cbbTrangThai.setBounds(290, 70, 130, 22); pnlTimKiem.add(cbbTrangThai);
        
        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(255, 191, 0));
        btnTimKiem.setBounds(500, 45, 100, 35); 
        btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
        pnlTimKiem.add(btnTimKiem);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(255, 191, 0));
        btnLamMoi.setBounds(620, 45, 100, 35); 
        btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 12));
        pnlTimKiem.add(btnLamMoi);

        // 2. Bảng danh sách
        JPanel pnlDS = createDarkPanel("Danh sách Hóa Đơn (Nhấn đúp chuột vào 1 dòng để xem chi tiết)");
        pnlDS.setBounds(10, 130, 770, 270);
        add(pnlDS);
        
        JScrollPane scrollHoaDon = new JScrollPane();
        scrollHoaDon.setBounds(10, 25, 750, 230);
        pnlDS.add(scrollHoaDon);
        tblHoaDon = new JTable();
        tblHoaDon.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã HD", "Ngày Lập", "Mã KH (ND)", "Tổng Tiền", "Loại đơn", "Trạng thái"}
        ) {
            public boolean isCellEditable(int row, int column) {
            	return false; 
            	}
        });
        tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        styleTable(tblHoaDon);
        scrollHoaDon.setViewportView(tblHoaDon);

        // 3. Khu vực xử lý
        JPanel pnlXuLy = createDarkPanel("Xử lý Đơn Hàng Online");
        pnlXuLy.setBounds(10, 410, 770, 90);
        add(pnlXuLy);

        btnChapNhan = new JButton("Chấp nhận đơn");
        btnChapNhan.setBackground(new Color(50, 205, 50));
        btnChapNhan.setForeground(Color.WHITE);
        btnChapNhan.setBounds(200, 25, 150, 40);
        btnChapNhan.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnChapNhan.setEnabled(false);
        pnlXuLy.add(btnChapNhan);

        btnTuChoi = new JButton("Từ chối / Hủy đơn");
        btnTuChoi.setBackground(Color.RED);
        btnTuChoi.setForeground(Color.WHITE);
        btnTuChoi.setBounds(400, 25, 150, 40);
        btnTuChoi.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTuChoi.setEnabled(false);
        pnlXuLy.add(btnTuChoi);
        
        // Xử lý sự kiện
        loadDataToTableHoaDon();

        btnTimKiem.addActionListener(e -> {
            try {
                String maHD = txtTimMaHD.getText().trim();
                String maND = txtTimMaND.getText().trim();
                String vaiTro = cbbVaiTro.getSelectedItem().toString();
                String trangThai = cbbTrangThai.getSelectedItem().toString(); 
                
                List<HoaDonEntity> ds = hdController.timKiemHoaDon(maHD, maND, vaiTro, trangThai);
                fillTableHoaDon(ds);
                
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
                btnTimKiem.doClick(); 
            }
        });

        btnTuChoi.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đơn hàng này không?\n(Sản phẩm sẽ được cộng lại vào kho)",
            											"Xác nhận Hủy", JOptionPane.YES_NO_OPTION);
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

    // Hàm tiện ích giao diện
    private JPanel createDarkPanel(String title) {
        JPanel pnl = new JPanel(null);
        pnl.setBackground(new Color(33, 33, 33));
        pnl.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.DEFAULT_JUSTIFICATION,
        				TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.BOLD, 12), Color.WHITE));
        return pnl;
    }

    private void addLabel(JPanel pnl, String text, int x, int y, int w, int h) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, w, h);
        pnl.add(lbl);
    }

    private void styleTable(JTable tbl) {
        tbl.setBackground(new Color(40, 40, 40));
        tbl.setForeground(Color.WHITE);
        tbl.setGridColor(Color.GRAY);
        tbl.getTableHeader().setBackground(new Color(255, 191, 0));
        tbl.getTableHeader().setForeground(Color.BLACK);
        tbl.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
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
        cbbTrangThai.setSelectedIndex(0); 
        loadDataToTableHoaDon(); 
        btnChapNhan.setEnabled(false);
        btnTuChoi.setEnabled(false);
    }
}