package nice.store.datn.service;


import nice.store.datn.config.PasswordUtil;
import nice.store.datn.config.SHA256PasswordEncoder;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.NhanVien;
import nice.store.datn.entity.Role;
import nice.store.datn.entity.RoleEnum;
import nice.store.datn.repository.KhachHangRepository;
import nice.store.datn.repository.NhanVienrepository;
import nice.store.datn.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienrepository nhanVienRepository;

    @Autowired
    private EmailService emailService;

    //dùng hàm nay
    public SHA256PasswordEncoder passwordEncoder() {
        return new SHA256PasswordEncoder();
    }
    private final Map<String, String> verificationCodes = new HashMap<>(); // Lưu trữ mã xác nhận tạm thời

    public KhachHang authenticateKhachHang(String email, String rawPassword) {
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang != null && passwordEncoder().matches(rawPassword, khachHang.getMatKhau())) {
            return khachHang;
        }
        return null;
    }


    public NhanVien authenticateNhanVien(String email, String rawPassword) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        if (nhanVien != null && passwordEncoder().matches(rawPassword,nhanVien.getMatKhau())) {
            return nhanVien;
        }
        return null;
    }

    // Đăng ký khách hàng
    public KhachHang registerKhachHang(String email, String password) {
        if (khachHangRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email đã tồn tại");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setEmail(email);
        khachHang.setMatKhau(PasswordUtil.hashPassword(password)); // Mã hóa mật khẩu
        khachHang.setNgayTao(LocalDateTime.now());
        khachHang.setTrangThai(1); // 1: Hoạt động
        Role roleKhachHang = roleRepository.findById(RoleEnum.KHACH_HANG.getValue())
                .orElseThrow(() -> new RuntimeException("Role KHACH_HANG không tồn tại"));

        // Gán Role cho khachHang
        khachHang.setIdRole(roleKhachHang);
        khachHangRepository.save(khachHang);

        // Gửi email thông báo
        String subject = "Tạo tài khoản thành công";
        String text = "Chào mừng bạn đến với Nice Store!\n\n"
                + "Thông tin tài khoản của bạn:\n"
                + "Email: " + email + "\n"
                + "Mật khẩu: " + password + "\n\n"
                + "Vui lòng đổi mật khẩu sau khi đăng nhập.";
        emailService.sendEmail(email, subject, text);

        return khachHang;
    }

    public void sendVerificationCode(String email) {
        // Kiểm tra email có tồn tại trong danh sách khách hàng không
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang == null) {
            throw new RuntimeException("Email không tồn tại trong hệ thống");
        }

        // Tạo mã xác nhận
        String code = generateVerificationCode();
        verificationCodes.put(email, code); // Lưu mã vào bộ nhớ tạm

        // Gửi email chứa mã xác nhận
        String subject = "Mã xác nhận đặt lại mật khẩu";
        String text = "Mã xác nhận của bạn là: " + code + "\n\n"
                + "Vui lòng nhập mã này để đặt lại mật khẩu.";
        emailService.sendEmail(email, subject, text);
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        return savedCode != null && savedCode.equals(code);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Mã 6 chữ số
        return String.valueOf(code);
    }
    @Transactional
    public void resetPassword(String email, String newPassword) {
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang == null) {
            throw new RuntimeException("Email không tồn tại");
        }

        String hashedPassword = PasswordUtil.hashPassword(newPassword);

        khachHang.setMatKhau(hashedPassword);

        khachHangRepository.save(khachHang);

        verificationCodes.remove(email);
    }
}