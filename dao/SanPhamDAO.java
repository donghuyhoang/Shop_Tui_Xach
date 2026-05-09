package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dto.SanPhamDTO;
import entity.SanPhamEntity;
import utils.ConnectionJDBCUtil;

public class SanPhamDAO {
	public List<SanPhamDTO> getAllSanPhamDTO() {
        List<SanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT sp.MaSP, sp.TenSP, l.TenLoai, sp.ChatLieu, th.TenTH, sp.SoLuong, sp.Gia, sp.SoLuongDaBan "
                   + "FROM SanPham sp "
                   + "LEFT JOIN LoaiTui l ON sp.MaLoai = l.MaLoai "
                   + "LEFT JOIN ThuongHieu th ON sp.MaTH = th.MaTH";
                   
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                SanPhamDTO dto = new SanPhamDTO();
                dto.setMaSP(rs.getString("MaSP"));
                dto.setTenSP(rs.getString("TenSP"));
                dto.setTenLoai(rs.getString("TenLoai")); 
                dto.setChatLieu(rs.getString("ChatLieu"));
                dto.setTenThuongHieu(rs.getString("TenTH")); 
                dto.setSoLuongConLai(rs.getInt("SoLuong")); 
                dto.setGia(rs.getDouble("Gia"));
                dto.setSoLuongDaBan(rs.getInt("SoLuongDaBan"));
                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public String getMaLoaiByTen(String tenLoai) {
        String sql = "SELECT MaLoai FROM LoaiTui WHERE TenLoai = ?"; // Sửa tên bảng/cột cho đúng DB của bạn
        try (java.sql.Connection conn = utils.ConnectionJDBCUtil.getConnection();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tenLoai);
            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("MaLoai");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public String getMaTHByTen(String tenTH) {
        String sql = "SELECT MaTH FROM ThuongHieu WHERE TenTH = ?";
        try (java.sql.Connection conn = utils.ConnectionJDBCUtil.getConnection();
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tenTH);
            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("MaTH");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    public List<String> getAllTenLoai() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT TenLoai FROM LoaiTui"; // Sửa bảng cho khớp DB
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) list.add(rs.getString("TenLoai"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public List<String> getAllTenTH() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT TenTH FROM ThuongHieu"; // Sửa bảng cho khớp DB
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) list.add(rs.getString("TenTH"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public boolean insert(SanPhamEntity sp) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, MaLoai, ChatLieu, MaTH, SoLuong, Gia) VALUES (?, ?,?,?, ?, ?, ?)";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, sp.getMaSP());
            pst.setString(2, sp.getTenSP());
            pst.setString(3, sp.getMaLoai()); // Lưu Mã Loại
            pst.setString(4, sp.getChatLieu());
            pst.setString(5, sp.getMaTH());   // Lưu Mã TH
            pst.setInt(6, sp.getSoLuongConLai());
            pst.setDouble(7, sp.getGia());
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    public boolean delete(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, maSP);
            return pst.executeUpdate() > 0;
            
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    public boolean update(SanPhamEntity sp) {
        String sql = "UPDATE SanPham SET TenSP=?, MaLoai=?, ChatLieu = ?,MaTH=?, SoLuong=?, Gia=? WHERE MaSP=?";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, sp.getTenSP());
            pst.setString(2, sp.getMaLoai());
            pst.setString(3, sp.getChatLieu());
            pst.setString(4, sp.getMaTH());
            pst.setInt(5, sp.getSoLuongConLai());
            pst.setDouble(6, sp.getGia());
            pst.setString(7, sp.getMaSP());
            return pst.executeUpdate() > 0;
        } 
        catch (Exception e) { 
        	e.printStackTrace(); return false; 
        }
    }
    public List<SanPhamDTO> searchAndSort(String tenSP, String tenTH, int giaIndex, int sortIndex) {
        List<SanPhamDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT sp.MaSP, sp.TenSP, l.TenLoai, th.TenTH, sp.SoLuong, sp.Gia, sp.SoLuongDaBan " +
            "FROM SanPham sp " +
            "LEFT JOIN LoaiTui l ON sp.MaLoai = l.MaLoai " +
            "LEFT JOIN ThuongHieu th ON sp.MaTH = th.MaTH " +
            "WHERE sp.TenSP LIKE ? "
        );

        if (tenTH != null && !tenTH.equals("Tất cả")) {
            sql.append("AND th.TenTH = ? ");
        }

        if (giaIndex == 1) sql.append("AND sp.Gia < 500000 ");
        else if (giaIndex == 2) sql.append("AND sp.Gia >= 500000 AND sp.Gia <= 1000000 ");
        else if (giaIndex == 3) sql.append("AND sp.Gia > 1000000 ");

        if (sortIndex == 1) sql.append("ORDER BY sp.Gia ASC ");
        else if (sortIndex == 2) sql.append("ORDER BY sp.Gia DESC ");

        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
             
            pst.setString(1, "%" + tenSP + "%"); 
            
            if (tenTH != null && !tenTH.equals("Tất cả")) {
                pst.setString(2, tenTH);
            }
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO dto = new SanPhamDTO();
                    dto.setMaSP(rs.getString("MaSP"));
                    dto.setTenSP(rs.getString("TenSP"));
                    dto.setTenLoai(rs.getString("TenLoai"));
                    dto.setChatLieu(rs.getString("ChatLieu"));
                    dto.setTenThuongHieu(rs.getString("TenTH")); 
                    dto.setSoLuongConLai(rs.getInt("SoLuong")); 
                    dto.setGia(rs.getDouble("Gia"));
                    dto.setSoLuongDaBan(rs.getInt("SoLuongDaBan"));
                    list.add(dto);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }    
}