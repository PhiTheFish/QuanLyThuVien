package Main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuanLySach extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtMaSach, txtTenSach, txtTacGia, txtNXB;
    private JTable table;
    private DefaultTableModel model;

    // Kết nối CSD
    String url = "jdbc:sqlserver://DESKTOP-ASI6E70\\SQLEXPRESS:1433;databaseName=QuanLyThuvien;encrypt=true;trustServerCertificate=true;";
    String user = "admin";
    String pass = "123456";

    public QuanLySach() {
        setTitle("Phần Mềm Quản Lý Thư Viện - Quản Lý Sách");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        //Tiêu đề
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(230, 235, 245));
        JLabel lblTitle = new JLabel("📚 Danh Sách Sách Trong Thư Viện");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 100, 0));
        panelTop.add(lblTitle);
        add(panelTop, BorderLayout.NORTH);

        // Bảng ở giữa
        model = new DefaultTableModel();
        model.addColumn("Mã Sách");
        model.addColumn("Tên Sách");
        model.addColumn("Tác Giả");
        model.addColumn("Nhà Xuất Bản");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Nhập và nút
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        
        // Các ô nhập liệu (Bên trái)
        JPanel panelFields = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFields.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Thông tin chi tiết", 
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.BLUE
        ));

        panelFields.add(new JLabel("  Mã Sách:"));
        txtMaSach = new JTextField();
        txtMaSach.setEditable(false); // Mã tự tăng nên không cho sửa trực tiếp
        txtMaSach.setBackground(new Color(240, 240, 240));
        panelFields.add(txtMaSach);

        panelFields.add(new JLabel("  Tên Sách:"));
        txtTenSach = new JTextField();
        panelFields.add(txtTenSach);

        panelFields.add(new JLabel("  Tác Giả:"));
        txtTacGia = new JTextField();
        panelFields.add(txtTacGia);

        panelFields.add(new JLabel("  Nhà Xuất Bản:"));
        txtNXB = new JTextField();
        panelFields.add(txtNXB);

        // Các nút thao tác ADD, UPDATE, DELETE (Bên phải)
        JPanel panelButtons = new JPanel(new GridLayout(3, 1, 10, 15));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnAdd = new JButton("➕ ADD");
        JButton btnUpdate = new JButton("🔄 Update");
        JButton btnDelete = new JButton("❌ Delete");
        
        btnAdd.setBackground(new Color(220, 245, 220));
        btnUpdate.setBackground(new Color(220, 235, 255));
        btnDelete.setBackground(new Color(255, 220, 220));

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);

        panelBottom.add(panelFields, BorderLayout.CENTER);
        panelBottom.add(panelButtons, BorderLayout.EAST);

        // Gộp bảng và phần nhập liệu lại
        JPanel panelCenterMain = new JPanel(new BorderLayout(5, 5));
        panelCenterMain.add(scrollPane, BorderLayout.CENTER);
        panelCenterMain.add(panelBottom, BorderLayout.SOUTH);
        
        add(panelCenterMain, BorderLayout.CENTER);

        // Cho dữ liệu từ SQL lên bảng
        loadDataToTable();

        // Ấn trên bảng
        table.getSelectionModel().addListSelectionListener(_ -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSach.setText(table.getValueAt(row, 0).toString());
                txtTenSach.setText(table.getValueAt(row, 1).toString());
                txtTacGia.setText(table.getValueAt(row, 2).toString());
                txtNXB.setText(table.getValueAt(row, 3).toString());
            }
        });

        // Nút ADD
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtTenSach.getText().isEmpty() || txtTacGia.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sách và tác giả!");
                    return;
                }
                try {
                    Connection conn = DriverManager.getConnection(url, user, pass);
                    String sql = "INSERT INTO Sach (TenSach, TacGia, NhaXuatBản) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, txtTenSach.getText());
                    pstmt.setString(2, txtTacGia.getText());
                    pstmt.setString(3, txtNXB.getText());
                    pstmt.executeUpdate();
                    conn.close();
                    
                    JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    loadDataToTable();
                    clearForm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi thêm: " + ex.getMessage());
                }
            }
        });

        // Nút UPDATE
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa trên bảng!");
                    return;
                }
                try {
                    Connection conn = DriverManager.getConnection(url, user, pass);
                    String sql = "UPDATE Sach SET TenSach = ?, TacGia = ?, NhaXuatBản = ? WHERE MaSach = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, txtTenSach.getText());
                    pstmt.setString(2, txtTacGia.getText());
                    pstmt.setString(3, txtNXB.getText());
                    pstmt.setString(4, txtMaSach.getText());
                    pstmt.executeUpdate();
                    conn.close();
                    
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadDataToTable();
                    clearForm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi cập nhật: " + ex.getMessage());
                }
            }
        });

        // Nút Xóa
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa trên bảng!");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa cuốn sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Connection conn = DriverManager.getConnection(url, user, pass);
                        String sql = "DELETE FROM Sach WHERE MaSach = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, txtMaSach.getText());
                        pstmt.executeUpdate();
                        conn.close();
                        
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        loadDataToTable();
                        clearForm();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi xóa: " + ex.getMessage());
                    }
                }
            }
        });
    }

    // Hàm tải dữ liệu từ cơ sở dữ liệu SQL Server lên JTable
    public void loadDataToTable() {
        model.setRowCount(0);
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Sach");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getString("TacGia"),
                    rs.getString("NhaXuatBản")
                });
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm làm trống các ô nhập liệu sau khi thao tác
    public void clearForm() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtTacGia.setText("");
        txtNXB.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanLySach().setVisible(true);
        });
    }
}