package entity;

public class HoaDonEntity {
    private int maHD;
    private java.sql.Timestamp ngayLap;
    private String maND;
    private double tongTien;
    private String vaiTro; 
    
    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    
    public java.sql.Timestamp getNgayLap() { return ngayLap; }
    public void setNgayLap(java.sql.Timestamp ngayLap) { this.ngayLap = ngayLap; }
    
    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
}