<h1 style="color:red">Register step by step</h1>
<ul>
    <li style="font-size:18px; font-weight:500">Người dùng đăng ký nhập thông tin tài khoản</li>
    <li style="font-size:18px; font-weight:500">Thông tin đăng ký được nhận trong class <b><i style="color:green">RegistationRequest</i></b></li>
    <li style="font-size:18px; font-weight:500">Tạo user mới với <i style="color:red">token</i> được lưu</li>
    <li style="font-size:18px; font-weight:500">Gửi mail cùng với <i style="color:red">token</i></li>
    <li style="font-size:18px; font-weight:500">Nếu người dùng ấn vào link --> Kiểm tra token được gửi theo mail dó</li>
    <li style="font-size:18px; font-weight:500">Nếu token có tồn tại --> Kiểm tra xem hết thời hạn chưa</li>
    <li style="font-size:18px; font-weight:500">Enable nếu chưa hết thời hạn --> enable cho tài khoản</li>
    <li style="font-size:18px; font-weight:500">Enable nếu hết thời hạn --> Thông báo cho người dùng cho người ta ấn gửi lại <i style="color:red">token</i></li>
    <li style="font-size:18px; font-weight:500">Thêm <i style="color:red">token</i> mới vào csdl</li>
    <li style="font-size:18px; font-weight:500">Nếu người dùng không nhận được mã --> Cho phép ấn gửi mã</li>
    <li style="font-size:18px; font-weight:500">Nếu người dùng đăng nhập bằng tài khoản chưa enable --> Đưa vào trang xác thực</li>
</ul>
<h2>sơ đồ chức năng (Controller - Service)</h2>
<pre>
register(Authcontroller)-->register(MyUserDetailService)
-->register(RegisterService) --> sendVerificationMail(RegisterService)
</pre>

