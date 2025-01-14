package nice.store.datn.config;





import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy vai trò của người dùng
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Người dùng không có vai trò"))
                .getAuthority();

        System.out.println("User role: " + role);

        // Chuyển hướng dựa trên vai trò
        if (role.equals("ROLE_KHACH_HANG")) {
            System.out.println("Redirecting to /home");
            response.sendRedirect("/home");
        } else if (role.equals("ROLE_NHAN_VIEN")) {
            System.out.println("Redirecting to /admin");
            response.sendRedirect("/admin");
        } else if (role.equals("ROLE_ADMIN")) {
            System.out.println("Redirecting to /admin");
            response.sendRedirect("/admin");
        } else {
            System.out.println("Redirecting to /");
            response.sendRedirect("/");
        }
    }

}