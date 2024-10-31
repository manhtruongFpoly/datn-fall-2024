package nice.store.datn.controller;

import jakarta.validation.Valid;
import nice.store.datn.entity.PhieuGiamGia;
import nice.store.datn.sevice.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDate;
import java.util.List;

@Controller

public class PhieuGiamGiaController {
    @Autowired
    PhieuGiamGiaService phieuGiamGiaService;

    @GetMapping("/phieu-giam-gia")
    public String hienThi(Model model) {
        model.addAttribute("listPgg", phieuGiamGiaService.findAll());
        return "/admin/PhieuGiamGia/PhieuGiamGia";
    }

    @GetMapping("/addPgg")
    public String PhieuGiamGiaAdd(Model model) {
        model.addAttribute("phieuGiamGia", new PhieuGiamGia());
        return "/admin/PhieuGiamGia/PhieuGiamGiaAdd";
    }

    @PostMapping("/SavePhieuGiamGia")
    public String addPhieuGiamGia(@Valid @ModelAttribute("phieuGiamGia") PhieuGiamGia pgg, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "redirect:/phieu-giam-gia";
        }
        pgg.setMa(pgg.getMa());
        pgg.setTen(pgg.getTen());
        pgg.setLoaiVoucher(pgg.getLoaiVoucher());
        pgg.setNgayBatDau(pgg.getNgayBatDau());
        pgg.setNgayKetThuc(pgg.getNgayKetThuc());
        pgg.setGiaTriGiam(pgg.getGiaTriGiam());
        pgg.setGiaTriMax(pgg.getGiaTriMax());
        pgg.setSoLuong(pgg.getSoLuong());

        LocalDate now = LocalDate.now();
        if (now.isBefore(pgg.getNgayBatDau())) {
            pgg.setTrangThai(2); // Chưa diễn ra
        } else if (now.isAfter(pgg.getNgayKetThuc())) {
            pgg.setTrangThai(1); // Ngừng hoạt động
        } else {
            pgg.setTrangThai(0); // Hoạt động
        }


        phieuGiamGiaService.save(pgg);
        return "redirect:/phieu-giam-gia";
    }


    @GetMapping("updatePGG/{id}")
    public String ShowPGGDetail(@PathVariable("id") Integer id , Model model){
        PhieuGiamGia phieuGiamGia = phieuGiamGiaService.findById(id);
        model.addAttribute("pgg" , phieuGiamGia);
        return "/admin/PhieuGiamGia/PhieuGiamGiaUpdate";

    }

    @PostMapping("/SavePhieuGiamGia/{id}")
    public String updatePhieuGiamGia(@PathVariable("id") Integer id, @Valid @ModelAttribute("pgg") PhieuGiamGia pgg, BindingResult result) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(pgg.getNgayBatDau())) {
            pgg.setTrangThai(2); // Chưa diễn ra
        } else if (now.isAfter(pgg.getNgayKetThuc())) {
            pgg.setTrangThai(1); // Ngừng hoạt động
        } else {
            pgg.setTrangThai(0); // Hoạt động
        }
        if (result.hasErrors()) {
            return "/admin/PhieuGiamGia/PhieuGiamGiaUpdate";
        }
        PhieuGiamGia updatedPgg = phieuGiamGiaService.update(id, pgg);
        if (updatedPgg != null) {
            return "redirect:/phieu-giam-gia";
        }
        return "/admin/PhieuGiamGia/PhieuGiamGiaUpdate";
    }

}
