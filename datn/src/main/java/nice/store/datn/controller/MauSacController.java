package nice.store.datn.controller;


import jakarta.validation.Valid;
import nice.store.datn.entity.DiaChi;
import nice.store.datn.entity.GioHang;
import nice.store.datn.entity.KhachHang;
import nice.store.datn.entity.MauSac;
import nice.store.datn.service.DiaChiService;
import nice.store.datn.service.GioHangService;
import nice.store.datn.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mau-sac")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;
    @GetMapping("/danh-sach")
    public String getAllMauSac(Model model) {
        List<MauSac> mauSacList = mauSacService.getAllMauSac();
        model.addAttribute("mauSacList", mauSacList);
        return "admin/mau-sac/danh-sach"; // List view for colors
    }

    @GetMapping("/chi-tiet-mau-sac/{id}")
    public String getMauSacById(@PathVariable int id, Model model) {
        Optional<MauSac> mauSac = mauSacService.getMauSacById(id);
        if (mauSac.isPresent()) {
            model.addAttribute("mauSac", mauSac.get());
            return "admin/mau-sac/update";
        } else {
            return "redirect:/mau-sac/danh-sach";
        }
    }

    @GetMapping("/them")
    public String showAddMauSacForm(MauSac mauSac, Model model) {
        model.addAttribute("mauSac", new MauSac());
        return "admin/mau-sac/add-ms";
    }

    @PostMapping("/them")
    public String createMauSac(@ModelAttribute @Valid MauSac mauSac, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/mau-sac/add-ms";
        }

        mauSacService.create(mauSac);
        return "redirect:/mau-sac/danh-sach";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<MauSac> mauSac = mauSacService.getMauSacById(id);
        if (mauSac.isPresent()) {
            model.addAttribute("mauSac", mauSac.get());
            return "admin/mau-sac/update";
        } else {
            return "redirect:/mau-sac/danh-sach";
        }
    }

    @PostMapping("/update/{id}")
    public String updateMauSac(@PathVariable("id") Integer id, @ModelAttribute MauSac mauSac) {
        try {
            MauSac updatedMauSac = mauSacService.update(id, mauSac);
            return "redirect:/mau-sac/danh-sach";
        } catch (RuntimeException e) {

            e.printStackTrace();
            return "redirect:/mau-sac/danh-sach";
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMauSac(@PathVariable Integer id) {
        boolean isDeleted = mauSacService.deleteMauSacById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Màu Sắc đã được xóa!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Màu sắc không tồn tại!");
        }
    }
}
