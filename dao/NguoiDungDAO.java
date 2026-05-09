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
}
