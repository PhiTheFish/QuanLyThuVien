# QuanLyThuVien

Ứng dụng quản lý thư viện được xây dựng bằng **Java Swing** (Giao diện desktop), kết nối cơ sở dữ liệu **Microsoft Access (`.accdb`)** thông qua thư viện **UCanAccess**.

## Hướng dẫn cài đặt và chạy ứng dụng

1. **Tải/Clone Project:**
   * Tải mã nguồn project về máy tính và giải nén (hoặc Clone từ GitHub).

2. **Mở Project trong IDE (Eclipse / NetBeans / IntelliJ):**
   * Mở IDE, chọn **Import Existing Projects into Workspace** và trỏ đến thư mục `QuanLyThuVien`.

3. **Cấu hình Build Path (Thư viện JDBC):**
   * Đảm bảo file thư viện `ucanaccess-5.1.2-uber.jar` trong thư mục `libraries` đã được thêm vào **Java Build Path** của project.

4. **Cơ sở dữ liệu:**
   * File cơ sở dữ liệu `QuanLyThuvien.accdb` đã được đặt sẵn ở thư mục gốc của project. 
   * Chương trình sử dụng đường dẫn tương đối để kết nối, không cần cài đặt SQL Server hay cấu hình server complex.

5. **Chạy chương trình:**
   * Tìm đến file `src/Main/DangNhap.java`.
   * Click chuột phải chọn **Run As** > **Java Application**.
   * **Tài khoản đăng nhập mặc định:** 
     * Username: `admin`
     * Password: `123456`
