# SpringBoot Core Template
Bộ core sau được tham khảo và xây dựng từ nhiều dự án khác nhau, vui lòng không sử dụng với mục đích kinh doanh khi không có sự cho phép từ tác giả

## Configuration
Chỉnh sửa <strong>Port, Tên chương trình, cấu hình database</strong> tại [đường dẫn](business-api/src/main/resources/application.properties) sau

## Installation
Nếu có bất kỳ sự cố gì xảy ra trong quá trình build:
- Bước 1: Vào File &#8594; Repair IDE
- Bước 2: Vào Mục Maven &#8594; Reload All Maven Projects
- Bước 3: Nếu Bước 1 và Bước 2 không được &#8594; Vào File &#8594; Invalidate Caches &#8594; Tích 3 mục đầu
- Nếu tất cả đều không được &#8594; Better call Tiến :v


## Contributing
Vì đây là bộ core template nên tuyệt đối không được push lên git khi chưa được review code

Khi pull code về &#8594; Vào thư mục chứa code &#8594; Xóa file .git &#8594; Tự tạo repo git mới và chạy các lệnh

```bash
git init
git add .
git commit -m "Core init"
git branch -M main
git remote add origin https://github.com/<Tên github>/<Tên repo đã tạo>.git 
git push -u origin main
```

Lưu ý update code core khi có bản patch mới!
