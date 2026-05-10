package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import entity.ChiTietHoaDonEntity;
import utils.ConnectionJDBCUtil;

public class ChiTietHoaDonDAO {
    public boolean insert(ChiTietHoaDonEntity ct) {
        String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
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
}