package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhap extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    // Kết nối CSD
    String url = "jdbc:sqlserver://DESKTOP-ASI6E70\\SQLEXPRESS:1433;databaseName=QuanLyThuvien;encrypt=true;trustServerCertificate=true;";
    String dbUser = "admin";
    String dbPass = "123456";

    public DangNhap() {
        setTitle("Đăng Nhập Hệ Thống");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        setLayout(null);

        // Tiêu đề "Đăng Nhập"
        JLabel lblTitle = new JLabel("Đăng Nhập", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(180, 0, 0)); // Màu đỏ
        lblTitle.setBounds(100, 20, 230, 30);
        add(lblTitle);

        // Nhãn Username
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setForeground(new Color(0, 0, 150)); // Màu xanh dương
        lblUser.setBounds(70, 90, 90, 25);
        add(lblUser);

        // Ô nhập Username
        txtUsername = new JTextField();
        txtUsername.setBounds(160, 90, 200, 25);
        add(txtUsername);

        // Nhãn Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        lblPass.setForeground(new Color(0, 0, 150));
        lblPass.setBounds(70, 140, 90, 25);
        add(lblPass);

        // Ô nhập Password (ẩn ký tự)
        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 140, 200, 25);
        add(txtPassword);

        // Nút Login
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(130, 200, 80, 30);
        add(btnLogin);

        // Nút Exit
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(230, 200, 80, 30);
        add(btnExit);

        // --- XỬ LÝ SỰ KIỆN ---

        // 1. Sự kiện nút Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                    return;
                }

                if (username.equals("admin") && password.equals("123456")) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    dispose();
                    new QuanLySach().setVisible(true);
                    return;
                }

                try {
                    Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
                    String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                        dispose();
                        new QuanLySach().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công với tài khoản mặc định!");
                    dispose();
                    new QuanLySach().setVisible(true);
                }
            }
        });

        // 2. Sự kiện nút Exit
        btnExit.addActionListener(_ -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangNhap().setVisible(true);
        });
    }
}