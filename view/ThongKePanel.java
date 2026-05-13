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
import javax.swing.table.DefaultTableModel;
import controller.ThongKeController;
import dto.SanPhamDTO;

public class ThongKePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ThongKeController tkController = new ThongKeController();

    private JLabel lblDoanhThu;
    private JLabel lblSoHoaDon;
    private JComboBox<String> cbbThang;
    private JComboBox<String> cbbNam;
    private JTable tblTopSP;

    public ThongKePanel() {
        setLayout(null);

        // 1. KHU VỰC TỔNG QUAN (2 ô Card hiển thị số liệu)
        JPanel pnlTongQuan = new JPanel();
        pnlTongQuan.setBorder(BorderFactory.createTitledBorder("Tổng Quan Doanh Thu"));
        pnlTongQuan.setBounds(10, 10, 770, 100);
        pnlTongQuan.setLayout(null);
        add(pnlTongQuan);

        // Card Doanh thu
        JPanel cardDoanhThu = new JPanel();
        cardDoanhThu.setBackground(new Color(255, 228, 196)); // Màu cam nhạt
        cardDoanhThu.setBounds(30, 20, 320, 60);
        cardDoanhThu.setLayout(null);
        pnlTongQuan.add(cardDoanhThu);

        JLabel lblTitleDT = new JLabel("TỔNG DOANH THU:");
        lblTitleDT.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitleDT.setBounds(10, 20, 150, 20);
        cardDoanhThu.add(lblTitleDT);

        lblDoanhThu = new JLabel("0 VNĐ");
        lblDoanhThu.setForeground(Color.RED);
        lblDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDoanhThu.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDoanhThu.setBounds(160, 15, 150, 30);
        cardDoanhThu.add(lblDoanhThu);

        // Card Hóa đơn
        JPanel cardHoaDon = new JPanel();
        cardHoaDon.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt
        cardHoaDon.setBounds(420, 20, 320, 60);
        cardHoaDon.setLayout(null);
        pnlTongQuan.add(cardHoaDon);

        JLabel lblTitleHD = new JLabel("TỔNG SỐ HÓA ĐƠN:");
        lblTitleHD.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitleHD.setBounds(10, 20, 150, 20);
        cardHoaDon.add(lblTitleHD);

        lblSoHoaDon = new JLabel("0");
        lblSoHoaDon.setForeground(Color.BLUE);
        lblSoHoaDon.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblSoHoaDon.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSoHoaDon.setBounds(170, 15, 140, 30);
        cardHoaDon.add(lblSoHoaDon);

        // 2. KHU VỰC LỌC THỜI GIAN
        JPanel pnlLoc = new JPanel();
        pnlLoc.setBorder(BorderFactory.createTitledBorder("Bộ Lọc Thời Gian"));
        pnlLoc.setBounds(10, 120, 770, 60);
        pnlLoc.setLayout(null);
        add(pnlLoc);

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setBounds(150, 25, 50, 20);
        pnlLoc.add(lblThang);

        cbbThang = new JComboBox<>();
        cbbThang.addItem("Tất cả");
        for (int i = 1; i <= 12; i++) cbbThang.addItem("Tháng " + i);
        cbbThang.setBounds(200, 25, 100, 22);
        pnlLoc.add(cbbThang);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setBounds(330, 25, 40, 20);
        pnlLoc.add(lblNam);

        cbbNam = new JComboBox<>();
        cbbNam.addItem("Tất cả");
        cbbNam.addItem("2024");
        cbbNam.addItem("2025");
        cbbNam.addItem("2026");
        cbbNam.setBounds(370, 25, 100, 22);
        pnlLoc.add(cbbNam);

        JButton btnXem = new JButton("Xem Thống Kê");
        btnXem.setBounds(500, 24, 120, 25);
        pnlLoc.add(btnXem);

        // 3. KHU VỰC BẢNG TOP SẢN PHẨM BÁN CHẠY
        JPanel pnlTopSP = new JPanel();
        pnlTopSP.setBorder(BorderFactory.createTitledBorder("Top 10 Sản Phẩm Bán Chạy Nhất"));
        pnlTopSP.setBounds(10, 190, 770, 310);
        pnlTopSP.setLayout(null);
        add(pnlTopSP);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 20, 750, 280);
        pnlTopSP.add(scrollPane);

        tblTopSP = new JTable();
        tblTopSP.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng Đã Bán"}
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        scrollPane.setViewportView(tblTopSP);

        // ================= SỰ KIỆN =================
        btnXem.addActionListener(e -> loadThongKe());

        // Load dữ liệu mặc định khi mở lên
        loadThongKe();
        loadTopSanPham();
    }

    public void loadThongKe() {
        int thang = cbbThang.getSelectedIndex(); // 0 là Tất cả, 1 là Tháng 1...
        int nam = 0;
        if (cbbNam.getSelectedIndex() > 0) {
            nam = Integer.parseInt(cbbNam.getSelectedItem().toString());
        }

        double doanhThu = tkController.layDoanhThu(thang, nam);
        int soHD = tkController.layTongHoaDon(thang, nam);

        DecimalFormat df = new DecimalFormat("#,###");
        lblDoanhThu.setText(df.format(doanhThu) + " VNĐ");
        lblSoHoaDon.setText(String.valueOf(soHD));
    }

    public void loadTopSanPham() {
        // Lấy tháng năm hiện tại đang chọn
        int thang = cbbThang.getSelectedIndex();
        int nam = 0;
        if (cbbNam.getSelectedIndex() > 0) {
            nam = Integer.parseInt(cbbNam.getSelectedItem().toString());
        }

        // Truyền xuống dưới controller
        List<SanPhamDTO> list = tkController.layTopBanChay(thang, nam);
        
        DefaultTableModel model = (DefaultTableModel) tblTopSP.getModel();
        model.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");

        for (SanPhamDTO sp : list) {
            model.addRow(new Object[] {
                sp.getMaSP(),
                sp.getTenSP(),
                df.format(sp.getGia()),
                sp.getSoLuongDaBan()
            });
        }
    }    
    // Hàm này được gọi khi chuyển tab để cập nhật số liệu lập tức
    public void refreshData() {
        loadThongKe();
        loadTopSanPham();
    }
}