package nice.store.datn.controller;


import nice.store.datn.entity.KhachHang;
import nice.store.datn.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khService;


    @GetMapping("/danh-sach")
    public String getAllKhachHang(Model model) {
        List<KhachHang> khachHangList = khService.getAllKH();
        model.addAttribute("khachHangList", khachHangList);
        return "pages/khach-hang/khachhang_list";
    }
//    @GetMapping("/danh-sach")
//    @ResponseBody
//    public List<KhachHang> getAllKhachHangJson() {
//        return khService.getAllKH();
//    }


    @GetMapping("/chi-tiet-khach-hang/{id}")
    public String getKhachHangById(@PathVariable int id, Model model) {
        Optional<KhachHang> khachHang = khService.getKHById(id);
        if (khachHang.isPresent()) {
            model.addAttribute("khachHang", khachHang.get());
            return "pages/khach-hang/chi-tiet-khach-hang";
        } else {
            return "redirect:/khach-hang/danh-sach";
        }
    }

    @GetMapping("/them")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "pages/khach-hang/them-khach-hang";
    }

    @PostMapping("/them")
    public String createKhachHang(@ModelAttribute KhachHang kh, Model model) {
        // Kiểm tra hợp lệ và lưu khách hàng
        if (kh.getMaKH() == null || kh.getHo() == null || kh.getTen() == null || kh.getEmail() == null) {
            model.addAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
            return "pages/khach-hang/them-khach-hang";
        }

        khService.createKH(kh);
        return "redirect:/khach-hang/danh-sach";
    }

    @PutMapping("/{id}")
    public KhachHang updateKhachhang(@PathVariable int id, @RequestBody KhachHang kh) {
        return khService.updateKH(id, kh);
    }

    @DeleteMapping("/delete-khach-hang/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable int id) {
        try {
            khService.deleteKHById(id);
            return ResponseEntity.ok().build(); // Trả về mã 200 nếu xóa thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về mã 404 nếu không tìm thấy khách hàng
        }
    }
}
