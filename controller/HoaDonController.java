package controller;

import java.util.List;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;

public class HoaDonController {
    private HoaDonDAO hdDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO ctDAO = new ChiTietHoaDonDAO();
    private SanPhamDAO spDAO = new SanPhamDAO();

    public boolean thanhToan(HoaDonEntity hd, List<ChiTietHoaDonEntity> dsChiTiet) {
        int maHDMoi = hdDAO.insert(hd);
        if (maHDMoi == -1) {
            return false;
        }
        for (ChiTietHoaDonEntity chiTiet : dsChiTiet) {
            chiTiet.setMaHD(maHDMoi); 
            boolean luuChiTiet = ctDAO.insert(chiTiet);
            boolean truKho = spDAO.truTonKho(chiTiet.getMaSP(), chiTiet.getSoLuong());
            if (!luuChiTiet || !truKho) {
                return false; 
            }
        }
        
        return true; 
    }
}