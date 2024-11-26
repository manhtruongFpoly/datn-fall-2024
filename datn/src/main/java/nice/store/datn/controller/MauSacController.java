package nice.store.datn.controller;


import jakarta.validation.Valid;
import nice.store.datn.entity.MauSac;
import nice.store.datn.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;
    @GetMapping("/mau-sac")
    public String getAllMauSac(Model model) {
        List<MauSac> mauSacList = mauSacService.findAllOrderedByDate();
        model.addAttribute("mauSacList", mauSacList);
        return "admin/mau-sac/danh-sach"; // List view for colors
    }

    @GetMapping("/mau-sac/chi-tiet-mau-sac/{id}")
    public String getMauSacById(@PathVariable int id, Model model) {
        Optional<MauSac> mauSac = mauSacService.getMauSacById(id);
        if (mauSac.isPresent()) {
            model.addAttribute("mauSac", mauSac.get());
            return "admin/mau-sac/update";
        } else {
            return "redirect:/mau-sac";
        }
    }

    @GetMapping("/mau-sac/them")
    public String showAddMauSacForm(MauSac mauSac, Model model) {
        model.addAttribute("mauSac", new MauSac());
        return "admin/mau-sac/add-ms";
    }

    @PostMapping("/mau-sac/them")
    public String createMauSac(@ModelAttribute @Valid MauSac mauSac, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/mau-sac/add-ms";
        }
        mauSac.setNgayTao(LocalDateTime.now());
        mauSacService.create(mauSac);
        return "redirect:/mau-sac";
    }

    @GetMapping("/mau-sac/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<MauSac> mauSac = mauSacService.getMauSacById(id);
        if (mauSac.isPresent()) {
            model.addAttribute("mauSac", mauSac.get());
            return "admin/mau-sac/update";
        } else {
            return "redirect:/mau-sac";
        }
    }

    @PostMapping("/mau-sac/update/{id}")
    public String updateMauSac(@PathVariable("id") Integer id, @ModelAttribute MauSac mauSac, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mauSac", mauSac);
            return "admin/mau-sac/update";
        }
        mauSac.setNgaySua(LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();

        try {
            MauSac updatedMauSac = mauSacService.update(id, mauSac);
            return "redirect:/mau-sac";
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Cập nhật màu sắc thất bại. Vui lòng thử lại.");
            return "admin/mau-sac/update";
        }
    }

    @DeleteMapping("/mau-sac/delete/{id}")
    public ResponseEntity<String> deleteMauSac(@PathVariable Integer id) {
        boolean isDeleted = mauSacService.deleteMauSacById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Màu Sắc đã được xóa!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Màu sắc không tồn tại!");
        }
    }
}
