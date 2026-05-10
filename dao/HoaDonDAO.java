package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entity.HoaDonEntity;
import utils.ConnectionJDBCUtil;

public class HoaDonDAO {
    public int insert(HoaDonEntity hd) {
        // CẬP NHẬT CHỮ MaKH THÀNH MaND Ở ĐÂY
        String sql = "INSERT INTO HoaDon (MaND, TongTien, VaiTro) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Lấy MaND từ Entity
            if (hd.getMaND() == null || hd.getMaND().trim().isEmpty()) {
                pst.setNull(1, java.sql.Types.VARCHAR);
            } else {
                pst.setString(1, hd.getMaND());
            }
            
            pst.setDouble(2, hd.getTongTien());
            pst.setString(3, hd.getVaiTro());
            
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1); 
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return -1;
    }
}