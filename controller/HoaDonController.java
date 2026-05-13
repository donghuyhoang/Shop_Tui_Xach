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
        if (maHDMoi == -1) return false;
        
        for (ChiTietHoaDonEntity chiTiet : dsChiTiet) {
            chiTiet.setMaHD(maHDMoi); 
            ctDAO.insert(chiTiet);
            spDAO.truTonKho(chiTiet.getMaSP(), chiTiet.getSoLuong());
        }
        return true; 
    }

    public List<HoaDonEntity> layDanhSachHoaDon() { return hdDAO.getAll(); }
    public List<HoaDonEntity> timKiemHoaDon(String maHD, String maND, String vaiTro) { return hdDAO.search(maHD, maND, vaiTro); }
    public List<ChiTietHoaDonEntity> layChiTietHoaDon(int maHD) { return ctDAO.getByMaHD(maHD); }

    // DUYỆT ĐƠN ONLINE
    public boolean duyetDonHang(int maHD) {
        return hdDAO.updateTrangThai(maHD, "Đã thanh toán");
    }

    // HỦY ĐƠN & HOÀN KHO
    public boolean huyDonHang(int maHD) {
        // 1. Lấy danh sách sản phẩm của hóa đơn này
        List<ChiTietHoaDonEntity> dsChiTiet = ctDAO.getByMaHD(maHD);
        
        // 2. Trả lại tồn kho cho từng sản phẩm
        for (ChiTietHoaDonEntity ct : dsChiTiet) {
            spDAO.hoanTraTonKho(ct.getMaSP(), ct.getSoLuong());
        }
        
        // 3. Đổi trạng thái hóa đơn thành "Đã hủy" (vẫn giữ lại trong DB để làm lịch sử)
        return hdDAO.updateTrangThai(maHD, "Đã hủy");
    }
}