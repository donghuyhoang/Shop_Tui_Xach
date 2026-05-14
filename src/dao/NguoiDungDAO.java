package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.NguoiDungEntity;
import utils.ConnectionJDBCUtil;

public class NguoiDungDAO {
	public NguoiDungEntity checklogin(String user, String password) {
		String sql = "select * from NguoiDung where TaiKhoan = ? and MatKhau = ?";
		NguoiDungEntity NguoiDung = null;
		try(Connection conn = ConnectionJDBCUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			pstmt.setString(1, user);
			pstmt.setString(2, password);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					NguoiDung = new NguoiDungEntity();
					NguoiDung.setMaND(rs.getString("MaND"));
					NguoiDung.setHoTen(rs.getString("HoTen"));
					NguoiDung.setSDT(rs.getString("SDT"));
					NguoiDung.setEmail(rs.getString("Email"));
					NguoiDung.setVaiTro(rs.getString("VaiTro"));
				}
			}
		} catch(Exception e) {
			e.getMessage();
		}
		return NguoiDung;
	}
	// Hàm Đăng ký tài khoản mới
    public boolean dangKy(NguoiDungEntity user) {
        String sql = "INSERT INTO nguoidung (MaND, HoTen, TaiKhoan, MatKhau, VaiTro, SDT, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getMaND());
            pst.setString(2, user.getHoTen());
            pst.setString(3, user.getTaiKhoan());
            pst.setString(4, user.getMatKhau());
            pst.setString(5, "Khách hàng"); // Mặc định đăng ký là khách
            pst.setString(6, user.getSDT());
            pst.setString(7, user.getDiaChi());
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    public boolean capNhatThongTin(entity.NguoiDungEntity user, String matKhauMoi) {
		String sql = "UPDATE nguoidung SET HoTen = ?, SDT = ?, Email = ?, DiaChi = ? ";
		// Nếu có nhập mật khẩu mới thì cập nhật luôn
		if (matKhauMoi != null && !matKhauMoi.trim().isEmpty()) {
			sql += ", MatKhau = '" + matKhauMoi + "' ";
		}
		sql += "WHERE MaND = ?";
		
		try (java.sql.Connection conn = utils.ConnectionJDBCUtil.getConnection();
			 java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, user.getHoTen());
			pst.setString(2, user.getSDT());
			pst.setString(3, user.getEmail());
			pst.setString(4, user.getDiaChi());
			pst.setString(5, user.getMaND());
			return pst.executeUpdate() > 0;
		} catch (Exception e) { e.printStackTrace(); return false; }
	}
}
