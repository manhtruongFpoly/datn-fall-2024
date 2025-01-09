package nice.store.datn.controller.ThongKe;

import nice.store.datn.response.SanPhamSapHetHangDTO;
import nice.store.datn.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThongKe {
    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/admin/thong-ke")
    public String hienThiHoaDon(Model model) {
        List<SanPhamSapHetHangDTO> sanPhamList = thongKeService.getSanPhamSapHetHangDTO();
        model.addAttribute("sanPhamList", sanPhamList);
        return "admin/ThongKe/ThongKe";
    }



}
