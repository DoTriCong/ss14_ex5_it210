Phần 1: Thiết kế kiến trúc (Architecture Design)
Entity & Quan hệ
Wallet: quản lý số dư của khách hàng.

Vendor: nhà cung cấp sản phẩm.

Product: sản phẩm thuộc về Vendor, có số lượng tồn kho.

Order: đơn hàng tổng, gắn với khách hàng và nhiều Vendor.

OrderDetail: chi tiết đơn hàng, mỗi dòng gắn với một Product và Vendor.

Quan hệ:

Order 1–N OrderDetail.

OrderDetail liên kết tới Product.

Product thuộc về Vendor.

Wallet liên kết tới Customer.

Luồng dữ liệu (Data Flow):

Menu Console → Controller nhận input → Service xử lý nghiệp vụ (Wallet, Product, Order, Vendor) → DAO thao tác DB → Transaction tổng quản lý các transaction con cho từng Vendor → kết quả trả về Console.

Phần 2: Phân tích rủi ro (Edge Cases) 

Liệt kê kịch bản lỗi:

Mất kết nối Database khi đang thanh toán

Giải pháp: rollback transaction tổng, không trừ tiền.

Sản phẩm bị xóa khỏi danh mục ngay lúc thanh toán

Giải pháp: kiểm tra tồn tại sản phẩm trước khi trừ kho, nếu không tồn tại → rollback.

Khách hàng nhập số âm hoặc ký tự khi nạp tiền

Giải pháp: validate input, bắt ngoại lệ, yêu cầu nhập lại.

Một Vendor hết hàng trong khi Vendor khác còn hàng

Giải pháp: rollback toàn bộ transaction tổng để đảm bảo tính nguyên tử.