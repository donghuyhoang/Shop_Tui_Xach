package controller;

import java.util.List;

import dao.SanPhamDAO;
import dto.SanPhamDTO;
import entity.SanPhamEntity;
public class SanPhamController {
	private SanPhamDAO sanPhamDAO;
	public SanPhamController() {
		this.sanPhamDAO = new SanPhamDAO();
	}
	public List<SanPhamDTO> layDanhSachSanPham(){
		return sanPhamDAO.getAllSanPhamDTO();
	}
	public boolean themSanPham(SanPhamEntity sp) {
		return sanPhamDAO.insert(sp);
	}
	public boolean xoaSanPham(String maSP) {
		return sanPhamDAO.delete(maSP);
	}
	public boolean suaSanPham(entity.SanPhamEntity sp) {
	    return sanPhamDAO.update(sp); 
	}
	public String layMaLoaiTheoTen(String tenLoai) {
	    return sanPhamDAO.getMaLoaiByTen(tenLoai);
	}

	public String layMaTHTheoTen(String tenTH) {
	    return sanPhamDAO.getMaTHByTen(tenTH);
	}
	public List<String> layDanhSachTenLoai() { return sanPhamDAO.getAllTenLoai(); }
	public List<String> layDanhSachTenTH() { return sanPhamDAO.getAllTenTH(); }
	public List<SanPhamDTO> timKiemVaSapXep(String tenSP, String tenTH, int giaIndex, int sortIndex) {
	    return sanPhamDAO.searchAndSort(tenSP, tenTH, giaIndex, sortIndex);
	}
	
}
