package view;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import controller.ThongKeController;
import dto.SanPhamDTO;

public class ThongKePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ThongKeController tkController = new ThongKeController();

    private JLabel lblDoanhThu, lblSoHoaDon;
    private JComboBox<String> cbbThang, cbbNam;
    private JTable tblTopSP;

    public ThongKePanel() {
        setLayout(null);
        setBackground(new Color(24, 24, 24));

        // 1. Khu vực tổng quan
        JPanel pnlTongQuan = createDarkPanel("Tổng Quan Doanh Thu");
        pnlTongQuan.setBounds(10, 10, 770, 110);
        add(pnlTongQuan);

        // Card Doanh thu
        JPanel cardDoanhThu = new JPanel(null);
        cardDoanhThu.setBackground(new Color(45, 45, 45));
        cardDoanhThu.setBounds(30, 20, 320, 75);
        pnlTongQuan.add(cardDoanhThu);

        JLabel lblTitleDT = new JLabel("TỔNG DOANH THU", SwingConstants.CENTER);
        lblTitleDT.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitleDT.setForeground(Color.WHITE);
        lblTitleDT.setBounds(10, 10, 300, 20);
        cardDoanhThu.add(lblTitleDT);

        lblDoanhThu = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblDoanhThu.setForeground(new Color(255, 191, 0));
        lblDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblDoanhThu.setBounds(10, 35, 300, 30);
        cardDoanhThu.add(lblDoanhThu);

        // Card Hóa đơn
        JPanel cardHoaDon = new JPanel(null);
        cardHoaDon.setBackground(new Color(45, 45, 45)); 
        cardHoaDon.setBounds(420, 20, 320, 75);
        pnlTongQuan.add(cardHoaDon);

        JLabel lblTitleHD = new JLabel("TỔNG SỐ HÓA ĐƠN", SwingConstants.CENTER);
        lblTitleHD.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitleHD.setForeground(Color.WHITE);
        lblTitleHD.setBounds(10, 10, 300, 20);
        cardHoaDon.add(lblTitleHD);

        lblSoHoaDon = new JLabel("0", SwingConstants.CENTER);
        lblSoHoaDon.setForeground(new Color(0, 206, 209)); // Màu Cyan nhạt
        lblSoHoaDon.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblSoHoaDon.setBounds(10, 35, 300, 30);
        cardHoaDon.add(lblSoHoaDon);

        // 2. Khu vực lọc thời gian
        JPanel pnlLoc = createDarkPanel("Bộ Lọc Thời Gian");
        pnlLoc.setBounds(10, 130, 770, 60);
        add(pnlLoc);

        addLabel(pnlLoc, "Tháng:", 150, 25, 50, 20);
        cbbThang = new JComboBox<>(); cbbThang.addItem("Tất cả");
        for (int i = 1; i <= 12; i++) cbbThang.addItem("Tháng " + i);
        cbbThang.setBounds(200, 25, 100, 22); pnlLoc.add(cbbThang);

        addLabel(pnlLoc, "Năm:", 330, 25, 40, 20);
        cbbNam = new JComboBox<>(); cbbNam.addItem("Tất cả");
        cbbNam.addItem("2024"); cbbNam.addItem("2025"); cbbNam.addItem("2026");
        cbbNam.setBounds(370, 25, 100, 22); pnlLoc.add(cbbNam);

        JButton btnXem = new JButton("Xem Thống Kê");
        btnXem.setBackground(new Color(255, 191, 0));
        btnXem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXem.setBounds(500, 24, 130, 25); pnlLoc.add(btnXem);

        // 3. Bảng tóp sản phẩm
        JPanel pnlTopSP = createDarkPanel("Top 10 Sản Phẩm Bán Chạy Nhất");
        pnlTopSP.setBounds(10, 200, 770, 300);
        add(pnlTopSP);

        JScrollPane scrollPane = new JScrollPane(); scrollPane.setBounds(10, 20, 750, 270); pnlTopSP.add(scrollPane);
        tblTopSP = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng Đã Bán"}) {
            public boolean isCellEditable(int row, int column) { 
            	return false; 
            	}
        });
        styleTable(tblTopSP);
        scrollPane.setViewportView(tblTopSP);

        btnXem.addActionListener(e -> refreshData());
        refreshData();
    }

    private JPanel createDarkPanel(String title) {
        JPanel pnl = new JPanel(null); pnl.setBackground(new Color(33, 33, 33));
        pnl.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
        												new Font("Tahoma", Font.BOLD, 12), Color.WHITE));
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

    public void loadThongKe() {
        int thang = cbbThang.getSelectedIndex(); 
        int nam = cbbNam.getSelectedIndex() > 0 ? Integer.parseInt(cbbNam.getSelectedItem().toString()) : 0;
        lblDoanhThu.setText(new DecimalFormat("#,###").format(tkController.layDoanhThu(thang, nam)) + " VNĐ");
        lblSoHoaDon.setText(String.valueOf(tkController.layTongHoaDon(thang, nam)));
    }

    public void loadTopSanPham() {
        int thang = cbbThang.getSelectedIndex();
        int nam = cbbNam.getSelectedIndex() > 0 ? Integer.parseInt(cbbNam.getSelectedItem().toString()) : 0;
        List<SanPhamDTO> list = tkController.layTopBanChay(thang, nam);
        DefaultTableModel model = (DefaultTableModel) tblTopSP.getModel(); model.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        for (SanPhamDTO sp : list) { 
        	model.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), df.format(sp.getGia()), sp.getSoLuongDaBan() }); 
        	}
    }    
    
    public void refreshData() { 
    	loadThongKe();
    	loadTopSanPham(); 
    }
}