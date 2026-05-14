package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import entity.NguoiDungEntity;
import dao.NguoiDungDAO;

public class RegisterDialog extends JDialog {
    private JTextField txtHoTen, txtTaiKhoan, txtSDT, txtDiaChi;
    private JPasswordField txtMatKhau;

    public RegisterDialog() {
        setTitle("Đăng Ký Thành Viên");
        setBounds(100, 100, 400, 450);
        getContentPane().setBackground(new Color(24, 24, 24));
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("TẠO TÀI KHOẢN", JLabel.CENTER);
        lblTitle.setForeground(new Color(255, 191, 0));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(0, 20, 400, 30);
        getContentPane().add(lblTitle);

        // Các ô nhập liệu 
        addInputFields();

        JButton btnRegister = new JButton("Đăng Ký Ngay");
        btnRegister.setBackground(new Color(255, 191, 0));
        btnRegister.setBounds(100, 340, 200, 40);
        btnRegister.addActionListener(e -> xuLyDangKy());
        getContentPane().add(btnRegister);
    }

    private void addInputFields() {
        txtHoTen = createField("Họ Tên:", 70);
        txtTaiKhoan = createField("Tài Khoản:", 120);
        txtMatKhau = createPasswordField("Mật Khẩu:", 170);
        txtSDT = createField("Số Điện Thoại:", 220);
        txtDiaChi = createField("Địa Chỉ:", 270);
    }

    private JTextField createField(String label, int y) {
        JLabel lbl = new JLabel(label); lbl.setForeground(Color.WHITE); lbl.setBounds(50, y, 100, 25);
        getContentPane().add(lbl);
        JTextField txt = new JTextField();
        txt.setBounds(150, y, 180, 25);
        getContentPane().add(txt);
        return txt;
    }

    private JPasswordField createPasswordField(String label, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(50, y, 100, 25);
        getContentPane().add(lbl);
        JPasswordField txt = new JPasswordField(); 
        txt.setBounds(150, y, 180, 25);
        getContentPane().add(txt);
        return txt;
    }

    private void xuLyDangKy() {
        NguoiDungEntity user = new NguoiDungEntity();
        user.setMaND("ND" + System.currentTimeMillis() % 10000);
        user.setHoTen(txtHoTen.getText());
        user.setTaiKhoan(txtTaiKhoan.getText());
        user.setMatKhau(new String(txtMatKhau.getPassword()));
        user.setSDT(txtSDT.getText());
        user.setDiaChi(txtDiaChi.getText());

        if(new NguoiDungDAO().dangKy(user)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
        }
    }
}