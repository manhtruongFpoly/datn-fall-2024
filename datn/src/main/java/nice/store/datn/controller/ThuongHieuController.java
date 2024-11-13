package nice.store.datn.controller;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import nice.store.datn.entity.SanPham;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.service.SanPhamService;
import nice.store.datn.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService service;

    @GetMapping("/danh-sach-thuong-hieu")
    public String getAll(Model model){
        List<ThuongHieu> list = service.getAllTh();
        model.addAttribute("listThuongHieu", list);
        return "/admin/ThuongHieu/ThuongHieuIndex";
    }
    @GetMapping("/add-view")
    public String ThuongHieuViewTable(Model model) {
        model.addAttribute("thuongHieu", new ThuongHieu());
        return "/admin/ThuongHieu/ThuongHieuAdd";
    }

@PostMapping("/add")
public String add(@Valid @ModelAttribute("themSanPham") ThuongHieu sp,
                  BindingResult result,
                  Model model) {
    // Kiểm tra lỗi từ BindingResult
    if (result.hasErrors()) {
        return "admin/ThuongHieu/ThuongHieuAdd"; // Quay lại trang tạo sản phẩm với thông báo lỗi
    }

    try {
        service.create(sp);
        model.addAttribute("successMessage", "Thêm sản phẩm thành công!");
        return "redirect:/danh-sach-thuong-hieu";// Chuyển hướng đến trang thêm sản phẩm
    } catch (RuntimeException e) {
        model.addAttribute("errorMessage","Trùng mã");
        return "/admin/ThuongHieu/ThuongHieuIndex"; // Quay lại trang thêm sản phẩm
    }
}

    @GetMapping("/update-view-th/{id}")
    public String ViewUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<ThuongHieu> th = service.getIdTh(id);
        if (th.isPresent()) {
            model.addAttribute("sp", th.get());
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
//            model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
            System.out.println("Loi");
            return "error"; // Chuyển hướng đến trang lỗi hoặc trang khác
        }
        return "admin/ThuongHieu/ThuongHieuUpdate";
    }


    @PostMapping("/update/thuong-hieu/{id}")
    public String updateTH(@PathVariable("id") Integer id, @Valid
    @ModelAttribute("sp") ThuongHieu sp, BindingResult result) {
        LocalDate now = LocalDate.now();
        if (result.hasErrors()) {
            return "admin/ThuongHieu/ThuongHieuIndex";
        }
        ThuongHieu updateSp = service.updateSp(id, sp);
        if (updateSp != null) {
            return "redirect:/danh-sach-thuong-hieu";
        }
        return "admin/ThuongHieu/ThuongHieuUpdate";
    }

    @GetMapping("/delete-thuong-hieu/{id}")
    public String deleteTh(@PathVariable("id") Integer id) {
        Optional<ThuongHieu> thuongHieuOptional = service.getIdTh(id);

        if (thuongHieuOptional.isPresent()) {
            service.deleteThuongHieu(id);
            return "redirect:/danh-sach-thuong-hieu";
        } else {
            return "redirect:/danh-sach-thuong-hieu";
        }
    }


}
