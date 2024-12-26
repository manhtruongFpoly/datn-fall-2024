package nice.store.datn.controller;

import jakarta.servlet.http.HttpServletRequest;
import nice.store.datn.entity.HoaDon;
import nice.store.datn.response.HoaDonChiTietDTO;
import nice.store.datn.service.HoaDonChiTietService;
import nice.store.datn.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/hoa-don")
public class HoaDonController {

    @Autowired HoaDonService hoaDonService;

    @Autowired HoaDonChiTietService hoaDonChiTietService;




    @Autowired
    public HoaDonController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @GetMapping("/hoa-don")
    public String hienThiDanhSachHoaDon(Model model) {
        List<HoaDon> hoaDons = hoaDonService.getAllHoaDon();
        model.addAttribute("hoaDons", hoaDons);
        return "/admin/Hoa_Don/hoa-don";
    }

    @GetMapping("/api/hoa-don/detail/{id}")
    public ResponseEntity<HoaDon> getHoaDonById(@PathVariable Integer id) {
        Optional<HoaDon> hoaDon = hoaDonService.getHoaDonById(id);
        return hoaDon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/hoa-don/detail1/{id}")
    public String chiTietHoaDon(@PathVariable Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.getHoaDonById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn!"));
        model.addAttribute("hoaDon", hoaDon);
        return "/admin/Hoa_Don/hoa-don-detail";
    }





}
