# QuanLyThuVien

Ứng dụng quản lý thư viện được xây dựng bằng **Java Swing** (Giao diện desktop), kết nối cơ sở dữ liệu **Microsoft Access (`.accdb`)** thông qua thư viện **UCanAccess**.

## Hướng dẫn cài đặt và chạy ứng dụng

### Bước 1: Tải/Clone Project
   * Tải mã nguồn project về máy tính và giải nén (hoặc Clone từ GitHub).

### Bước 2: Import Project
1. Mở **Eclipse** > Chọn **File** > **Import...**
2. Chọn **General** > **Existing Projects into Workspace** > Bấm **Next**.
3. Tại ô **Select root directory**, bấm **Browse...** và trỏ đến thư mục `QuanLyThuVien`.
4. Nhấn **Finish**.

### Bước 3: Cấu hình lại Build Path (Bắt buộc nếu bị lỗi đỏ/vàng)
Nếu project xuất hiện dấu chấm than vàng hoặc báo lỗi `Archive for required library ... does not exist`:
1. Click chuột phải vào project **`QuanLyThuVien`** > Chọn **Properties**.
2. Chọn **Java Build Path** > Chuyển sang tab **Libraries** (hoặc chọn mục **Classpath**).
3. Nếu thấy file `.jar` cũ bị báo lỗi màu đỏ, bấm chọn và nhấn **Remove**.
4. Chọn mục **Classpath** (hoặc **Libraries**) > Bấm **Add JARs...**
5. Tìm mở thư mục `QuanLyThuVien/libraries` > Chọn file `ucanaccess-5.1.2-uber.jar` > Bấm **OK**.
6. Nhấn **Apply and Close**.
7. Trên thanh thực đơn, chọn **Project** > **Clean...** > Bấm **OK**.

### Bước 4: Chạy ứng dụng
1. Mở thư mục `src` > package `Main`.
2. Click chuột phải vào file **`DangNhap.java`** > Chọn **Run As** > **Java Application**.

---

## 🔑 Tài khoản đăng nhập mặc định
* **Tài khoản (Username):** `admin`
* **Mật khẩu (Password):** `123456`

---

## ✨ Các tính năng chính
*  **Đăng nhập hệ thống:** Bảo mật đăng nhập kiểm tra trực tiếp từ CSDL.
*  **Hiển thị danh sách sách:** Lấy dữ liệu từ MS Access tải lên bảng `JTable`.
*  **Thêm sách mới:** Tự động lưu thông tin sách mới vào CSDL.
*  **Cập nhật thông tin:** Sửa thông tin sách trực tiếp khi chọn dòng trên bảng.
*  **Xóa sách:** Xóa thông tin sách khỏi thư viện với hộp thoại xác nhận an toàn.
