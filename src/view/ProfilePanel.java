package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import entity.NguoiDungEntity;
import dao.NguoiDungDAO;

public class ProfilePanel extends JPanel {
    private JTextField txtHoTen, txtSDT, txtEmail, txtDiaChi;
    private JPasswordField txtPassCu, txtPassMoi;
    private NguoiDungEntity user;

    public ProfilePanel(NguoiDungEntity user) {
        this.user = user;
        setLayout(null);
        setBackground(new Color(24, 24, 24));

        // Thông tin các nhân
        JPanel pnlInfo = new JPanel(null);
        pnlInfo.setBackground(new Color(33, 33, 33));
        pnlInfo.setBorder(BorderFactory.createTitledBorder(null, "THÔNG TIN TÀI KHOẢN", TitledBorder.DEFAULT_JUSTIFICATION,
        													TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.BOLD, 14),
        													Color.WHITE));
        pnlInfo.setBounds(50, 30, 700, 200);
        add(pnlInfo);

        addLabelAndField(pnlInfo, "Họ tên:", txtHoTen = new JTextField(user.getHoTen()), 30);
        addLabelAndField(pnlInfo, "Số điện thoại:", txtSDT = new JTextField(user.getSDT()), 70);
        addLabelAndField(pnlInfo, "Email:", txtEmail = new JTextField(user.getEmail()), 110);
        addLabelAndField(pnlInfo, "Địa chỉ:", txtDiaChi = new JTextField(user.getDiaChi()), 150);

        // Đổi mật khẩu
        JPanel pnlPass = new JPanel(null);
        pnlPass.setBackground(new Color(33, 33, 33));
        pnlPass.setBorder(BorderFactory.createTitledBorder(null, "ĐỔI MẬT KHẨU", TitledBorder.DEFAULT_JUSTIFICATION,
        													TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.BOLD, 14),
        													Color.WHITE));
        pnlPass.setBounds(50, 250, 700, 180);
        add(pnlPass);

        addLabelAndField(pnlPass, "Mật khẩu cũ:", txtPassCu = new JPasswordField(), 40);
        addLabelAndField(pnlPass, "Mật khẩu mới:", txtPassMoi = new JPasswordField(), 90);

        JButton btnUpdate = new JButton("CẬP NHẬT THÔNG TIN");
        btnUpdate.setBackground(new Color(255, 191, 0));
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUpdate.setBounds(300, 450, 200, 40);
        btnUpdate.addActionListener(e -> xuLyCapNhat());
        add(btnUpdate);
    }

    private void addLabelAndField(JPanel pnl, String text, JTextField field, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(50, y, 120, 25);
        pnl.add(lbl);
        field.setBounds(180, y, 450, 25);
        pnl.add(field);
    }

    private void xuLyCapNhat() {
        // 1. Cập nhật dữ liệu từ các ô nhập liệu vào đối tượng user
        user.setHoTen(txtHoTen.getText().trim());
        user.setSDT(txtSDT.getText().trim());
        user.setEmail(txtEmail.getText().trim());
        user.setDiaChi(txtDiaChi.getText().trim());

        String passCu = new String(txtPassCu.getPassword());
        String passMoi = new String(txtPassMoi.getPassword());

        // 2. Kiểm tra nếu người dùng muốn đổi mật khẩu
        if (!passMoi.isEmpty()) {
            if (!passCu.equals(user.getMatKhau())) {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 3. Gọi DAO để lưu xuống CSDL
        NguoiDungDAO dao = new NguoiDungDAO();
        if (dao.capNhatThongTin(user, passMoi.isEmpty() ? null : passMoi)) {
            // Cập nhật lại mật khẩu trong bộ nhớ tạm nếu đổi thành công
            if (!passMoi.isEmpty()) {
                user.setMatKhau(passMoi); 
            }
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
            
            // Xóa trắng ô nhập mật khẩu sau khi đổi xong
            txtPassCu.setText("");
            txtPassMoi.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống! Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}