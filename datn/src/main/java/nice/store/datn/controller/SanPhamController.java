package nice.store.datn.controller;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.entity.SanPham;
import nice.store.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService service;

    @GetMapping("/danh-sach-san-pham")
    public String getAll(Model model){
        List<SanPham> listSp = service.getAllSp();
        model.addAttribute("listSanPham", listSp);
        return "/admin/SanPham/SanPhamIndex";
    }
    @GetMapping("/add-view")
    public String SanPhamViewTable(Model model) {
        model.addAttribute("sanPhamAdd", new SanPham());
        return "/admin/SanPham/SanPhamAdd";
    }

//    @PostMapping("/add")
//    public String add(@Valid
//                      @ModelAttribute ("themSanPham") SanPham sp
//                      , BindingResult result
//                       , Model model,
//                      RedirectAttributes redirectAttributes){
//        try {
//            service.create(sp);
//            return "/admin/SanPham/SanPhamIndex"; // Chuyển hướng đến trang danh sách
//        } catch (RuntimeException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "/admin/SanPham/SanPhamAdd"; // Quay lại trang tạo sản phẩm
//        }
//    }
@PostMapping("/add")
public String add(@Valid @ModelAttribute("themSanPham") SanPham sp,
                  BindingResult result,
                  RedirectAttributes redirectAttributes) {
    // Kiểm tra lỗi từ BindingResult
    if (result.hasErrors()) {
        return "admin/SanPham/SanPhamAdd"; // Quay lại trang tạo sản phẩm với thông báo lỗi
    }

    try {
        service.create(sp);
        redirectAttributes.addFlashAttribute("successMessage" , "Thêm sản phẩm thành công!");
        return "/admin/SanPham/SanPhamIndex";// Chuyển hướng đến trang thêm sản phẩm
    } catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "admin/SanPham/SanPhamAdd"; // Quay lại trang thêm sản phẩm
    }
}

//    @GetMapping("/update/{id}")
//    public String ViewUpdate(@PathVariable("id") Integer id , Model model){
//        Optional<SanPham> sanPham = service.getIdSanPham(id);
//        model.addAttribute("sp" , sanPham);
//        return "admin/SanPham/SanPhamUpdate";
//
//    }

    @GetMapping("/update/{id}")
    public String ViewUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<SanPham> sanPham = service.getIdSanPham(id);
        if (sanPham.isPresent()) {
            model.addAttribute("sp", sanPham.get());
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
//            model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
            System.out.println("Loi");
            return "error"; // Chuyển hướng đến trang lỗi hoặc trang khác
        }
        return "admin/SanPham/SanPhamUpdate";
    }


    @PostMapping("/update-san-pham/{id}")
    public String updatePhieuGiamGia(@PathVariable("id") Integer id, @Valid
    @ModelAttribute("sp") SanPham sp, BindingResult result) {
        LocalDate now = LocalDate.now();
        if (result.hasErrors()) {
            return "admin/SanPham/SanPhamIndex";
        }
        SanPham updateSp = service.updateSp(id, sp);
        if (updateSp != null) {
            return "redirect:/san-pham/danh-sach-san-pham";
        }
        return "admin/SanPham/SanPhamUpdate";
    }

    @GetMapping("/delete/{id}")
    public String deleteSanPham(@PathVariable("id") Integer id) {
        Optional<SanPham> sanPhamOptional = service.getIdSanPham(id);

        if (sanPhamOptional.isPresent()) {
            service.deleteSanPham(id);
            return "redirect:/san-pham/danh-sach-san-pham";
        } else {
            return "redirect:/san-pham/danh-sach-san-pham";
        }
    }


}
