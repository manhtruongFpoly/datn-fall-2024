package nice.store.datn.controller;

import nice.store.datn.entity.NhanVien;
import nice.store.datn.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

public class NhanVienController {

    private final NhanVienService service;

    @Autowired
    public NhanVienController(NhanVienService service) {
        this.service = service;
    }

    @GetMapping("/danh-sachNV")
    public String getAllNhanVien(Model model) {
        List<NhanVien> listNv = service.getAllNV();
        model.addAttribute("listNhanVien", listNv);
        return "admin/NhanVien/NhanVienIndex";
    }


    @GetMapping("/add-viewNV")
    public String showAddForm(Model model) {
        model.addAttribute("nhanVienAdd", new NhanVien());
        return "admin/NhanVien/NhanVienAdd";
    }

    @PostMapping("/addNV")
    public String addNhanVien(
            @Validated @ModelAttribute("nhanVienAdd") NhanVien nv,
            BindingResult result,
            Model model) {
        // Kiểm tra lỗi từ BindingResult
        if (result.hasErrors()) {
            return "admin/NhanVien/NhanVienAdd";
        }

        try {
            service.create(nv);
            model.addAttribute("successMessage", "Thêm nhân viên thành công!");
            return "redirect:/danh-sachNV";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/NhanVien/NhanVienAdd";
        }
    }


    @GetMapping("/updateNV-view/{maNv}")
    public String showUpdateForm(@PathVariable("maNv") String maNv, Model model) {
        Optional<NhanVien> nhanVien = service.getNhanVienByMaNv(maNv);
        if (nhanVien.isPresent()) {
            model.addAttribute("nv", nhanVien.get());
            return "admin/NhanVien/NhanVienUpdate";
        } else {
            model.addAttribute("errorMessage", "Nhân viên không tồn tại.");
            return "error";
        }
    }

    @PostMapping("/updateNhanVien/{maNv}")
    public String updateNhanVien(
            @PathVariable("maNv") String maNv,
            @Validated @ModelAttribute("nhanVienUpdate") NhanVien nv,
            BindingResult result,
            Model model) {

        // Check for validation errors
        if (result.hasErrors()) {
            // If there are validation errors, return the same view with errors
            return "admin/NhanVien/NhanVienUpdate";
        }

        try {
            // Call the service method to update the "Nhân Viên" by maNv
            service.updateNhanVienByMaNv(maNv, nv);

            // Add success message to the model
            model.addAttribute("successMessage", "Cập nhật nhân viên thành công!");

            // Redirect to the list of employees (assuming it's a valid URL)
            return "redirect:/danh-sachNV";
        } catch (RuntimeException e) {
            // Handle any exceptions thrown (e.g., if the maNv doesn't exist)
            model.addAttribute("errorMessage", e.getMessage());

            // Return to the update page with the error message
            return "admin/NhanVien/NhanVienUpdate";
        }
    }



    @GetMapping("/deleteNV/{id}")
    public String deleteNhanVien(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteNV(id);
            model.addAttribute("successMessage", "Xóa nhân viên thành công!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Xóa nhân viên thất bại: " + e.getMessage());
        }
        return "redirect:/danh-sachNV";
    }
}
