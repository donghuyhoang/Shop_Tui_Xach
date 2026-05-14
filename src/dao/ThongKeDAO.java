package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dto.SanPhamDTO;
import utils.ConnectionJDBCUtil;

public class ThongKeDAO {
    
    // 1. Lấy tổng doanh thu (Chỉ tính đơn Đã thanh toán)
    public double getDoanhThu(int thang, int nam) {
        double tong = 0;
        // Thêm điều kiện TrangThai = 'Đã thanh toán'
        StringBuilder sql = new StringBuilder("SELECT SUM(TongTien) FROM hoadon WHERE TrangThai = 'Đã thanh toán' ");
        if (thang > 0) sql.append("AND MONTH(NgayLap) = ? ");
        if (nam > 0) sql.append("AND YEAR(NgayLap) = ? ");

        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (thang > 0) pst.setInt(index++, thang);
            if (nam > 0) pst.setInt(index++, nam);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) tong = rs.getDouble(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return tong;
    }

    // 2. Lấy tổng số hóa đơn (Chỉ đếm đơn Đã thanh toán)
    public int getTongSoHoaDon(int thang, int nam) {
        int tong = 0;
        // Thêm điều kiện TrangThai = 'Đã thanh toán'
        StringBuilder sql = new StringBuilder("SELECT COUNT(MaHD) FROM hoadon WHERE TrangThai = 'Đã thanh toán' ");
        if (thang > 0) sql.append("AND MONTH(NgayLap) = ? ");
        if (nam > 0) sql.append("AND YEAR(NgayLap) = ? ");

        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (thang > 0) pst.setInt(index++, thang);
            if (nam > 0) pst.setInt(index++, nam);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) tong = rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return tong;
    }

    // 3. Lấy Top 10 Sản phẩm bán chạy nhất (Chỉ tính từ đơn Đã thanh toán)
    public List<SanPhamDTO> getTopBanChay(int thang, int nam) {
        List<SanPhamDTO> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT sp.MaSP, sp.TenSP, sp.Gia, SUM(ct.SoLuong) AS TongBan " +
            "FROM chitiethoadon ct " +
            "JOIN hoadon hd ON ct.MaHD = hd.MaHD " +
            "JOIN sanpham sp ON ct.MaSP = sp.MaSP " +
            "WHERE hd.TrangThai = 'Đã thanh toán' " 
        );

        if (thang > 0) sql.append("AND MONTH(hd.NgayLap) = ? ");
        if (nam > 0) sql.append("AND YEAR(hd.NgayLap) = ? ");
        
        sql.append("GROUP BY sp.MaSP, sp.TenSP, sp.Gia ORDER BY TongBan DESC LIMIT 10");

        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            
            int index = 1;
            if (thang > 0) pst.setInt(index++, thang);
            if (nam > 0) pst.setInt(index++, nam);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSP(rs.getString("MaSP"));
                    sp.setTenSP(rs.getString("TenSP"));
                    sp.setGia(rs.getDouble("Gia"));
                    sp.setSoLuongDaBan(rs.getInt("TongBan")); 
                    list.add(sp);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}