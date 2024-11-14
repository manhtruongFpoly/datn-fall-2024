package nice.store.datn.controller;

import jakarta.servlet.http.HttpServletRequest;
import nice.store.datn.entity.HoaDon;
import nice.store.datn.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    private final HoaDonService hoaDonService;



    @Autowired
    public HoaDonController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @GetMapping
    public String hienThiDanhSachHoaDon(Model model) {
        List<HoaDon> hoaDons = hoaDonService.getAllHoaDon();
        model.addAttribute("hoaDons", hoaDons);
        return "/admin/Hoa_Don/hoa-don";
    }
}
