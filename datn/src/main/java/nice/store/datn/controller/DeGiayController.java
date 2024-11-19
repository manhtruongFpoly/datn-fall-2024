package nice.store.datn.controller;

import jakarta.persistence.EntityNotFoundException;
import nice.store.datn.entity.DeGiay;
import nice.store.datn.service.DeGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DeGiayController {
    @Autowired
    private DeGiayService deGiayService;

    @GetMapping("de-giay")
    public String listDeGiay(Model model) {
        model.addAttribute("listDeGiay", deGiayService.getAllDeGiay());
        return "/admin/DeGiay/DeGiayIndex";
    }

    @GetMapping("/view-De-Giay")
    public String addView(Model model) {
        model.addAttribute("deGiay", new DeGiay());
        return "/admin/DeGiay/DeGiayAdd";
    }

    @PostMapping("/addDe-Giay")
    public String addDeGiay(@ModelAttribute DeGiay deGiay, Model model) {

        if (deGiayService.existsByTenDeGiay(deGiay.getTenDeGiay())) {

            model.addAttribute("errorMessage", "Tên Đế Giày đã tồn tại. Vui lòng nhập tên khác.");
            model.addAttribute("deGiay", deGiay); // Preserve the entered data
            return "/admin/DeGiay/DeGiayAdd";
        }


        deGiayService.addDeGiay(deGiay);
        model.addAttribute("successMessage", "Đế Giày đã được thêm thành công.");
        return "redirect:/de-giay"; // Redirect to the list page after successful submission
    }


    @GetMapping("/view-updateDeGiay/{ma}")
    public String updateView(@PathVariable("ma") Integer ma, Model model) {

        model.addAttribute("deGiay", deGiayService.getDeGiayById(ma).orElse(new DeGiay()));
        return "/admin/degiay/updateDeGiay";
    }

    @PostMapping("/updateDeGiay")
    public String updateDeGiay(@RequestParam("maDeGiay") String maDeGiay, @ModelAttribute DeGiay deGiay, Model model) {
        try {
            deGiayService.updateDG(maDeGiay, deGiay);  // Pass maDeGiay to the service
            return "redirect:/de-giay";  // Redirect after successful update
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());  // Show error message in case of failure
            return "/admin/degiay/updateDeGiay";  // Redirect back to the edit form
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while updating the DeGiay.");
            return "/admin/degiay/updateDeGiay";
        }
    }




    @GetMapping("/deleteDeGiay/{id}")
    public String deleteDeGiay(@PathVariable Integer id) {
        deGiayService.deleteDeGiay(id);
        return "redirect:/de-giay";
    }
}


