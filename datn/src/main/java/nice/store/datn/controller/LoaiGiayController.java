package nice.store.datn.controller;

import nice.store.datn.entity.LoaiGiay;
import nice.store.datn.service.LoaiGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller

public class LoaiGiayController {

    @Autowired
    private LoaiGiayService loaiGiayService;

    @GetMapping("/loai-giay")
    public String listLoaiGiay(Model model) {
        model.addAttribute("listLoaiGiay", loaiGiayService.findAll());
        return "/admin/LoaiGiay/LoaiGiayIndex";
    }

    @GetMapping("/addLoaiGiay")
    public String showAddForm(Model model) {
        model.addAttribute("loaiGiay", new LoaiGiay());
        return "/admin/LoaiGiay/LoaiGiayAdd";
    }

    @PostMapping("/saveLoaiGiay")
    public String saveLoaiGiay(@ModelAttribute LoaiGiay loaiGiay) {
        // Kiểm tra nếu `ngayTao` chưa được gán giá trị
        if (loaiGiay.getNgayTao() == null) {
            loaiGiay.setNgayTao(LocalDate.now()); // Đặt ngày hiện tại
        }
        loaiGiayService.save(loaiGiay);
        return "redirect:/loai-giay";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        LoaiGiay loaiGiay = loaiGiayService.findById(id);  // Kiểm tra xem loaiGiay có null không
        if (loaiGiay == null) {
            return "redirect:/loai-giay"; // Trở về danh sách nếu không tìm thấy
        }
        model.addAttribute("loaiGiay", loaiGiay);
        return "/admin/LoaiGiay/LoaiGiayUpdate";
    }


    @PostMapping("/updateLoaiGiay/{id}")
    public String update1(@PathVariable("id") Integer id, @ModelAttribute LoaiGiay loaiGiay) {
        // Nếu `loaiGiay` chưa có ngày tạo, gán ngày hiện tại
        if (loaiGiay.getNgayTao() == null) {
            loaiGiay.setNgayTao(LocalDate.now());
        }

        // Luôn cập nhật `ngaySua` là ngày hiện tại
        loaiGiay.setNgaySua(LocalDate.now());
        loaiGiayService.save(loaiGiay);
        return "redirect:/loai-giay";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiGiay(@PathVariable("id") Integer id) {
        loaiGiayService.deleteById(id);
        return "redirect:/loai-giay";
    }
}
