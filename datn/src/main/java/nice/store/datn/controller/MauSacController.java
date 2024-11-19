package nice.store.datn.controller;


import nice.store.datn.entity.MauSac;
import nice.store.datn.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;


@Controller

public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    @GetMapping("mau-sac")
    public String listMauSac(Model model) {
        model.addAttribute("listMauSac", mauSacService.getAllMauSac());
        return "/admin/MauSac/MauSacIndex";
    }

    // Hiển thị form thêm mới Màu Sắc
    @GetMapping("/view-Mau-Sac")
    public String addView(Model model) {
        model.addAttribute("mauSac", new MauSac());
        return "/admin/MauSac/MauSacAdd";
    }

    // Xử lý thêm Màu Sắc mới
    @PostMapping("/addMau-Sac")
    public String addMauSac(@ModelAttribute MauSac mauSac, Model model) {
        if (mauSacService.existsByTenMauSac(mauSac.getTenMauSac())) {
            model.addAttribute("errorMessage", "Tên Màu Sắc đã tồn tại. Vui lòng nhập tên khác.");
            model.addAttribute("mauSac", mauSac); // Giữ lại dữ liệu đã nhập
            return "/admin/MauSac/MauSacAdd";
        }

        mauSacService.addMauSac(mauSac);
        model.addAttribute("successMessage", "Màu Sắc đã được thêm thành công.");
        return "redirect:/mau-sac"; // Chuyển hướng đến trang danh sách sau khi thêm thành công
    }

    // Hiển thị form cập nhật Màu Sắc
    @GetMapping("/view-updateMauSac/{id}")
    public String updateView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("mauSac", mauSacService.getMauSacById(id).orElse(new MauSac()));
        return "/admin/MauSac/MauSacUpdate";
    }

    // Xử lý cập nhật Màu Sắc
    @PostMapping("/updateMauSac")
    public String updateMauSac(@RequestParam("maMauSac") String maMauSac, @ModelAttribute MauSac mauSac, Model model) {
        try {
            mauSacService.updateMauSac(maMauSac, mauSac); // Truyền `maMauSac` vào service
            return "redirect:/mau-sac"; // Chuyển hướng sau khi cập nhật thành công
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage()); // Hiển thị thông báo lỗi nếu không tìm thấy
            return "/admin/MauSac/MauSacUpdate"; // Quay lại form cập nhật
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật Màu Sắc.");
            return "/admin/MauSac/MauSacUpdate";
        }
    }

    // Xử lý xóa Màu Sắc
    @GetMapping("/deleteMauSac/{id}")
    public String deleteMauSac(@PathVariable Integer id) {
        mauSacService.deleteMauSac(id);
        return "redirect:/mau-sac";

    }
}
