package view;

import java.awt.*;
import javax.swing.*;
import entity.NguoiDungEntity;

public class MainCustomerView extends JFrame {
    private JPanel panelCenter;
    private NguoiDungEntity currentUser;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainCustomerView frame = new MainCustomerView(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainCustomerView(NguoiDungEntity user) {
        this.currentUser = user;
        
        String tenKhach = (user == null) ? "Khách vãng lai" : user.getHoTen();
        setTitle("Shop Túi Xách - Khách hàng: " + tenKhach);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 650);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Thanh menut trái
        JPanel pnlMenu = new JPanel(new GridLayout(6, 1, 0, 10));
        pnlMenu.setBackground(new Color(40, 40, 40));
        pnlMenu.setPreferredSize(new Dimension(200, 0));
        contentPane.add(pnlMenu, BorderLayout.WEST);

        pnlMenu.add(new JLabel(" SHOPPING ", JLabel.CENTER) {{ 
            setForeground(new Color(255, 191, 0)); 
            setFont(new Font("Segoe UI", Font.BOLD, 18)); 
        }});

        JButton btnShop = createMenuBtn("MUA SẮM");
        pnlMenu.add(btnShop);

        // Vùng hiển thị chính
        panelCenter = new JPanel(new CardLayout());
        contentPane.add(panelCenter, BorderLayout.CENTER);

        // Nạp trang Mua sắm (Truyền user vào)
        BanHangPanel shopPanel = new BanHangPanel(false, user); 
        panelCenter.add(shopPanel, "Shop");

        // Khối logic: nếu đã đăng nhập
        if (user != null) {
            JButton btnProfile = createMenuBtn("TRANG CÁ NHÂN");
            JButton btnLogout = createMenuBtn("ĐĂNG XUẤT");

            pnlMenu.add(btnProfile);
            pnlMenu.add(new JLabel());
            pnlMenu.add(btnLogout);

            ProfilePanel profilePanel = new ProfilePanel(user);
            panelCenter.add(profilePanel, "Profile");

            // Sự kiện chuyển trang
            setActiveTab(btnShop, btnProfile);
            btnShop.addActionListener(e -> {
                ((CardLayout)panelCenter.getLayout()).show(panelCenter, "Shop");
                setActiveTab(btnShop, btnProfile);
            });
            btnProfile.addActionListener(e -> {
                ((CardLayout)panelCenter.getLayout()).show(panelCenter, "Profile");
                setActiveTab(btnProfile, btnShop);
            });
            btnLogout.addActionListener(e -> {
                this.dispose(); // Đóng trang hiện tại (đang có user)
                // Quay lại trang chủ bình thường (truyền null để làm khách vãng lai)
                new MainCustomerView(null).setVisible(true); 
            });
        } 
        // Khối static: nếu là khách vãn lai
        else {
            JButton btnLogin = createMenuBtn("ĐĂNG NHẬP");
            pnlMenu.add(btnLogin);
            
            btnLogin.addActionListener(e -> {
                // 1. Mở cửa sổ đăng nhập (Nó sẽ tự động tạm dừng luồng ở đây)
                Login loginDlg = new Login();
                loginDlg.setVisible(true); 
                
                // 2. Chờ người dùng đăng nhập xong, lấy kết quả
                NguoiDungEntity userLogin = loginDlg.getLoggedInUser();
                
                // 3. Nếu đăng nhập thành công
                if (userLogin != null) {
                    this.dispose(); // Đóng trang khách cũ
                    
                    if (userLogin.getVaiTro().equalsIgnoreCase("Khách hàng")) {
                        new MainCustomerView(userLogin).setVisible(true); // Mở trang khách hàng mới
                    } else {
                        new MainStaffView().setVisible(true); // Nếu là nhân viên thì vào trang quản lý
                    }
                }
            });
        }
    }

    private JButton createMenuBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 40, 40));
        btn.setFocusPainted(false);
        return btn;
    }

    private void setActiveTab(JButton active, JButton inactive) {
        active.setBackground(new Color(70, 70, 70)); 
        active.setForeground(new Color(255, 191, 0)); 
        inactive.setBackground(new Color(40, 40, 40)); 
        inactive.setForeground(Color.WHITE); 
    }
    
 // Hàm dùng để vẽ lại Menu sau khi đăng nhập thành công
    public void refreshMenu(entity.NguoiDungEntity user) {
        this.currentUser = user;
        this.dispose(); // Đóng trang hiện tại
        new MainCustomerView(user).setVisible(true); // Mở lại với thông tin User đã đăng nhập
    }
}