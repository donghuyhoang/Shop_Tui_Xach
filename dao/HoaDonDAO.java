package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entity.HoaDonEntity;
import utils.ConnectionJDBCUtil;

public class HoaDonDAO {
    
    // Hàm Insert
    public int insert(HoaDonEntity hd) {
        String sql = "INSERT INTO hoadon (MaND, TongTien, VaiTro) VALUES (?, ?, ?)"; 
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
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

    // Hàm lấy tất cả Hóa Đơn 
    public java.util.List<HoaDonEntity> getAll() {
        java.util.List<HoaDonEntity> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM hoadon ORDER BY NgayLap DESC"; 
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                HoaDonEntity hd = new HoaDonEntity();
                hd.setMaHD(rs.getInt("MaHD"));
                hd.setNgayLap(rs.getTimestamp("NgayLap"));
                hd.setMaND(rs.getString("MaND"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setVaiTro(rs.getString("VaiTro"));
                list.add(hd);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Hàm tìm kiếm Hóa Đơn (Bổ sung lấy NgayLap)
    public java.util.List<HoaDonEntity> search(String maHD, String maND, String vaiTro) {
        java.util.List<HoaDonEntity> list = new java.util.ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM hoadon WHERE 1=1 "); 
        
        if (!maHD.isEmpty()) sql.append("AND MaHD = ? ");
        if (!maND.isEmpty()) sql.append("AND MaND LIKE ? ");
        if (!vaiTro.equals("Tất cả")) sql.append("AND VaiTro = ? ");
        sql.append("ORDER BY NgayLap DESC");

        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (!maHD.isEmpty()) pst.setInt(paramIndex++, Integer.parseInt(maHD));
            if (!maND.isEmpty()) pst.setString(paramIndex++, "%" + maND + "%");
            if (!vaiTro.equals("Tất cả")) pst.setString(paramIndex++, vaiTro);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    HoaDonEntity hd = new HoaDonEntity();
                    hd.setMaHD(rs.getInt("MaHD"));
                    hd.setNgayLap(rs.getTimestamp("NgayLap")); 
                    hd.setMaND(rs.getString("MaND"));
                    hd.setTongTien(rs.getDouble("TongTien"));
                    hd.setVaiTro(rs.getString("VaiTro"));
                    list.add(hd);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}