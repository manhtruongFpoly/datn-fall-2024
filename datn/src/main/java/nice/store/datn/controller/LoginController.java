package nice.store.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/admin")
    public String adminSubmit(){
        return "pages/landing_page";
    }


    @RequestMapping("/home")
    public String loginSubmit(){
        return "index";
    }

    @RequestMapping("PhieuGiamGia")
    public String PhieuGiamGia(){
        return "/admin/PhieuGiamGia/PhieuGiamGia";
    }
}
