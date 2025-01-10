package nice.store.datn.controller.user;

import nice.store.datn.response.SanPhamCTResponse;
import nice.store.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    private SanPhamCTService sanPhamCTService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private ChatLieuService chatLieuService;

    @Autowired
    private DeGiayService deGiayService;

    @Autowired
    private LoaiGiayService loaiGiayService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private HinhAnhService hinhAnhService;

    @Autowired
    private MauSacService mauSacService;

    @RequestMapping("/home")
    public String loginSubmit(){
        return "/user/index";
    }

    @GetMapping("/product-use")
    public String productview(Model model) {
        // Fetching all product responses (SanPhamCTResponse)
        List<SanPhamCTResponse> sanPhamCTResponses = sanPhamCTService.getAllSanPhamCTResponses();

        // Add the list of product responses to the model
        model.addAttribute("listSanPhamCT", sanPhamCTResponses);

        // Fetch other necessary lists and add them to the model
        model.addAttribute("listSanPham", sanPhamService.getAllSanPham());
        model.addAttribute("listHinhAnh", hinhAnhService.getAllImages());
        model.addAttribute("listThuongHieu", thuongHieuService.getAllTh());
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieu());
        model.addAttribute("listDeGiay", deGiayService.getAllDeGiay());
        model.addAttribute("listLoaiGiay", loaiGiayService.findAll());
        model.addAttribute("listKichCo", kichCoService.getAllKichCo());
        model.addAttribute("listMauSac", mauSacService.getAllMauSac());

        return "/user/product";
    }

    @GetMapping("/gioi-thieu")
    public String gioiThieu(){
        return "/user/intro";
    }

    @GetMapping("/tra-cuu")
    public String lienHe(){
        return "/user/tracuu";
    }

    @GetMapping("/login")
    public String loginUser(){
        return "/user/login";
    }

    @GetMapping("/dang-ky")
    public String RegistrationUser(){
        return "/user/registration";
    }

    @GetMapping("/gio-hang")
    public String cartUser(){
        return "/user/cart";
    }

    @RequestMapping("/detail")
    public String detail(Model model){
        return "/user/ProductDetail";
    }


}
