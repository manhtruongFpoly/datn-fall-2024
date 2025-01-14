package nice.store.datn.controller;

import jakarta.servlet.http.HttpSession;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.NhanVien;
import nice.store.datn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    // Hiển thị trang đăng nhập
    @GetMapping("/dang-nhap")
    public String showLoginPage() {
        return "user/login"; // Trả về tên template HTML (login.html)
    }

    @PostMapping("/dang-nhap")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        // Thử đăng nhập với vai trò khách hàng
        KhachHang khachHang = authService.authenticateKhachHang(email, password);
        if (khachHang != null) {
            session.setAttribute("user", khachHang);
            return "redirect:/home"; // Chuyển hướng đến trang chính cho khách hàng
        }

        // Thử đăng nhập với vai trò nhân viên
        NhanVien nhanVien = authService.authenticateNhanVien(email, password);
        if (nhanVien != null) {
            return "redirect:/admin"; // Chuyển hướng đến trang admin cho nhân viên
        }

        // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
        model.addAttribute("error", "Email hoặc mật khẩu không đúng");
        return "user/login"; // Trả về trang đăng nhập với thông báo lỗi
    }

    // Hiển thị trang đăng ký
    @GetMapping("/dang-ky")
    public String showRegisterPage() {
        return "user/registration";
    }

    // Xử lý đăng ký
    @PostMapping("/dang-ky")
    public String register(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            authService.registerKhachHang(email, password);
            return "redirect:/dang-nhap"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        } catch (RuntimeException e) {
            // Nếu có lỗi (ví dụ: email đã tồn tại), hiển thị thông báo lỗi
            model.addAttribute("error", e.getMessage());
            return "user/registration"; // Trả về trang đăng ký với thông báo lỗi
        }
    }

    @GetMapping("/quen-mat-khau")
    public String showForgotPasswordPage() {
        return "user/QuenMK"; // Trả về trang quên mật khẩu
    }

    // Xử lý yêu cầu quên mật khẩu
    @PostMapping("/quen-mat-khau")
    public String forgotPassword(
            @RequestParam String email,
            Model model
    ) {
        try {
            authService.sendVerificationCode(email);
            return "redirect:/verify-code?email=" + email; // Chuyển hướng đến trang xác nhận mã
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user/QuenMK";
        }
    }

    // Hiển thị trang xác nhận mã
    @GetMapping("/verify-code")
    public String showVerifyCodePage(
            @RequestParam String email,
            Model model
    ) {
        model.addAttribute("email", email); // Truyền email vào trang xác nhận mã
        return "user/verifycode"; // Trả về trang xác nhận mã
    }

    // Xử lý xác nhận mã
    @PostMapping("/verify-code")
    public String verifyCode(
            @RequestParam String email,
            @RequestParam String code,
            Model model
    ) {
        try {
            if (!authService.verifyCode(email, code)) {
                model.addAttribute("error", "Mã xác nhận không đúng");
                return "user/verifycode";
            }
            return "redirect:/reset-password?email=" + email; // Chuyển hướng đến trang đặt lại mật khẩu
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user/verifycode";
        }
    }

    // Hiển thị trang đặt lại mật khẩu
    @GetMapping("/reset-password")
    public String showResetPasswordPage(
            @RequestParam String email,
            Model model
    ) {
        model.addAttribute("email", email); // Truyền email vào trang đặt lại mật khẩu
        return "user/resetpassword"; // Trả về trang đặt lại mật khẩu
    }

    // Xử lý đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            authService.resetPassword(email, password);
            return "redirect:/dang-nhap"; // Chuyển hướng đến trang đăng nhập
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user/resetpassword";
        }
    }

    // Xử lý đăng xuất
    @GetMapping("/dang-xuat")
    public String logout() {
        return "redirect:/dang-nhap"; // Chuyển hướng đến trang đăng nhập
    }
}