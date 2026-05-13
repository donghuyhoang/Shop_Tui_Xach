package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import entity.ChiTietHoaDonEntity;
import utils.ConnectionJDBCUtil;

public class ChiTietHoaDonDAO {
    public boolean insert(ChiTietHoaDonEntity ct) {
        String sql = "INSERT INTO chitiethoadon (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, ct.getMaHD());
            pst.setString(2, ct.getMaSP());
            pst.setInt(3, ct.getSoLuong());
            pst.setDouble(4, ct.getDonGia());
            pst.setDouble(5, ct.getThanhTien());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Lấy chi tiết để hiển thị Dialog hoặc để xử lý Hủy Đơn Hoàn Kho
    public java.util.List<ChiTietHoaDonEntity> getByMaHD(int maHD) {
        java.util.List<ChiTietHoaDonEntity> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM chitiethoadon WHERE MaHD = ?";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maHD);
            try (java.sql.ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDonEntity ct = new ChiTietHoaDonEntity();
                    ct.setMaHD(rs.getInt("MaHD"));
                    ct.setMaSP(rs.getString("MaSP"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));
                    ct.setThanhTien(rs.getDouble("ThanhTien"));
                    list.add(ct);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}