package nice.store.datn.controller;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import nice.store.datn.entity.Nhanvien;
import nice.store.datn.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RestController("/nhan-vien")
public class NhanVienController {
@Autowired
    private NhanVienService service;
@GetMapping("/danh-sach-nhan-vien")
    public String getAll(Model model) {
    List<Nhanvien> listNv = service.getAllNv();
    model.addAttribute("ListNhaVien", listNv);
    return "/admin/NhanVien/NhanVienIndex";
 }
 @GetMapping("/add-view")
    public String NhanVienViewTable(Model model) {
    model.addAttribute("nhanVienAdd", new Nhanvien());
    return "/admin/NhanVien/NhanVienAdd";
 }
 @PostMapping("/add")
    public String add(@Valid @ModelAttribute("themNhanVien") Nhanvien nv,
                      BindingResult result,
                      RedirectAttributes redirectAttributes){
    if(result.hasErrors()){
        return "/admin/NhanVien/NhanVienAdd"; // báo lỗi
    }
    try {
        service.create(nv);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công" );
        return "/admin/NhanVien/NhanVienIndex";
    }catch (Exception e){
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "/admin/NhanVien/NhanVienAdd";
    }
 }
 @GetMapping("/update/{id}")
public String ViewUpdate(@PathVariable("id") Integer id, Model model) {
     Optional<Nhanvien> nhanVien = service.getIdNhanVien(id);
     if(nhanVien.isPresent()){
         model.addAttribute("nv", nhanVien.get());
     }else {
         System.out.println("Lỗi");
         return "errorMessage";
     }
     return "/admin/NhanVien/NhanVienUpdate";
 }

 @PostMapping("/update-nhan-vien/{id}")
 public String updateNhanVien(@PathVariable("id") Integer id,
                              @Valid @ModelAttribute("nv") Nhanvien nv, BindingResult result){
    if (result.hasErrors()) {
        return "/admin/NhanVien/NhanVienIndex";
    }
    Nhanvien updateNv =service.update(id, nv);
    if (updateNv != null){
        return "redirect:/nhan-vien/danh-sach-nhan-vien";
    }
    return "/admin/NhanVien/NhanVienUpdate";
 }



 @GetMapping("/delete/{id}")
    public String deleteNhanvien(@PathVariable("id") Integer id){
    Optional<Nhanvien> nhanvienOptional = service.getIdNhanVien(id);
    if(nhanvienOptional.isPresent()){
        service.deleteNhanVien(id);
        return "redirect:/nhan-vien/danh-sach-nhan-vien";
    }else {
        return "redirect:/nhan-vien/danh-sach-nhan-vien";
    }
 }
}
