package view;

import java.awt.EventQueue;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.NguoiDungDAO;
import entity.NguoiDungEntity;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTaiKhoan;
	private JPasswordField textMatKhau;
	private NguoiDungEntity loggedInUser = null;

	// Hàm main để test giao diện Login độc lập
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
					dialog.setVisible(true);
					
					if (dialog.getLoggedInUser() != null) {
						System.out.println("Đăng nhập OK: " + dialog.getLoggedInUser().getHoTen());
					} else {
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Đăng Nhập Hệ Thống");
		setModal(true);
		setBounds(100, 100, 450, 350); 
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(24, 24, 24)); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SHOP TÚI XÁCH", JLabel.CENTER);
		lblNewLabel.setForeground(new Color(255, 191, 0)); 
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblNewLabel.setBounds(0, 30, 434, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblUser = new JLabel("Tài khoản:");
		lblUser.setForeground(Color.WHITE);
		lblUser.setBounds(100, 90, 80, 20);
		contentPane.add(lblUser);
		
		textTaiKhoan = new JTextField();
		textTaiKhoan.setBounds(180, 90, 150, 25);
		contentPane.add(textTaiKhoan);
		
		JLabel lblPass = new JLabel("Mật khẩu:");
		lblPass.setForeground(Color.WHITE);
		lblPass.setBounds(100, 130, 80, 20);
		contentPane.add(lblPass);
		
		textMatKhau = new JPasswordField();
		textMatKhau.setBounds(180, 130, 150, 25);
		contentPane.add(textMatKhau);
		
		JButton btnLogin = new JButton("Đăng Nhập");
		btnLogin.setBackground(new Color(255, 191, 0));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBounds(100, 180, 230, 35);
		btnLogin.addActionListener(e -> {
			String TaiKhoan = textTaiKhoan.getText();
			String MatKhau = new String(textMatKhau.getPassword());
			
			if(TaiKhoan.isEmpty() || MatKhau.isEmpty()){
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
			    return;
			}
			
			NguoiDungDAO dao = new NguoiDungDAO();
			NguoiDungEntity user = dao.checklogin(TaiKhoan, MatKhau);
			
			if(user != null) {
				this.loggedInUser = user; 
				JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
			    this.dispose(); 
			} else {
				JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnLogin);

		JButton btnRegister = new JButton("Chưa có tài khoản? Đăng ký ngay");
		btnRegister.setForeground(new Color(255, 191, 0));
		btnRegister.setContentAreaFilled(false); 
		btnRegister.setBorderPainted(false);
		btnRegister.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btnRegister.setBounds(100, 225, 230, 20);
		btnRegister.addActionListener(e -> {
			RegisterDialog reg = new RegisterDialog();
			reg.setVisible(true);
		});
		contentPane.add(btnRegister);
		
		JLabel lblForgot = new JLabel("Quên mật khẩu?", JLabel.CENTER);
		lblForgot.setForeground(Color.GRAY);
		lblForgot.setBounds(0, 260, 434, 20);
		contentPane.add(lblForgot);
	}

	public NguoiDungEntity getLoggedInUser() {
		return loggedInUser;
	}
}