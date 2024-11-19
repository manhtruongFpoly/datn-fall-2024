package nice.store.datn.controller;

import jakarta.validation.Valid;
import nice.store.datn.entity.SanPham;
import nice.store.datn.response.SanPhamResponse;
import nice.store.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SanPhamController {
    @Autowired
    private SanPhamService sanPhamService;

    //hÀM HIỂN THỊ SPCT THÔNG QUA SP



    @GetMapping("/products")
    public String listProducts(Model model) {
        List<SanPhamResponse> products = sanPhamService.getAllProducts();
//
//        // map so luong
//        Map<Long, Integer> soLuongMap = new HashMap<>();
//        for(SanPhamResponse sp : products){
//            int soLuong =
//        }

        model.addAttribute("products", products);
        return "admin/sanPham/SanPhamIndex";
    }

    @GetMapping("/add-view")
    public String SanPhamViewTable(Model model) {
        model.addAttribute("sanPhamAdd", new SanPham());
        return "/admin/SanPham/SanPhamAdd";
    }
    @PostMapping("/sanpham/tao-moi")
    public String createSanPham(@Valid @ModelAttribute("sanPham") SanPham sanPham,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/sanPham/SanPhamIndex";
        }

        try {
            sanPhamService.createProduct(sanPham);

            redirectAttributes.addFlashAttribute("successMessage", "Tạo sản phẩm thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/products";
    }

    @GetMapping("/sanpham/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.getIdSanPham(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sp = sanPhamOptional.get();
            model.addAttribute("sp", sp);
            return "admin/sanPham/SanPhamUpdate"; // The view for editing the product
        } else {

            return "redirect:/products";
        }
    }

    @PostMapping("/sanpham/update/{id}")
    public String updateProduct(@PathVariable("id") Integer id,
                                @Valid @ModelAttribute("sp") SanPham sp,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/sanPham/SanPhamUpdate"; // Return to the form if validation fails
        }

        try {
            sanPhamService.updateSp(id, sp); // Call the service method to update the product
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được cập nhật thành công!");
            return "redirect:/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật sản phẩm.");
            return "redirect:/sanpham/update/" + id;
        }
    }

}
