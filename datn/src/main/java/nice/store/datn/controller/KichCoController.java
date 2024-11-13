package nice.store.datn.controller;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import nice.store.datn.entity.KichCo;
import nice.store.datn.entity.ThuongHieu;
import nice.store.datn.service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class KichCoController {
    @Autowired
    private KichCoService service;

    @GetMapping("/danh-sach-kich-co")
    public String getAll(Model model){
        List<KichCo> list = service.getAllKichCo();
        model.addAttribute("listKichCo", list);
        return "/admin/KichCo/KichCoIndex";
    }
    @GetMapping("/add-view")
    public String KichCoViewTable(Model model){
        model.addAttribute("kichCo", new KichCo());
        return "/admin/KichCo/KichCoAdd";
    }
    @GetMapping("/add")
    public String add(@Valid @ModelAttribute("themSanPham") KichCo kc,
                      BindingResult result,
                      Model model){
        if (result.hasErrors()){
            return "/admin/KichCo/KichCoAdd";
        }
        try {
            service.create(kc);
            model.addAttribute("successMessage","Thêm sản phẩm thành công");
            return "redirect:/danh-sach-kich-co";
        } catch ( RuntimeException e){
            model.addAttribute("errorMessage","Trung ma");
            return "/admin/KichCo/KichCoIndex";
        }
    }
    @GetMapping("/update-view-kc/{id}")
    public String ViewUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<KichCo> th = service.getKichCoById(id);
        if (th.isPresent()) {
            model.addAttribute("sp", th.get());
        } else {
            System.out.println("Loi");
            return "error"; // Chuyển hướng đến trang lỗi hoặc trang khác
        }
        return "/admin/KichCo/KichCoUpdate";
    }
    @PostMapping("/update/kich-co/{id}")
    public String updateKC(@PathVariable("id") Integer id, @Valid
    @ModelAttribute("sp") KichCo sp, BindingResult result) {
        LocalDate now = LocalDate.now();
        if (result.hasErrors()) {
            return "/admin/KichCo/KichCoUpdate";
        }
        KichCo updateSp = service.updateKC(id, sp);
        if (updateSp != null) {
            return "redirect:/danh-sach-kich-co";
        }
        return "/admin/KichCo/KichCoUpdate";
    }

    @GetMapping("/delete-kich-co/{id}")
    public String deletekc(@PathVariable("id") Integer id) {
        Optional<KichCo> kichCoOptional = service.getKichCoById(id);

        if (kichCoOptional.isPresent()) {
            service.deleteKichCo(id);
            return "redirect:/danh-sach-kich-co";
        } else {
            return "redirect:/danh-sach-kich-co";
        }
    }
}
