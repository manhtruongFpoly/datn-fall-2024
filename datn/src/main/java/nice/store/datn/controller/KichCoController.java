package nice.store.datn.controller;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.KichCo;
import nice.store.datn.service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class KichCoController {
    @Autowired
    private KichCoService kichCoService;

    @GetMapping("kich-co")
    public String listKichCo(Model model) {
        model.addAttribute("listKichCo", kichCoService.getAllKichCo());
        return "/admin/KichCo/KichCoIndex";
    }

    @GetMapping("/view-Kich-Co")
    public String addView(Model model) {
        model.addAttribute("kichCo", new KichCo());
        return "/admin/KichCo/KichCoAdd";
    }

    @PostMapping("/addKich-Co")
    public String addKichCo(@ModelAttribute KichCo kichCo, Model model) {

        if (kichCoService.existsBySize(kichCo.getSize())) {  // Assuming `size` is a field in `KichCo`
            model.addAttribute("errorMessage", "Size đã tồn tại. Vui lòng nhập kích cỡ khác.");
            model.addAttribute("kichCo", kichCo);  // Preserve the entered data
            return "/admin/KichCo/KichCoAdd";
        }

        kichCoService.addKichCo(kichCo);
        model.addAttribute("successMessage", "Kích Cỡ đã được thêm thành công.");
        return "redirect:/kich-co"; //
    }

    @GetMapping("/view-updateKichCo/{ma}")
    public String updateView(@PathVariable("ma") Integer ma, Model model) {
        model.addAttribute("kichCo", kichCoService.getKichCoById(ma).orElse(new KichCo()));
        return "/admin/KichCo/KichCoUpdate";
    }

    @PostMapping("/updateKichCo")
    public String updateKichCo(@RequestParam("maKichCo") String maKichCo, @ModelAttribute KichCo kichCo, Model model) {
        try {
            kichCoService.updateKichCo(maKichCo, kichCo);
            return "redirect:/kich-co";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/admin/KichCo/KichCoUpdate";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while updating the KichCo.");
            return "/admin/KichCo/KichCoUpdate";
        }
    }

    @GetMapping("/deleteKichCo/{id}")
    public String deleteKichCo(@PathVariable Integer id) {
        kichCoService.deleteKichCo(id);
        return "redirect:/kich-co";
    }
}
