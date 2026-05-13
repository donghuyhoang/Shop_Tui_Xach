package controller;

import java.util.List;
import dao.ThongKeDAO;
import dto.SanPhamDTO;

public class ThongKeController {
    private ThongKeDAO tkDAO = new ThongKeDAO();

    public double layDoanhThu(int thang, int nam) {
        return tkDAO.getDoanhThu(thang, nam);
    }

    public int layTongHoaDon(int thang, int nam) {
        return tkDAO.getTongSoHoaDon(thang, nam);
    }
    

    public List<SanPhamDTO> layTopBanChay(int thang, int nam) {
        return tkDAO.getTopBanChay(thang, nam);
    }

    
}