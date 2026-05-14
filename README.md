
👜 Dự Án Quản Lý Cửa Hàng Túi Xách (Java Swing)
Đây là ứng dụng Desktop được xây dựng bằng ngôn ngữ Java, hỗ trợ quản lý việc bán hàng, kho túi xách và theo dõi đơn hàng dành cho cả khách hàng và nhân viên cửa hàng.

🚀 Tính Năng Chính
👤 Đối với Khách Hàng (MainCustomerView)
Xem Sản Phẩm: Duyệt danh sách túi xách theo loại, thương hiệu.

Giỏ Hàng: Thêm sản phẩm vào giỏ và đặt hàng trực tuyến.

Tài Khoản: Đăng ký, đăng nhập và quản lý thông tin cá nhân.

🛠️ Đối với Nhân Viên & Quản Lý (MainStaffView)
Bán Hàng Tại Quầy: Tạo hóa đơn trực tiếp cho khách tại shop.

Quản Lý Kho: Thêm, sửa, xóa thông tin sản phẩm, loại túi và thương hiệu.

Quản Lý Hóa Đơn: Theo dõi trạng thái đơn hàng và duyệt đơn online từ khách hàng.

Thống Kê: Theo dõi doanh thu và số lượng hàng bán ra.

🛠️ Công Nghệ Sử Dụng
Ngôn ngữ: Java (JDK 8+)

Giao diện: Java Swing & AWT.

Cơ sở dữ liệu: MySQL.

Thư viện: JDBC (MySQL Connector/J).

Kiến trúc: MVC (Model - View - Controller) kết hợp DAO Pattern.

📋 Hướng Dẫn Cài Đặt
1. Chuẩn bị Cơ sở dữ liệu (Database)
Mở XAMPP hoặc MySQL Workbench.

Tạo một database mới tên là: shoptuixach.

Import file .sql (nếu có) hoặc tạo các bảng theo cấu hình trong package entity.

2. Cấu hình Kết nối
Mở file mã nguồn tại đường dẫn:
src/utils/ConnectionJDBCUtil.java

Chỉnh sửa thông tin tài khoản MySQL của bạn:

Java
static final String USER = "root";      // Tên user MySQL của bạn
static final String PASS = "your_pass"; // Mật khẩu MySQL của bạn
3. Thêm thư viện (Library)
Đảm bảo bạn đã thêm file mysql-connector-j-x.x.x.jar vào Build Path của dự án trong Eclipse/IntelliJ.

🏁 Hướng Dẫn Chạy Chương Trình
Để khởi động ứng dụng đúng luồng, bạn hãy thực hiện theo thứ tự sau:

Tìm đến thư mục src/view/.

Click chuột phải vào file MainCustomerView.java.

Chọn Run As -> Java Application.

Lưu ý: Bạn có thể chọn "Đăng nhập" từ Menu để chuyển sang giao diện Quản lý nếu tài khoản có vai trò là Nhân viên hoặc Admin.

📂 Cấu Trúc Thư Mục Chính
src/controller: Điều hướng dữ liệu giữa View và DAO.

src/dao: Thực hiện các câu lệnh truy vấn SQL xuống Database.

src/entity: Các lớp đối tượng ánh xạ từ bảng trong CSDL.

src/view: Giao diện người dùng (JFrame, JPanel, JDialog).

src/utils: Các tiện ích như kết nối JDBC.
