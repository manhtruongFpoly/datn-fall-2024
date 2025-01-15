package nice.store.datn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA256PasswordEncoder(); // Sử dụng mã hóa SHA-256
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Tắt CSRF (nếu cần)
                .authorizeHttpRequests()
                .requestMatchers(
                        "/dang-nhap",
                        "/dang-ky",
                        "/quen-mat-khau",
                        "/quen-mat-khau/**", // Cho phép tất cả các đường dẫn liên quan đến quên mật khẩu
                        "/verify-code",
                        "/verify-code/**", // Cho phép tất cả các đường dẫn liên quan đến xác nhận mã
                        "/reset-password",
                        "/reset-password/**", // Cho phép tất cả các đường dẫn liên quan đến đặt lại mật khẩu
                        "/css/**",
                        "/js/**",
                        "/home",
                        "/product-use",
                        "/product-detail/{productId}",
                        "/cart/view/",
                        "/cart/checkout",
                        "/product-detail-tra-sl-sp-va-id-spct/{productId}",
                        "/api/cart/add",
                        "/api/san-pham-chi-tiet/desktop-images/logo.jpg",
                        "/cart/view",
                        "/api/gio-hang/tao-hoa-don-dat-hang-khong-dang-nhap",  // tạo hóa đơn pub
                        "/detai12/{productId}",
                        "/api/phieu-giam-gia-online",
                        "/api/gio-hang/them-san-pham-vao-hoa-don/{id}",
                        "/api/gio-hang/delete-sp/{id}",
                        "/gioi-thieu",
                        "/tra-cuu",
                        "/gio-hang",
                        "/detail",
                        "/api/ban-hang/them-phuong-thuc-thanh-toan/{id}",
                        "/api/hoa-don/update-phieu-giam-gia/{id}",
                        "/api/hoa-don/lich-su-hoa-don/add/{idHD}",
                        "/css/**", "/js/**", "/images/**",
                        "/desktop-images/**",

                        "/api/san-pham-ct-co-trong-hoa-don/{hoaDonId}",
                        "/api/don-hang/update-so-luong/xac-nhan",
                        "/thong-ke-hoadon"




                ).permitAll() // Cho phép truy cập công khai
                .requestMatchers("/khach-hang/**").hasRole("KHACH_HANG")
                .requestMatchers("/admin/**").hasAnyRole("NHAN_VIEN", "ADMIN")
                .anyRequest().authenticated() // Yêu cầu xác thực cho các trang khác
                .and()
                .formLogin()
                .loginPage("/dang-nhap")
                .successHandler(customAuthenticationSuccessHandler()) // Xử lý chuyển hướng sau đăng nhập
                .failureUrl("/dang-nhap?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/dang-nhap")
                .permitAll();

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Lấy vai trò của người dùng
            String role = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .findFirst()
                    .orElse("");

            // Chuyển hướng dựa trên vai trò
            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/ban-hang-tai-quay");
            } else if (role.equals("ROLE_NHAN_VIEN")) {
                response.sendRedirect("/admin/ban-hang-tai-quay");
            } else if (role.equals("ROLE_KHACH_HANG")) {
                response.sendRedirect("/home");
            } else {
                response.sendRedirect("/home");
            }
        };
    }
}