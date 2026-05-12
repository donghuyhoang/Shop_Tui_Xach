package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.NguoiDungDAO;
import entity.NguoiDungEntity;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTaiKhoan;
	private JTextField textMatKhau;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN PAGE");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(168, 36, 111, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User:");
		lblNewLabel_1.setBounds(115, 84, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(115, 115, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		textTaiKhoan = new JTextField();
		textTaiKhoan.setBounds(183, 81, 96, 20);
		contentPane.add(textTaiKhoan);
		textTaiKhoan.setColumns(10);
		
		textMatKhau = new JTextField();
		textMatKhau.setBounds(183, 112, 96, 20);
		contentPane.add(textMatKhau);
		textMatKhau.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String TaiKhoan = textTaiKhoan.getText();
				String MatKhau = textMatKhau.getText();
				if(TaiKhoan.isEmpty() || MatKhau.isEmpty()){
					JOptionPane.showMessageDialog(Login.this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
				    return;
				}
				NguoiDungDAO nguoidungdao = new NguoiDungDAO();
				NguoiDungEntity user = nguoidungdao.checklogin(TaiKhoan, MatKhau);
				if(user != null) {
					JOptionPane.showMessageDialog(Login.this, "Đăng nhập thành công! Xin chào " + user.getHoTen());
				    Login.this.dispose(); 
				    
				    // Đã xóa tham số user bị dư thừa ở đây
				    MainStaffView mainForm = new MainStaffView();
				    mainForm.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(Login.this, "Sai tài khoản hoặc mật khẩu!");
				}
			}
		});
		btnLogin.setBounds(163, 155, 88, 22);
		contentPane.add(btnLogin);
	}
}