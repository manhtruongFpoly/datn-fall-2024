package nice.store.datn.controller.ThongKe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThongKe {
    @GetMapping("/admin/thong-ke")
    public String hienThiHoaDon() {
        return "admin/ThongKe/ThongKe";
    }

}
