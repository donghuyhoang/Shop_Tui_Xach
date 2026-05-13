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
                + "FROM sanpham sp "
                + "LEFT JOIN loaitui l ON sp.MaLoai = l.MaLoai "
                + "LEFT JOIN thuonghieu th ON sp.MaTH = th.MaTH";
                   
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
        String sql = "SELECT MaLoai FROM loaitui WHERE TenLoai = ?"; 
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
        String sql = "SELECT MaTH FROM thuonghieu WHERE TenTH = ?";
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
        String sql = "SELECT TenLoai FROM loaitui"; 
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) list.add(rs.getString("TenLoai"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getAllTenTH() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT TenTH FROM thuonghieu"; 
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) list.add(rs.getString("TenTH"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(SanPhamEntity sp) {
        String sql = "INSERT INTO sanpham (MaSP, TenSP, MaLoai, ChatLieu, MaTH, SoLuong, Gia) VALUES (?, ?,?,?, ?, ?, ?)";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, sp.getMaSP());
            pst.setString(2, sp.getTenSP());
            pst.setString(3, sp.getMaLoai()); 
            pst.setString(4, sp.getChatLieu());
            pst.setString(5, sp.getMaTH());   
            pst.setInt(6, sp.getSoLuongConLai());
            pst.setDouble(7, sp.getGia());
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean delete(String maSP) {
        String sql = "DELETE FROM sanpham WHERE MaSP=?";
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
        String sql = "UPDATE sanpham SET TenSP=?, MaLoai=?, ChatLieu = ?, MaTH=?, SoLuong=?, Gia=? WHERE MaSP=?";
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
        	    "SELECT sp.MaSP, sp.TenSP, l.TenLoai, sp.ChatLieu, th.TenTH, sp.SoLuong, sp.Gia, sp.SoLuongDaBan " +
        	    "FROM sanpham sp " +
        	    "LEFT JOIN loaitui l ON sp.MaLoai = l.MaLoai " +
        	    "LEFT JOIN thuonghieu th ON sp.MaTH = th.MaTH " +
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

    public boolean truTonKho(String maSP, int soLuongMua) {
        String sql = "UPDATE sanpham SET SoLuong = SoLuong - ?, SoLuongDaBan = SoLuongDaBan + ? WHERE MaSP = ?";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, soLuongMua);
            pst.setInt(2, soLuongMua);
            pst.setString(3, maSP);
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    public boolean hoanTraTonKho(String maSP, int soLuongTra) {
        // Cộng lại số lượng tồn kho, trừ đi số lượng đã bán
        String sql = "UPDATE sanpham SET SoLuong = SoLuong + ?, SoLuongDaBan = SoLuongDaBan - ? WHERE MaSP = ?";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, soLuongTra);
            pst.setInt(2, soLuongTra);
            pst.setString(3, maSP);
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}